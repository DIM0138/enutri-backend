package br.com.enutri.service;

import br.com.enutri.model.*;
import br.com.enutri.model.metricas.AdesaoData;
import br.com.enutri.model.metricas.Metrica;
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

    public Metrica getPacienteMetricas(Long idPaciente, LocalDate dataInicio, LocalDate dataFim){
        List<PlanoAlimentar> planos = planoAlimentarRepository.getByPaciente(pacienteService.getPacienteById(idPaciente));

        List<RegistroDiario> registrosPaciente = new ArrayList<>();
        for (PlanoAlimentar plano : planos) {
            registrosPaciente.addAll(plano.getRegistrosDiarios());
        }

        List<RegistroDiario> registrosDiarios = new ArrayList<>();

        if (dataInicio != null & dataFim != null){
            for (RegistroDiario registroDiario : registrosPaciente){
                LocalDate registroData = registroDiario.getData();
                if (
                        (registroData.isEqual(dataInicio) || registroData.isAfter(dataInicio)) &&
                        (registroData.isEqual(dataFim) || registroData.isBefore(dataFim))
                ) {
                    registrosDiarios.add(registroDiario);
                }
            }
        }
        else {
            registrosDiarios.addAll(registrosPaciente);
        }
        Metrica metricas = new Metrica();

        metricas.setAdesaoTag(adesaoTag(registrosDiarios));
        metricas.setAdesaoEmocao(adesaoEmocao(registrosDiarios));
        metricas.setQuantidadeQualidadeSono(quantidadeSono(registrosDiarios));
        metricas.setQuantidadeEmocao(quantidadeEmocao(registrosDiarios));
        metricas.setSintomas(sintomas(registrosDiarios));

        return metricas;
    }

    private AdesaoData adesaoTag(List<RegistroDiario> registroDiarios){
        List<Refeicao> refeicoes = new ArrayList<Refeicao>();

        for (RegistroDiario registroDiario : registroDiarios){
            refeicoes.addAll(registroDiario.getRefeicoes());
        }

        AdesaoData adesaoTag = new AdesaoData();

        for (Refeicao refeicao : refeicoes) {
            Boolean refeicaoFeita = refeicao.getRefeicaoFeita();
            if (refeicaoFeita != null) {
                String tipoRefeicao = refeicao.getReceitaEscolhida().getTipoRefeicao().toString();
                if (refeicaoFeita){
                    adesaoTag.getFeito().put(tipoRefeicao, adesaoTag.getFeito().getOrDefault(tipoRefeicao, 0)+1);
                } else {
                    adesaoTag.getNaoFeito().put(tipoRefeicao, adesaoTag.getNaoFeito().getOrDefault(tipoRefeicao, 0)+1);
                }
            }
        }

        return adesaoTag;
    }

    private AdesaoData adesaoEmocao(List<RegistroDiario> registroDiarios){
        List<Refeicao> refeicoes = new ArrayList<Refeicao>();

        for (RegistroDiario registroDiario : registroDiarios){
            refeicoes.addAll(registroDiario.getRefeicoes());
        }

        AdesaoData adesaoEmocao = new AdesaoData();

        for (Refeicao refeicao : refeicoes) {
            Boolean refeicaoFeita = refeicao.getRefeicaoFeita();
            if (refeicaoFeita != null) {
                String tipoRefeicao = refeicao.getEmocao().toString();
                if (refeicaoFeita) {
                    adesaoEmocao.getFeito().put(tipoRefeicao, adesaoEmocao.getFeito().getOrDefault(tipoRefeicao, 0)+1);
                }
                else {
                    adesaoEmocao.getNaoFeito().put(tipoRefeicao, adesaoEmocao.getNaoFeito().getOrDefault(tipoRefeicao, 0)+1);
                }
            }

        }

        return adesaoEmocao;
    }

    private Map<String, Integer> quantidadeSono(List<RegistroDiario> registroDiarios){
        Map<String, Integer> quantidadeSono = new HashMap<>();

        for (RegistroDiario registroDiario : registroDiarios){
            String tipoSono = registroDiario.getQualidadeSono().toString();
            if (!Objects.equals(tipoSono, "PENDENTE")){
                quantidadeSono.put(tipoSono, quantidadeSono.getOrDefault(tipoSono, 0)+1);
            }
        }
        return quantidadeSono;
    }

    private Map<String, Integer> quantidadeEmocao(List<RegistroDiario> registroDiarios){
        List<Refeicao> refeicoes = new ArrayList<Refeicao>();

        for (RegistroDiario registroDiario : registroDiarios){
            refeicoes.addAll(registroDiario.getRefeicoes());
        }

        Map<String, Integer> quantidadeEmocao = new HashMap<>();

        for (Refeicao refeicao : refeicoes){
            String tipoEmocao = refeicao.getEmocao().toString();
            if (!Objects.equals(tipoEmocao, "PENDENTE")){
                quantidadeEmocao.put(tipoEmocao, quantidadeEmocao.getOrDefault(tipoEmocao, 0)+1);
            }
        }

        return quantidadeEmocao;
    }

    private List<String> sintomas(List<RegistroDiario> registroDiarios){
        List<String> sintomas = new ArrayList<>();

        for (RegistroDiario registroDiario : registroDiarios){
            sintomas.addAll(registroDiario.getSintomas());
        }

        return sintomas;
    }
}
