package br.com.enutri.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.Relatorio;
import br.com.enutri.model.dto.RelatorioDTO;
import br.com.enutri.repository.RelatorioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private NutricionistaService nutricionistaService;

    public Relatorio getRelatorioById(Long id) {
        try {
            return relatorioRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Relatório de id " + id + " não encontrado.");
        }
    }

    @Transactional
    public RelatorioDTO novoRelatorio(RelatorioDTO relatorioDTO) {
        
        Paciente paciente = pacienteService.getPacienteById(relatorioDTO.getPaciente());
        Nutricionista nutricionista = nutricionistaService.getNutricionistaById(relatorioDTO.getNutricionistaResponsavel());

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
        
        Relatorio relatorio = getRelatorioById(id);

        relatorio.novaMedicao(dadoMedido);
    
        return new RelatorioDTO(relatorio);
    }

}
