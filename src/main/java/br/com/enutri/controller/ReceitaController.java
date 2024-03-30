package br.com.enutri.controller;

import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping(path="/novo")
    private ResponseEntity<ReceitaDTO> addReceita(@RequestBody ReceitaDTO receitaDTO){
        Receita novaReceita = receitaService.save(receitaDTO);
        ReceitaDTO savedReceitaDTO = new ReceitaDTO(novaReceita);

        return new ResponseEntity<>(savedReceitaDTO, HttpStatus.CREATED);
    }
    // TODO (Maria Amanda):Editar receita

    // TODO (Maria Amanda):Remover receita
}
