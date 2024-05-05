package br.com.enutri.service;

import br.com.enutri.exception.ResourceNotFoundException;
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
import jakarta.persistence.EntityNotFoundException;
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
        try {
            return planoAlimentarRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Plano alimentar de id " + id + " não encontrado.");
        }
    }

    public RegistroDiario getRegistroDiarioById(Long id) throws ResourceNotFoundException {
        try {
            return registroDiarioRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Registro diário de id " + id + " não encontrado.");
        }
    }

    public Refeicao getRefeicaoById(Long id) throws ResourceNotFoundException {
        try {
            return refeicaoRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Refeição de id " + id + " não encontrado.");
        }
    }

    @Transactional
    public PlanoAlimentarDTO novoPlanoAlimentar(PlanoAlimentarDTO planoAlimentarDTO) {

        PlanoAlimentar novoPlanoAlimentar = new PlanoAlimentar();
        Paciente paciente = pacienteService.getPacienteById(planoAlimentarDTO.getPaciente());
        Nutricionista nutricionistaResponsavel = nutricionistaService.getById(planoAlimentarDTO.getNutricionistaResponsavel());

        novoPlanoAlimentar.setPaciente(paciente);
        novoPlanoAlimentar.setNutricionistaResponsavel(nutricionistaResponsavel);
        novoPlanoAlimentar.setDataInicio(planoAlimentarDTO.getDataInicio());
        novoPlanoAlimentar.setDataFim(planoAlimentarDTO.getDataFim());

        PlanoAlimentar planoAlimentarSalvo = planoAlimentarRepository.save(novoPlanoAlimentar);

        planoAlimentarDTO.setId(planoAlimentarSalvo.getId());

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
}