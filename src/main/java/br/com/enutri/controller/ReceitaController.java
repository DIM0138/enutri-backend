package br.com.enutri.controller;

import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.service.ReceitaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping(path = "/todos")
    public ResponseEntity<List<ReceitaDTO>> retriveAllReceitas() {
        List<ReceitaDTO> receitas = receitaService.retriveAllReceitas();
        return new ResponseEntity<>(receitas, HttpStatus.OK);
    }

    @PostMapping(path = "/novo")
    private ResponseEntity<ReceitaDTO> addReceita(@RequestBody ReceitaDTO receitaDTO) {
        Receita novaReceita = receitaService.save(receitaDTO);
        ReceitaDTO savedReceitaDTO = new ReceitaDTO(novaReceita);

        return new ResponseEntity<>(savedReceitaDTO, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/atualizar/{id}")
    public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable("id") Long id,
            @RequestBody ReceitaDTO receitaDTO) {
        if (!receitaService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Receita receitaAtualizada = receitaService.atualizar(id, receitaDTO);
        ReceitaDTO receitaAtualizadaDTO = new ReceitaDTO(receitaAtualizada);
        return new ResponseEntity<>(receitaAtualizadaDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<String> deleteReceita(@PathVariable("id") Long id) {
        receitaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReceitaDTO> getReceita(@PathVariable Long id) {
        Receita receita = receitaService.getReceitaById(id);
        ReceitaDTO receitaDTO = new ReceitaDTO(receita);
        return new ResponseEntity<>(receitaDTO, HttpStatus.OK);
    }

}
