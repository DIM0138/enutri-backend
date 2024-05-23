package br.com.enutri.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import br.com.enutri.model.dto.validation.OnCreate;
import br.com.enutri.model.dto.validation.OnUpdate;
import br.com.enutri.service.NutricionistaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/nutricionistas")
@Tag(name="Nutricionistas")
@Validated
public class NutricionistaController {

    @Autowired
    private NutricionistaService nutricionistaService;

    @GetMapping("/{id}")
    public ResponseEntity<NutricionistaDTO> getNutricionista(@PathVariable long id) {

        Nutricionista nutricionistaConsultado = nutricionistaService.getNutricionistaById(id);
        NutricionistaDTO nutricionistaDTO = new NutricionistaDTO(nutricionistaConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionistaDTO);
    }

    @GetMapping("/login/")
    public ResponseEntity<NutricionistaDTO> login(@RequestParam String login, @RequestParam String senha) {

        Nutricionista nutricionistaLogado = nutricionistaService.login(login, senha);
        NutricionistaDTO nutricionistaDTO = new NutricionistaDTO(nutricionistaLogado);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionistaDTO);
    }

    @GetMapping("/existe/login")
    public ResponseEntity<Boolean> checkNutricionistaLogin(@RequestParam String login) {
        Boolean loginExists = nutricionistaService.existsByLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(loginExists);
    }

    @GetMapping("/existe/email")
    public ResponseEntity<Boolean> checkNutricionistaEmail(@RequestParam String email) {
        Boolean loginExists = nutricionistaService.existsByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(loginExists);
    }

    @GetMapping("/existe/cpf")
    public ResponseEntity<Boolean> checkNutricionistaCpf(@RequestParam String cpf) {
        Boolean loginExists = nutricionistaService.existsByCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(loginExists);
    }

    @GetMapping("/existe/crn")
    public ResponseEntity<Boolean> checkNutricionistaCrn(@RequestParam String crn) {
        Boolean loginExists = nutricionistaService.existsByCrn(crn);
        return ResponseEntity.status(HttpStatus.OK).body(loginExists);
    }
    

    @GetMapping("/{id}/pacientes")
    public ResponseEntity<List<PacienteDTO>> getPacientes(@PathVariable long id) {
        
        List<PacienteDTO> pacientes = nutricionistaService.getListaPacientes(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacientes);
    }
    
    @PostMapping("/novo")
    @Validated(OnCreate.class)
    public ResponseEntity<Nutricionista> addNutricionista(@Valid @RequestBody NutricionistaDTO nutricionistaDTO, HttpServletRequest request) {

        Nutricionista novoNutricionista = nutricionistaService.save(nutricionistaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoNutricionista);
    }

    @PatchMapping(path="/atualizar/{id}")
    @Validated(OnUpdate.class)
    public ResponseEntity<NutricionistaDTO> atualizarNutricionista(@PathVariable("id") Long id, @Valid @RequestBody NutricionistaDTO nutricionistaDTO) {

        Nutricionista nutricionistaAtualizado = nutricionistaService.atualizar(id, nutricionistaDTO);
        NutricionistaDTO nutricionistaAtualizadoDTO = new NutricionistaDTO(nutricionistaAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(nutricionistaAtualizadoDTO);
    }

    @DeleteMapping(path="/deletar/{id}")
    public ResponseEntity<String> deleteReceita(@PathVariable("id") Long id) {

        nutricionistaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
