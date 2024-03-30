package br.com.enutri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.dto.NutricionistaDTO;
import br.com.enutri.service.NutricionistaService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/nutricionistas")
public class NutricionistaController {

    @Autowired
    private NutricionistaService nutricionistaService;

    @GetMapping("/{id}")
    public ResponseEntity<NutricionistaDTO> getNutricionista(@PathVariable long id) {

        Nutricionista nutricionistaConsultado = nutricionistaService.getById(id);
        NutricionistaDTO nutricionistaDTO = new NutricionistaDTO(nutricionistaConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionistaDTO);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<Nutricionista>> getAllNutricionistas() {

        List<Nutricionista> listaNutricionistas = nutricionistaService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(listaNutricionistas);
    }
    
    @PostMapping("/novo")
    public ResponseEntity<Nutricionista> addNutricionista(@RequestBody NutricionistaDTO nutricionistaDTO, HttpServletRequest request) {

        Nutricionista novoNutricionista = nutricionistaService.save(nutricionistaDTO);

        return ResponseEntity.status(HttpStatus.OK).body(novoNutricionista);
    }
}
