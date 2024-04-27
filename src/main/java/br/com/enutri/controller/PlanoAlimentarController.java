package br.com.enutri.controller;

import br.com.enutri.model.ListaCompras;
import br.com.enutri.model.dto.ListaComprasDTO;
import br.com.enutri.service.ListaComprasService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.model.dto.RefeicaoDTO;
import br.com.enutri.model.dto.RegistroDiarioDTO;
import br.com.enutri.service.PlanoAlimentarService;

import java.util.List;

@RestController
@RequestMapping("/planos-alimentares")
@Tag(name="PlanoAlimentar")
public class PlanoAlimentarController {
    
    @Autowired
    private PlanoAlimentarService PlanoAlimentarService;

    @Autowired
    private ListaComprasService listaComprasService;

    @GetMapping("/{id}")
    public ResponseEntity<PlanoAlimentarDTO> getPlanoAlimentar(@PathVariable Long id) {
        PlanoAlimentar planoAlimentar = PlanoAlimentarService.getPlanoAlimentarById(id);
        PlanoAlimentarDTO planoAlimentarDTO = new PlanoAlimentarDTO(planoAlimentar);
        
        return ResponseEntity.status(HttpStatus.OK).body(planoAlimentarDTO);
    }

    @PostMapping("/novo")
    public ResponseEntity<PlanoAlimentarDTO> novoPlanoAlimentar(@RequestBody PlanoAlimentarDTO planoAlimentarDTO) {
        planoAlimentarDTO = PlanoAlimentarService.novoPlanoAlimentar(planoAlimentarDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(planoAlimentarDTO);
    }

    @PostMapping("/novo/registro-diario")
    public ResponseEntity<RegistroDiarioDTO> novoRegistroDiario(@RequestBody RegistroDiarioDTO registroDiarioDTO) {
        RegistroDiarioDTO novoRegistroDiario = PlanoAlimentarService.novoRegistroDiario(registroDiarioDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRegistroDiario);
    }

    @PostMapping("/{id}/add/refeicao")
    public ResponseEntity<RefeicaoDTO> novaRefeicao(@RequestBody RefeicaoDTO refeicaoDTO, @PathVariable Long id) {
        RefeicaoDTO novaRefeicao = PlanoAlimentarService.adicionarRefeicao(refeicaoDTO, id);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRefeicao);
    }

    @PatchMapping("/{id}/edit/refeicao")
    public ResponseEntity<RefeicaoDTO> editarRefeicao(@RequestBody RefeicaoDTO refeicaoDTO, @PathVariable Long id) {
        // TODO
        
        return ResponseEntity.status(HttpStatus.OK).body(refeicaoDTO);
    }

    @PostMapping("/{id}/ativar")
    public ResponseEntity<PlanoAlimentarDTO> ativarPlanoAlimentar(@PathVariable Long id) {
        PlanoAlimentarDTO planoAlimentarDTO = PlanoAlimentarService.ativarPlanoAlimentar(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(planoAlimentarDTO);
    }

    @PostMapping("/registros-diarios/responder")
    public ResponseEntity<RegistroDiarioDTO> responderRegistroDiario(@RequestBody RegistroDiarioDTO registroDiarioDTO) {
        RegistroDiarioDTO registroDiarioRespondido = PlanoAlimentarService.responderRegistroDiario(registroDiarioDTO);
        
        return ResponseEntity.status(HttpStatus.OK).body(registroDiarioRespondido);
    }

    @PostMapping("/refeicoes/responder")
    public ResponseEntity<RefeicaoDTO> responderRefeicao(@RequestBody RefeicaoDTO refeicaoDTO) {
        RefeicaoDTO refeicaoRespondida = PlanoAlimentarService.responderRefeicao(refeicaoDTO);

        return ResponseEntity.status(HttpStatus.OK).body(refeicaoRespondida);
    }

    @GetMapping("/{id}/lista-compras")
    public ResponseEntity<ListaComprasDTO> gerarListaCompras(@PathVariable Long id) {

        ListaCompras listaCompras = listaComprasService.gerarListaItens(id);
        ListaComprasDTO savedListaComprasDTO = new ListaComprasDTO(listaCompras);
        return new ResponseEntity<ListaComprasDTO>(savedListaComprasDTO, HttpStatus.CREATED);
    }

}