package br.com.enutri.controller;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.dto.PlanoAlimentarDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import br.com.enutri.service.PacienteService;
import jakarta.servlet.http.HttpServletRequest;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.TokenCadastro;
import br.com.enutri.model.dto.PacienteDTO;
import br.com.enutri.model.dto.TokenCadastroDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/pacientes")
@Tag(name="Paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // TOKENS

    @GetMapping("/token/novo")
    public ResponseEntity<TokenCadastroDTO> generateToken(@RequestParam String nomePaciente, @RequestParam String idNutricionista) {

        TokenCadastro novoToken = pacienteService.generateNewToken(nomePaciente, Long.parseLong(idNutricionista));
        TokenCadastroDTO tokenDTO = new TokenCadastroDTO(novoToken);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<TokenCadastroDTO> getToken(@PathVariable String token) {
        
        TokenCadastro tokenConsultado = pacienteService.getByToken(token);
        TokenCadastroDTO tokenDTO = new TokenCadastroDTO(tokenConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }

    // PACIENTES

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable long id) {

        Paciente pacienteConsultado = pacienteService.getPacienteById(id);
        PacienteDTO pacienteDTO = new PacienteDTO(pacienteConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(pacienteDTO);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Paciente>> getAll() {
        
        List<Paciente> listaPacientes = pacienteService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(listaPacientes);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> signupPaciente(@RequestBody PacienteDTO pacienteDTO, HttpServletRequest request) {

        pacienteService.signup(pacienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<PacienteDTO> atualizarPaciente(@PathVariable("id") Long id, @RequestBody PacienteDTO pacienteDTO) {

        Paciente pacienteAtualizado = pacienteService.update(id, pacienteDTO);
        PacienteDTO pacienteAtualizadoDTO = new PacienteDTO(pacienteAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(pacienteAtualizadoDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletePaciente(@PathVariable("id") Long id) {
        pacienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/plano-alimentar")
    public ResponseEntity<PlanoAlimentarDTO> getPlanoAlimentarPaciente(@PathVariable("id") Long id){
        PlanoAlimentar planoAlimentarPaciente = pacienteService.getPlanoAlimentarAtual(id);
        PlanoAlimentarDTO planoAlimentarDTO = new PlanoAlimentarDTO(planoAlimentarPaciente);
        return ResponseEntity.status(HttpStatus.OK).body(planoAlimentarDTO);
    }
}
