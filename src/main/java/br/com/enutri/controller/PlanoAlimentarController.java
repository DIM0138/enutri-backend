package br.com.enutri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.service.PlanoAlimentarService;

@RestController
@RequestMapping("/planos-alimentares")
public class PlanoAlimentarController {
    
    @Autowired
    private PlanoAlimentarService PlanoAlimentarService;

    @PostMapping("/novo")
    public ResponseEntity<PlanoAlimentar> novoPlanoAlimentar(@RequestBody PlanoAlimentarDTO planoAlimentarDTO) {
        PlanoAlimentar novoPlanoAlimentar = PlanoAlimentarService.save(planoAlimentarDTO);

        return ResponseEntity.ok(novoPlanoAlimentar);
    }
}