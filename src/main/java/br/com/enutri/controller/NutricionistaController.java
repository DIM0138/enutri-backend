package br.com.enutri.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.dto.NutricionistaDTO;
import br.com.enutri.model.dto.PacienteDTO;
import br.com.enutri.service.NutricionistaService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/nutricionistas")
@Tag(name="Nutricionistas")
public class NutricionistaController {

    @Autowired
    private NutricionistaService nutricionistaService;

    @GetMapping("/{id}")
    public ResponseEntity<NutricionistaDTO> getNutricionista(@PathVariable long id) {

        Nutricionista nutricionistaConsultado = nutricionistaService.getNutricionistaById(id);
        NutricionistaDTO nutricionistaDTO = new NutricionistaDTO(nutricionistaConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionistaDTO);
    }

    @GetMapping("/{id}/pacientes")
    public ResponseEntity<List<PacienteDTO>> getPacientes(@PathVariable long id) {
        
        List<PacienteDTO> pacientes = nutricionistaService.getListaPacientes(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacientes);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<Nutricionista>> getAllNutricionistas() {

        List<Nutricionista> listaNutricionistas = nutricionistaService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(listaNutricionistas);
    }
    
    @PostMapping("/novo")
    public ResponseEntity<Nutricionista> addNutricionista(@RequestBody NutricionistaDTO nutricionistaDTO, HttpServletRequest request) {

        Nutricionista novoNutricionista = nutricionistaService.save(nutricionistaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoNutricionista);
    }

    @PatchMapping(path="/atualizar/{id}")
    public ResponseEntity<NutricionistaDTO> atualizarNutricionista(@PathVariable("id") Long id, @RequestBody NutricionistaDTO nutricionistaDTO) {

        if(!nutricionistaService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Nutricionista nutricionistaAtualizado = nutricionistaService.atualizar(id, nutricionistaDTO);
        NutricionistaDTO nutricionistaAtualizadoDTO = new NutricionistaDTO(nutricionistaAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionistaAtualizadoDTO);
    }

    @DeleteMapping(path="/deletar/{id}")
    public ResponseEntity<String> deleteReceita(@PathVariable("id") Long id) {

        if(!nutricionistaService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        nutricionistaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
