package br.com.enutri.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.dto.RelatorioDTO;
import br.com.enutri.model.dto.validation.OnCreate;
import br.com.enutri.service.RelatorioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/relatorios")
@Tag(name="Relatorio")
public class RelatorioController {

    @Autowired
    RelatorioService relatorioService;

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<RelatorioDTO>> getRelatoriosByPaciente(@PathVariable Long id) {

        List<RelatorioDTO> relatorios = relatorioService.getRelatoriosByPacienteId(id);

        return ResponseEntity.status(HttpStatus.OK).body(relatorios);
    }
    
    @PostMapping("/novo")
    @Validated(OnCreate.class)
    public ResponseEntity<RelatorioDTO> novoRelatorio(@RequestBody @Valid RelatorioDTO relatorioDTO) {
        
        RelatorioDTO novoRelatorio = relatorioService.novoRelatorio(relatorioDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRelatorio);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<RelatorioDTO> updateRelatorio(@PathVariable Long id, @RequestBody RelatorioDTO relatorioDTO) {
        RelatorioDTO relatorio = relatorioService.updateRelatorio(id, relatorioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(relatorio);
    }

    @DeleteMapping("/{id}/medicao/{idMedicao}")
    public ResponseEntity<RelatorioDTO> deleteMedicao(@PathVariable Long id, @PathVariable Long idMedicao) {

        RelatorioDTO relatorio = relatorioService.deleteMedicao(id, idMedicao);

        return ResponseEntity.status(HttpStatus.OK).body(relatorio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RelatorioDTO> deleteRelatorio(@PathVariable Long id) {
        RelatorioDTO relatorio = relatorioService.deleteRelatorio(id);

        return ResponseEntity.status(HttpStatus.OK).body(relatorio);
    }
}
