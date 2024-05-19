package br.com.enutri.service;

import br.com.enutri.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.Receita;
import br.com.enutri.model.Refeicao;
import br.com.enutri.model.RegistroDiario;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.model.dto.RefeicaoDTO;
import br.com.enutri.model.dto.RegistroDiarioDTO;
import br.com.enutri.repository.PlanoAlimentarRepository;
import br.com.enutri.repository.RefeicaoRepository;
import br.com.enutri.repository.RegistroDiarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PlanoAlimentarService {
    
    @Autowired
    private PlanoAlimentarRepository planoAlimentarRepository;

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private NutricionistaService nutricionistaService;

    @Autowired
    private ReceitaService receitaService;

    public PlanoAlimentar getPlanoAlimentarById(Long id) throws ResourceNotFoundException {
        return planoAlimentarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano alimentar de id " + id + " não encontrado."));
    }

    public RegistroDiario getRegistroDiarioById(Long id) throws ResourceNotFoundException {
        return registroDiarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro Diário de id " + id + " não encontrado."));
    }

    public Refeicao getRefeicaoById(Long id) throws ResourceNotFoundException {
            return refeicaoRepository.findById(id).
            orElseThrow(() -> new ResourceNotFoundException("Refeição de id " + id + " não encontrado."));
    }

    @Transactional
    public PlanoAlimentarDTO novoPlanoAlimentar(PlanoAlimentarDTO planoAlimentarDTO) {

        PlanoAlimentar novoPlanoAlimentar = new PlanoAlimentar();
        Paciente paciente = pacienteService.getPacienteById(planoAlimentarDTO.getPaciente().getId());
        Nutricionista nutricionistaResponsavel = nutricionistaService.getNutricionistaById(planoAlimentarDTO.getNutricionistaResponsavel());

        novoPlanoAlimentar.setPaciente(paciente);
        novoPlanoAlimentar.setNutricionistaResponsavel(nutricionistaResponsavel);
        novoPlanoAlimentar.setDataInicio(planoAlimentarDTO.getDataInicio());
        novoPlanoAlimentar.setDataFim(planoAlimentarDTO.getDataFim());

        PlanoAlimentar planoAlimentarSalvo = planoAlimentarRepository.save(novoPlanoAlimentar);

        int diasDePlano = planoAlimentarDTO.getDataFim().getDayOfYear() - planoAlimentarDTO.getDataInicio().getDayOfYear() + 1;
        for(int i = 0; i < diasDePlano; i++) {
            RegistroDiario novoRegistroDiario = new RegistroDiario();
            novoRegistroDiario.setPlanoAlimentar(planoAlimentarSalvo);
            novoRegistroDiario.setData(planoAlimentarDTO.getDataInicio().plusDays(i) );
            RegistroDiario registroDiarioSalvo = registroDiarioRepository.save(novoRegistroDiario);

            planoAlimentarSalvo.addRegistroDiario(registroDiarioSalvo);
        }

        planoAlimentarDTO = new PlanoAlimentarDTO(planoAlimentarSalvo);

        return planoAlimentarDTO;
    }

    @Transactional
    public RegistroDiarioDTO novoRegistroDiario(RegistroDiarioDTO registroDiarioDTO) {

        PlanoAlimentar planoAlimentar = getPlanoAlimentarById(registroDiarioDTO.getPlanoAlimentar());

        RegistroDiario novoRegistroDiario = new RegistroDiario();
        novoRegistroDiario.setPlanoAlimentar(planoAlimentar);
        novoRegistroDiario.setData(registroDiarioDTO.getData());

        RegistroDiario novoRegistroDiarioSalvo = registroDiarioRepository.save(novoRegistroDiario);
        planoAlimentar.addRegistroDiario(novoRegistroDiarioSalvo);

        registroDiarioDTO.setId(novoRegistroDiarioSalvo.getId());

        return registroDiarioDTO;
    }

    @Transactional
    public RefeicaoDTO adicionarRefeicao(RefeicaoDTO refeicaoDTO, Long planoAlimentarId) {

        PlanoAlimentar planoAlimentar = getPlanoAlimentarById(planoAlimentarId);
        
        Long registroDiarioId = planoAlimentar.getRegistroDiarioByDate(refeicaoDTO.getData()).getId();
        RegistroDiario registroDiario = getRegistroDiarioById(registroDiarioId);
        Receita receitaEscolhida = receitaService.getById(refeicaoDTO.getReceitaEscolhida().getId());

        Refeicao novaRefeicao = new Refeicao();
        novaRefeicao.setData(refeicaoDTO.getData());
        novaRefeicao.setHorario(refeicaoDTO.getHorario());
        novaRefeicao.setReceitaEscolhida(receitaEscolhida);

        Refeicao novaRefeicaoSalva = refeicaoRepository.save(novaRefeicao);
        registroDiario.addRefeicao(novaRefeicaoSalva);

        return new RefeicaoDTO(novaRefeicaoSalva);
    }

    public RefeicaoDTO removerRefeicao(Long planoAlimentarId, Long refeicaoId) {

        PlanoAlimentar planoAlimentar = getPlanoAlimentarById(planoAlimentarId);
        Refeicao refeicao = getRefeicaoById(refeicaoId);
        Long registroDiarioId = planoAlimentar.getRegistroDiarioByDate(refeicao.getData()).getId();
        RegistroDiario registroDiario = getRegistroDiarioById(registroDiarioId);

        registroDiario.getRefeicoes().remove(refeicao);
        refeicaoRepository.delete(refeicao);

        return new RefeicaoDTO(refeicao);
    }

    public PlanoAlimentarDTO ativarPlanoAlimentar(Long id) {
        PlanoAlimentar planoAlimentar = getPlanoAlimentarById(id);
        long pacienteId = planoAlimentar.getPaciente().getId();
        Paciente paciente = pacienteService.getPacienteById(pacienteId);

        if(paciente.existsPlanoAlimentarAtivo()) {
            paciente.getPlanoAlimentarAtual().setAtivo(false);
        }
        paciente.addPlanoAlimentar(planoAlimentar);
        paciente.setPlanoAtual(planoAlimentar);
        planoAlimentar.setAtivo(true);

        pacienteService.save(paciente);
        planoAlimentarRepository.save(planoAlimentar);

        return new PlanoAlimentarDTO(planoAlimentar);
    }

    public RegistroDiarioDTO responderRegistroDiario(RegistroDiarioDTO registroDiarioDTO) {
        RegistroDiario registroDiario = getRegistroDiarioById(registroDiarioDTO.getId());
        registroDiario.addListaSintomas(registroDiarioDTO.getSintomas());
        registroDiario.setQualidadeSono(registroDiarioDTO.getQualidadeSono());

        RegistroDiario registroDiarioSalvo = registroDiarioRepository.save(registroDiario);

        return new RegistroDiarioDTO(registroDiarioSalvo);
    }

    public RefeicaoDTO responderRefeicao(RefeicaoDTO refeicaoDTO) {

        Refeicao refeicao = getRefeicaoById(refeicaoDTO.getId());
        refeicao.setEmocao(refeicaoDTO.getEmocao());
        refeicao.setRefeicaoFeita(true);

        Refeicao refeicaoSalva = refeicaoRepository.save(refeicao);

        return new RefeicaoDTO(refeicaoSalva);
    }

    public List<PlanoAlimentarDTO> getListaPlanosAlimentaresByNutricionista(Long id) {
        Nutricionista nutricionista = nutricionistaService.getNutricionistaById(id);
        List<PlanoAlimentar> planosAlimentares = planoAlimentarRepository.getByNutricionistaResponsavel(nutricionista);
        List<PlanoAlimentarDTO> planosAlimentaresDTO = new ArrayList<PlanoAlimentarDTO>();
        for(PlanoAlimentar planoAlimentar : planosAlimentares) {
            planosAlimentaresDTO.add(new PlanoAlimentarDTO(planoAlimentar));
        }
        return planosAlimentaresDTO;
    }

    public Boolean deletePlanoAlimentar(Long id) {
        PlanoAlimentar planoAlimentar = getPlanoAlimentarById(id);

        if(planoAlimentar.getAtivo()) {
            planoAlimentar.getPaciente().setPlanoAtual(null);
        }

        planoAlimentarRepository.delete(planoAlimentar);

        return true;
    }
}