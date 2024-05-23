package br.com.enutri.controller;

import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.model.dto.validation.OnCreate;
import br.com.enutri.service.ReceitaService;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
@Tag(name="Receitas")
@Validated
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping(path = "/todos")
    public ResponseEntity<List<ReceitaDTO>> retriveAllReceitas() {
        List<ReceitaDTO> receitas = receitaService.retriveAllReceitas();
        return new ResponseEntity<>(receitas, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReceitaDTO> getReceitaById(@PathVariable long id) {
        Receita receitaConsultada = receitaService.getById(id);
        ReceitaDTO receitaDTO = new ReceitaDTO(receitaConsultada);
        return new ResponseEntity<>(receitaDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/novo")
    @Validated(OnCreate.class)
    public ResponseEntity<ReceitaDTO> addReceita(@RequestBody @Valid ReceitaDTO receitaDTO) {
        Receita novaReceita = receitaService.save(receitaDTO);
        ReceitaDTO savedReceitaDTO = new ReceitaDTO(novaReceita);

        return new ResponseEntity<>(savedReceitaDTO, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/atualizar/{id}")
    public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable("id") Long id,
            @RequestBody ReceitaDTO receitaDTO) {

        Receita receitaAtualizada = receitaService.atualizar(id, receitaDTO);
        ReceitaDTO receitaAtualizadaDTO = new ReceitaDTO(receitaAtualizada);
        return new ResponseEntity<>(receitaAtualizadaDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<String> deleteReceita(@PathVariable("id") Long id) {

        receitaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
