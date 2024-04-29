package br.com.enutri.service;

import java.util.List;
import java.util.Optional;

import br.com.enutri.model.PlanoAlimentar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.TokenCadastro;
import br.com.enutri.model.dto.PacienteDTO;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.TokenCadastroRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PacienteService {

    @Autowired
    public PacienteRepository pacientesRepository;
    
    @Autowired
    private NutricionistaRepository nutricionistasRepository;

    @Autowired
    private TokenCadastroRepository tokensRepository;

    public Paciente getPacienteById(long id) throws ResourceNotFoundException {

        try {
            Paciente paciente = pacientesRepository.getReferenceById(id);
            return paciente;
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Paciente de id " + id + " não encontrado");
        }
   
    }

    public TokenCadastro generateNewToken(String nomePaciente, long idNutricionista){
        TokenCadastro novoToken = new TokenCadastro();
        
        boolean tokenExiste = tokensRepository.existsById(novoToken.getToken());

        while(tokenExiste) {
            novoToken.newToken();
        }

        TokenCadastro tokenSalvo = tokensRepository.save(novoToken);

        Paciente novoPaciente = new Paciente();
        novoPaciente.setNomeCompleto(nomePaciente);
        novoPaciente.setToken(tokenSalvo);

        tokenSalvo.setPaciente(novoPaciente);
        
        Nutricionista nutricionistaResponsavel;

        try {
            nutricionistaResponsavel = nutricionistasRepository.getReferenceById(idNutricionista);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Nutricionista com id " + idNutricionista + " não encontrado");
        } 
        
        novoPaciente.setNutricionistaResponsavel(nutricionistaResponsavel);
        nutricionistaResponsavel.getListaPacientes().add(novoPaciente);

        pacientesRepository.save(novoPaciente);
        nutricionistasRepository.save(nutricionistaResponsavel);

        return tokenSalvo;
    }

    public TokenCadastro getByToken(String token) {
        try {
            return tokensRepository.getReferenceById(token);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Token " + token + " não encontrado");
        }
    }

    public Paciente preSignup(PacienteDTO pacienteDTO) {
        Paciente novoPaciente = new Paciente();

        novoPaciente.setNomeCompleto(pacienteDTO.getNomeCompleto());

        pacientesRepository.save(novoPaciente);

        return novoPaciente;
    }
    
    public void signup(PacienteDTO pacienteDTO) {
        try {
            Paciente novoPaciente = pacientesRepository.getReferenceById(pacienteDTO.getId());
    
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
    
            pacientesRepository.save(novoPaciente);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Paciente de id " + pacienteDTO.getId() + " não encontrado");
        }
    }

    public Paciente update(Long id, PacienteDTO pacienteDTO) {
        return pacientesRepository.findById(id).map(pacienteExistente -> {
            Optional.ofNullable(pacienteDTO.getNomeCompleto()).ifPresent(pacienteExistente::setNomeCompleto);
            Optional.ofNullable(pacienteDTO.getGenero()).ifPresent(pacienteExistente::setGenero);
            Optional.ofNullable(pacienteDTO.getDataNascimento()).ifPresent(pacienteExistente::setDataNascimento);
            Optional.ofNullable(pacienteDTO.getEndereco()).ifPresent(pacienteExistente::setEndereco);
            Optional.ofNullable(pacienteDTO.getTelefone()).ifPresent(pacienteExistente::setTelefone);
            Optional.ofNullable(pacienteDTO.getEmail()).ifPresent(pacienteExistente::setEmail);
            Optional.ofNullable(pacienteDTO.getCpf()).ifPresent(pacienteExistente::setCPF);
            Optional.ofNullable(pacienteDTO.getLogin()).ifPresent(pacienteExistente::setLogin);
            Optional.ofNullable(pacienteDTO.getSenha()).ifPresent(pacienteExistente::setSenha);
            return pacientesRepository.save(pacienteExistente);
        }).orElseThrow(() -> new ResourceNotFoundException("Paciente de id " + id + " não encontrado"));
    }

    public List<Paciente> getAll() {
        return pacientesRepository.findAll();
    }

    public Paciente save(Paciente paciente) {
        return pacientesRepository.save(paciente);
    }

    public void delete(Long id) {
        pacientesRepository.deleteById(id);
    }

    public PlanoAlimentar getPlanoAlimentarAtual(Long idPaciente) {
        Paciente paciente = pacientesRepository.findById(idPaciente)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        return paciente.getPlanoAlimentarAtual();
    }
}