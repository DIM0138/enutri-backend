package br.com.enutri.controller;


import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import br.com.enutri.service.PlanoAlimentarService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/planos")
public class PlanoAlimentarController {

    @Autowired
    private PlanoAlimentarService planoAlimentarService;

    @GetMapping("/{id}")
    public ResponseEntity<PlanoAlimentar> getOk(@PathVariable Long id) {

        PlanoAlimentar planoAlimentar = planoAlimentarService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(planoAlimentar);
    }
    

    @PostMapping("/novo")
    public ResponseEntity<PlanoAlimentar> novoPlanoAlimentar(@RequestBody PlanoAlimentarDTO planoAlimentarDTO, HttpServletRequest request) {

        PlanoAlimentar novoPlanoAlimentar = planoAlimentarService.novoPlanoAlimentar(planoAlimentarDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlanoAlimentar);
    }

}
