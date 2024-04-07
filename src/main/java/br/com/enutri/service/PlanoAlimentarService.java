package br.com.enutri.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.PlanoAlimentarRepository;
import br.com.enutri.repository.ReceitaRepository;
import br.com.enutri.repository.RefeicaoRepository;
import br.com.enutri.repository.RegistroDiarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PlanoAlimentarService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    @Autowired
    private PlanoAlimentarRepository planoAlimentarRepository;

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Transactional
    public PlanoAlimentarDTO novoPlanoAlimentar(PlanoAlimentarDTO planoAlimentarDTO) {

        PlanoAlimentar novoPlanoAlimentar = new PlanoAlimentar();
        Paciente paciente = pacienteRepository.getReferenceById(planoAlimentarDTO.getPaciente());
        Nutricionista nutricionistaResponsavel = nutricionistaRepository.getReferenceById(planoAlimentarDTO.getNutricionistaResponsavel());

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

        PlanoAlimentar planoAlimentar = planoAlimentarRepository.getReferenceById(registroDiarioDTO.getPlanoAlimentar());

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
        
        PlanoAlimentar planoAlimentar = planoAlimentarRepository.getReferenceById(planoAlimentarId);
        Long registroDiarioId = planoAlimentar.getRegistroDiarioByDate(refeicaoDTO.getData()).getId();
        RegistroDiario registroDiario = registroDiarioRepository.getReferenceById(registroDiarioId);
        Receita receitaEscolhida = receitaRepository.getReferenceById(refeicaoDTO.getReceitaEscolhida());

        Refeicao novaRefeicao = new Refeicao();
        novaRefeicao.setData(refeicaoDTO.getData());
        novaRefeicao.setHorario(refeicaoDTO.getHorario());
        novaRefeicao.setReceitaEscolhida(receitaEscolhida);

        Refeicao novaRefeicaoSalva = refeicaoRepository.save(novaRefeicao);
        registroDiario.addRefeicao(novaRefeicaoSalva);

        return new RefeicaoDTO(novaRefeicaoSalva);
    }

    public PlanoAlimentarDTO ativarPlanoAlimentar(Long id) {
        PlanoAlimentar planoAlimentar = planoAlimentarRepository.getReferenceById(id);
        long pacienteId = planoAlimentar.getPaciente().getId();
        Paciente paciente = pacienteRepository.getReferenceById(pacienteId);
        
        try{
            paciente.getPlanoAlimentarAtual().setAtivo(false);
        }
        catch(NoSuchElementException e){
            
        } 
        finally{
            paciente.addPlanoAlimentar(planoAlimentar);
            paciente.setPlanoAtual(planoAlimentar);
            planoAlimentar.setAtivo(true);
        }

        pacienteRepository.save(paciente);
        planoAlimentarRepository.save(planoAlimentar);

        return new PlanoAlimentarDTO(planoAlimentar);
    }

    public PlanoAlimentarDTO getPlanoAlimentar(Long id) throws ResourceNotFoundException{

        try {
            PlanoAlimentar planoAlimentar = planoAlimentarRepository.getReferenceById(id);
            return new PlanoAlimentarDTO(planoAlimentar);
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Plano alimentar não encontrado");
        }
    }
}