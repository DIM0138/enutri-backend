package br.com.enutri.controller;

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
import br.com.enutri.model.dto.PacienteDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable long id) {

        Paciente pacienteConsultado = pacienteService.getById(id);
        PacienteDTO pacienteDTO = new PacienteDTO(pacienteConsultado);

        return ResponseEntity.status(HttpStatus.OK).body(pacienteDTO);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Paciente>> retriveAllPacientes() {
        
        List<Paciente> listaPacientes = pacienteService.retriveAllPacientes();

        return ResponseEntity.status(HttpStatus.OK).body(listaPacientes);
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<String> signupPaciente(@RequestBody PacienteDTO pacienteDTO, HttpServletRequest request) {

        pacienteService.signup(pacienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<PacienteDTO> atualizarPaciente(@PathVariable("id") Long id, @RequestBody PacienteDTO pacienteDTO) {

        Paciente pacienteAtualizado = pacienteService.atualizar(id, pacienteDTO);
        PacienteDTO pacienteAtualizadoDTO = new PacienteDTO(pacienteAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(pacienteAtualizadoDTO);
    }

    @DeleteMapping(path="/deletar/{id}")
    public ResponseEntity<String> deletePaciente(@PathVariable("id") Long id) {
        pacienteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
