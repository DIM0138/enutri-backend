package br.com.enutri.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.exception.DuplicateResourceException;
import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.model.Medicao;
import br.com.enutri.model.MedicaoRelatorio;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.Relatorio;
import br.com.enutri.model.dto.RelatorioDTO;
import br.com.enutri.repository.MedicaoRelatorioRepository;
import br.com.enutri.repository.MedicaoRepository;
import br.com.enutri.repository.RelatorioRepository;
import jakarta.transaction.Transactional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private NutricionistaService nutricionistaService;

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private MedicaoRelatorioRepository medicaoRelatorioRepository;

    public Relatorio getRelatorioById(Long id) {

        return relatorioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Relatório de id " + id + " não encontrado."));
    }

    public List<RelatorioDTO> getRelatoriosByPacienteId(Long id) {
        
        List<Relatorio> relatorios = relatorioRepository.findByPacienteId(id);
        List<RelatorioDTO> relatoriosDTO = new ArrayList<RelatorioDTO>();
        for(Relatorio relatorio : relatorios) {
            relatoriosDTO.add(new RelatorioDTO(relatorio));
        }

        return relatoriosDTO;
    }

    @Transactional
    public RelatorioDTO novoRelatorio(RelatorioDTO relatorioDTO) {

        if(relatorioRepository.existsByDataConsultaAndPacienteId(relatorioDTO.getDataConsulta(), relatorioDTO.getPaciente())) {
            throw new DuplicateResourceException("O paciente de id " + relatorioDTO.getPaciente() + " já possui um relatório para a data " + relatorioDTO.getDataConsulta() + ".");
        }
        
        Paciente paciente = pacienteService.getPacienteById(relatorioDTO.getPaciente());
        Nutricionista nutricionista = nutricionistaService.getNutricionistaById(relatorioDTO.getNutricionistaResponsavel());

        Relatorio novoRelatorio = new Relatorio();
        novoRelatorio.setDataConsulta(relatorioDTO.getDataConsulta());
        novoRelatorio.setNutricionistaResponsavel(nutricionista);
        novoRelatorio.setPaciente(paciente);
        novoRelatorio.setMedicoes(new ArrayList<MedicaoRelatorio>());

        for(MedicaoRelatorio medicaoRelatorio : relatorioDTO.getMedicoes()) {
            Medicao medicaoExistente = medicaoRepository.findOneByNomeIgnoreCaseAndUnidadeIgnoreCase(medicaoRelatorio.getMedicao().getNome(), medicaoRelatorio.getMedicao().getUnidade());
            if(medicaoExistente != null) {
                medicaoRelatorio.setMedicao(medicaoExistente);
            }
            else {
                Medicao novaMedicao = medicaoRepository.save(medicaoRelatorio.getMedicao());
                medicaoRelatorio.setMedicao(novaMedicao);
            }
        }

        novoRelatorio.setMedicoes(relatorioDTO.getMedicoes());
        Relatorio relatorioSalvo = relatorioRepository.save(novoRelatorio);
        
        return new RelatorioDTO(relatorioSalvo);
    }

    public RelatorioDTO deleteRelatorio(Long id) {
        Relatorio relatorio = getRelatorioById(id);
        relatorioRepository.delete(relatorio);
        return new RelatorioDTO(relatorio);
    }

    @Transactional
    public RelatorioDTO deleteMedicao(Long idRelatorio, Long idMedicao) {

        Relatorio relatorio = getRelatorioById(idRelatorio);
        MedicaoRelatorio medicaoRelatorio = medicaoRelatorioRepository.findById(idMedicao).orElseThrow(() -> new ResourceNotFoundException("Medição de id " + idMedicao + " não encontrada."));

        relatorio.getMedicoes().remove(medicaoRelatorio);
        medicaoRelatorioRepository.delete(medicaoRelatorio);

        Relatorio relatorioSalvo = relatorioRepository.save(relatorio);

        return new RelatorioDTO(relatorioSalvo);
    }

    @Transactional
    public RelatorioDTO updateRelatorio(Long id, RelatorioDTO relatorioDTO) {

        Relatorio relatorio = getRelatorioById(id);
        
        for(MedicaoRelatorio medicaoRelatorio : relatorio.getMedicoes()) {
            medicaoRelatorioRepository.delete(medicaoRelatorio);
        }

        relatorio.setMedicoes(new ArrayList<MedicaoRelatorio>());

        for(MedicaoRelatorio medicaoRelatorio : relatorioDTO.getMedicoes()) {
            Medicao medicaoExistente = medicaoRepository.findOneByNomeIgnoreCaseAndUnidadeIgnoreCase(medicaoRelatorio.getMedicao().getNome(), medicaoRelatorio.getMedicao().getUnidade());
            if(medicaoExistente != null) {
                medicaoRelatorio.setMedicao(medicaoExistente);
            }
            else {
                Medicao novaMedicao = medicaoRepository.save(medicaoRelatorio.getMedicao());
                medicaoRelatorio.setMedicao(novaMedicao);
            }
        }

        relatorio.setMedicoes(relatorioDTO.getMedicoes());
        Relatorio relatorioAtualizado = relatorioRepository.save(relatorio);

        return new RelatorioDTO(relatorioAtualizado);
    }
}
