package br.com.enutri.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.Relatorio;
import br.com.enutri.model.dto.RelatorioDTO;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.RelatorioRepository;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    public RelatorioDTO novoRelatorio(RelatorioDTO relatorioDTO) {
        Paciente paciente = pacienteRepository.getReferenceById(relatorioDTO.getPaciente());
        Nutricionista nutricionista = nutricionistaRepository.getReferenceById(relatorioDTO.getNutricionistaResponsavel());

        Relatorio novoRelatorio = new Relatorio();
        novoRelatorio.setDataConsulta(relatorioDTO.getDataConsulta());
        novoRelatorio.setNutricionistaResponsavel(nutricionista);
        novoRelatorio.setPaciente(paciente);
        novoRelatorio.setDadosMedidos(new HashMap<String, String>());

        Relatorio relatorioSalvo = relatorioRepository.save(novoRelatorio);
        paciente.addRelatorio(relatorioSalvo);
        relatorioDTO.setId(relatorioSalvo.getId());

        return relatorioDTO;
    }

    public RelatorioDTO novoDadoMedido(Map<String, String> dadoMedido, long id) {
        Relatorio relatorio = relatorioRepository.getReferenceById(id);
        relatorio.novaMedicao(dadoMedido);

        return new RelatorioDTO(relatorio);
    }

}
