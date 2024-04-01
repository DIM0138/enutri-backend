package br.com.enutri.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Paciente;
import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.Refeicao;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.model.dto.RefeicaoDTO;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.PlanoAlimentarRepository;
import br.com.enutri.repository.RefeicaoRepository;

@Service
public class PlanoAlimentarService {

    @Autowired
    private PlanoAlimentarRepository planoAlimentarRepository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public PlanoAlimentar novoPlanoAlimentar(PlanoAlimentarDTO planoAlimentarDTO) {
        PlanoAlimentar novoPlanoAlimentar = new PlanoAlimentar();
        novoPlanoAlimentar.setPaciente(planoAlimentarDTO.getPaciente());
        novoPlanoAlimentar.setNutricionistaResponsavel(planoAlimentarDTO.getNutricionistaResponsavel());
        novoPlanoAlimentar.setDataInicio(planoAlimentarDTO.getDataInicio());
        novoPlanoAlimentar.setDataFim(planoAlimentarDTO.getDataFim());

        PlanoAlimentar novoPlanoAlimentarSalvo = planoAlimentarRepository.save(novoPlanoAlimentar);
        
        List<Refeicao> refeicoes = new ArrayList<Refeicao>();

        for(RefeicaoDTO refeicaoDTO : planoAlimentarDTO.getListaRefeicoes()) {
            Refeicao novaRefeicao = new Refeicao();
            novaRefeicao.setDataRefeicao(refeicaoDTO.getDataRefeicao());
            novaRefeicao.setHorario(refeicaoDTO.getHorario());
            novaRefeicao.setReceitaEscolhida(refeicaoDTO.getReceitaEscolhida());
            novaRefeicao.setPlanoAlimentar(novoPlanoAlimentarSalvo);
            refeicaoRepository.save(novaRefeicao);
            refeicoes.add(novaRefeicao);
        }

        novoPlanoAlimentarSalvo.setListaRefeicoes(refeicoes);

        Paciente paciente = pacienteRepository.getReferenceById(planoAlimentarDTO.getPaciente().getId());
        paciente.setPlanoAtual(novoPlanoAlimentarSalvo);

        return novoPlanoAlimentarSalvo;
    }

    public PlanoAlimentar findById(Long id) {
        return planoAlimentarRepository.findById(id).get();
    }
}
