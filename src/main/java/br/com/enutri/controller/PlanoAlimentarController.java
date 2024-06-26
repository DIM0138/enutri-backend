package br.com.enutri.controller;

import br.com.enutri.model.ListaCompras;
import br.com.enutri.model.dto.ListaComprasDTO;
import br.com.enutri.service.ListaComprasService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.model.dto.RefeicaoDTO;
import br.com.enutri.model.dto.RegistroDiarioDTO;
import br.com.enutri.model.dto.validation.OnCreate;
import br.com.enutri.service.PlanoAlimentarService;

import java.util.List;


@RestController
@RequestMapping("/planos-alimentares")
@Tag(name="PlanoAlimentar")
@Validated
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

    @GetMapping("/nutricionista/{id}")
    public ResponseEntity<List<PlanoAlimentarDTO>> getListaPlanosAlimentares(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(PlanoAlimentarService.getListaPlanosAlimentaresByNutricionista(id));
    }
    

    @PostMapping("/novo")
    @Validated(OnCreate.class)
    public ResponseEntity<PlanoAlimentarDTO> novoPlanoAlimentar(@RequestBody @Valid PlanoAlimentarDTO planoAlimentarDTO) {
        planoAlimentarDTO = PlanoAlimentarService.novoPlanoAlimentar(planoAlimentarDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(planoAlimentarDTO);
    }

    @PostMapping("/{id}/refeicao")
    @Validated(OnCreate.class)
    public ResponseEntity<RefeicaoDTO> novaRefeicao(@RequestBody @Valid RefeicaoDTO refeicaoDTO, @PathVariable Long id) {
        RefeicaoDTO novaRefeicao = PlanoAlimentarService.adicionarRefeicao(refeicaoDTO, id);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRefeicao);
    }

    @DeleteMapping("/{id}/refeicao/{idRefeicao}")
    public ResponseEntity<RefeicaoDTO> removerRefeicao(@PathVariable Long id, @PathVariable Long idRefeicao) {
        RefeicaoDTO refeicaoRemovida = PlanoAlimentarService.removerRefeicao(id, idRefeicao);
        
        return ResponseEntity.status(HttpStatus.OK).body(refeicaoRemovida);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletarPlanoAlimentar(@PathVariable Long id) {
        Boolean deletado = PlanoAlimentarService.deletePlanoAlimentar(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletado);
    }
}