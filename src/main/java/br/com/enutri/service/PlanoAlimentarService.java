package br.com.enutri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.Refeicao;
import br.com.enutri.model.RegistroDiario;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.model.dto.RefeicaoDTO;
import br.com.enutri.model.dto.RegistroDiarioDTO;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.PlanoAlimentarRepository;
import br.com.enutri.repository.RefeicaoRepository;

@Service
public class PlanoAlimentarService {

    @Autowired
    private PlanoAlimentarRepository planoAlimentarRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    public PlanoAlimentar save(PlanoAlimentarDTO planoAlimentarDTO) {

        PlanoAlimentar novoPlanoAlimentar = planoAlimentarRepository.save(new PlanoAlimentar());
        Paciente paciente = pacienteRepository.getReferenceById(planoAlimentarDTO.getPaciente().getId());
        Nutricionista nutricionistaResponsavel = nutricionistaRepository.getReferenceById(planoAlimentarDTO.getNutricionistaResponsavel().getId());

        novoPlanoAlimentar.setPaciente(paciente);
        novoPlanoAlimentar.setNutricionistaResponsavel(nutricionistaResponsavel);
        novoPlanoAlimentar.setDataInicio(planoAlimentarDTO.getDataInicio());
        novoPlanoAlimentar.setDataFim(planoAlimentarDTO.getDataFim());

        for(RegistroDiarioDTO registroDiarioDTO : planoAlimentarDTO.getRegistrosDiarios()) {
            RegistroDiario novoRegistroDiario = new RegistroDiario();
            novoRegistroDiario.setPlanoAlimentar(novoPlanoAlimentar);
            novoRegistroDiario.setData(registroDiarioDTO.getData());
            for(RefeicaoDTO refeicaoDTO : registroDiarioDTO.getRefeicoes()) {
                Refeicao novaRefeicao = refeicaoRepository.save(new Refeicao(refeicaoDTO));
                novoRegistroDiario.getRefeicoes().add(novaRefeicao);
            }
        }

        paciente.setPlanoAtual(novoPlanoAlimentar);

        return planoAlimentarRepository.save(novoPlanoAlimentar);
    }
}