package br.com.enutri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Paciente;
import br.com.enutri.model.dto.PacienteDTO;
import br.com.enutri.repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    public PacienteRepository pacientes;

    public Paciente preSignup(PacienteDTO pacienteDTO) {
        Paciente novoPaciente = new Paciente();
        
        novoPaciente.setNomeCompleto(pacienteDTO.getNomeCompleto());

        pacientes.save(novoPaciente);

        return novoPaciente;
    }

    public void signup(PacienteDTO pacienteDTO) {
        Paciente novoPaciente = pacientes.getReferenceById(pacienteDTO.getId());

        novoPaciente.setNomeCompleto(pacienteDTO.getNomeCompleto());
        novoPaciente.setGenero(pacienteDTO.getGenero());
        novoPaciente.setDataNascimento(pacienteDTO.getDataNascimento());
        novoPaciente.setEndereco(pacienteDTO.getEndereco());
        novoPaciente.setTelefone(pacienteDTO.getTelefone());
        novoPaciente.setEmail(pacienteDTO.getEmail());
        novoPaciente.setCPF(pacienteDTO.getCpf());
        novoPaciente.setLogin(pacienteDTO.getLogin());
        novoPaciente.setSenha(pacienteDTO.getSenha());
        novoPaciente.getToken().setUsado(true);

        pacientes.save(novoPaciente);
    }

    public List<Paciente> retriveAllPacientes() {
        return pacientes.findAll();
    }

    public Paciente getById(long id) {
        return pacientes.getReferenceById(id);
    }
}
