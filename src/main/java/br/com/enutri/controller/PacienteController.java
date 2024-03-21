package br.com.enutri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import br.com.enutri.repository.PacienteRepository;
import jakarta.servlet.http.HttpServletRequest;
import br.com.enutri.model.Paciente;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacientes;

    @GetMapping("/todos")
    public List<Paciente> retriveAllPacientes() {
        return pacientes.findAll();
    }
    
    @PostMapping("/")
    public ResponseEntity<Paciente> addPaciente(@RequestBody Paciente paciente, HttpServletRequest request) {
        pacientes.save(paciente);

        return ResponseEntity.status(HttpStatus.OK).body(paciente);
    }
    
}
