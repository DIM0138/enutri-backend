package br.com.enutri.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.dto.RelatorioDTO;
import br.com.enutri.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    RelatorioService relatorioService;

    @PostMapping("/novo")
    public ResponseEntity<RelatorioDTO> novoRelatorio(@RequestBody RelatorioDTO relatorioDTO) {
        
        RelatorioDTO novoRelatorio = relatorioService.novoRelatorio(relatorioDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRelatorio);
    }

    @PostMapping("/{id}/novo-dado")
    public ResponseEntity<RelatorioDTO> postMethodName(@RequestBody Map<String, String> dadoMedido, @PathVariable long id) {
        
        RelatorioDTO relatorio = relatorioService.novoDadoMedido(dadoMedido, id);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorio);
    }

}
