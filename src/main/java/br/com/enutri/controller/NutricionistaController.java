package br.com.enutri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.repository.NutricionistaRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/nutricionistas")
public class NutricionistaController {

    @Autowired
    private NutricionistaRepository nutricionistas;

    @GetMapping("/todos")
    public List<Nutricionista> retriveAllNutricionistas() {
        return nutricionistas.findAll();
    }
    
    @PostMapping("/")
    public ResponseEntity<Nutricionista> addNutricionista(@RequestBody Nutricionista nutricionista, HttpServletRequest request) {
        nutricionistas.save(nutricionista);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionista);
    }
}
