package br.com.enutri.service;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.Refeicao;
import br.com.enutri.model.RegistroDiario;
import br.com.enutri.repository.PlanoAlimentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MetricasService {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    PlanoAlimentarRepository planoAlimentarRepository;

    public Map<String, Map<String, Integer>> adesaoTag(Long idPaciente, LocalDate dataInicio, LocalDate dataFim){
        PlanoAlimentar planoAlimentar = pacienteService.getPlanoAlimentarAtual(idPaciente);

        List<Refeicao> refeicoes = new ArrayList<Refeicao>();

        for (RegistroDiario registroDiario : planoAlimentar.getRegistrosDiarios()){
            if (registroDiario.getData().isAfter(dataInicio) && registroDiario.getData().isBefore(dataFim)) {
                refeicoes.addAll(registroDiario.getRefeicoes());
            }
        }

        Map<String, Integer> feito =  new HashMap<String, Integer>();
        Map<String, Integer> naoFeito =  new HashMap<String, Integer>();

        for (Refeicao refeicao : refeicoes) {
            if (refeicao.getRefeicaoFeita()) {
                String tipoRefeicao = refeicao.getReceitaEscolhida().getTipoRefeicao().toString();
                feito.put(tipoRefeicao, feito.getOrDefault(tipoRefeicao, 0)+1);
            }
            else {
                String tipoRefeicao = refeicao.getReceitaEscolhida().getTipoRefeicao().toString();
                naoFeito.put(tipoRefeicao, naoFeito.getOrDefault(tipoRefeicao, 0)+1);
            }
        }

        Map<String, Map<String, Integer>> dados = new HashMap<>();
        dados.put("feito", feito);
        dados.put("naoFeito", naoFeito);

        return dados;
    }
}
