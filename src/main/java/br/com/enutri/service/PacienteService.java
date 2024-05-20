package br.com.enutri.service;

import java.time.LocalDate;
import java.util.Optional;

import br.com.enutri.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.exception.UnauthorizedAccessException;
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
        return pacientesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente de id " + id + " não encontrado"));
    }

    public Paciente getPacienteByLogin(String login) throws ResourceNotFoundException {
        return pacientesRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente de login " + login + " não encontrado"));
    }

    public TokenCadastro generateNewToken(String nomePaciente, long idNutricionista) throws ResourceNotFoundException {
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

    public TokenCadastro getByToken(String token) throws ResourceNotFoundException {
            return tokensRepository.findById(token)
                    .orElseThrow(() -> new ResourceNotFoundException("Token " + token + " não encontrado"));
    }

    public Paciente preSignup(PacienteDTO pacienteDTO) {
        Paciente novoPaciente = new Paciente();

        novoPaciente.setNomeCompleto(pacienteDTO.getNomeCompleto());

        pacientesRepository.save(novoPaciente);

        return novoPaciente;
    }
    
    public Paciente signup(PacienteDTO pacienteDTO) throws ResourceNotFoundException {

            Paciente novoPaciente = getPacienteById(pacienteDTO.getId());
    
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
    
            return pacientesRepository.save(novoPaciente);
    }

    public Paciente login(String login, String senha) throws ResourceNotFoundException, UnauthorizedAccessException {
        Paciente paciente = getPacienteByLogin(login);

        if(!paciente.getSenha().equals(senha)) {
            throw new UnauthorizedAccessException("Accesso não permitido. Senha inválida.");
        }

        return paciente;
    }

    public Paciente update(Long id, PacienteDTO pacienteDTO) throws ResourceNotFoundException {
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

    public Boolean existsByLogin(String login){
        return pacientesRepository.existsByLogin(login);
    }

    public Boolean existsByEmail(String email){
        return pacientesRepository.existsByEmail(email);
    }

    public Boolean existsByCpf(String cpf){
        return pacientesRepository.existsByCPF(cpf);
    }

    public Paciente save(Paciente paciente) {
        return pacientesRepository.save(paciente);
    }

    public void delete(Long id) {
        pacientesRepository.deleteById(id);
    }

    public PlanoAlimentar getPlanoAlimentarAtual(Long idPaciente) {
        Paciente paciente = getPacienteById(idPaciente);

        return paciente.getPlanoAlimentarAtual();
    }

    public RegistroDiario getRegistroDiario(Long idPaciente, LocalDate data) {
        Paciente paciente = getPacienteById(idPaciente);
        PlanoAlimentar planoAlimentarAtual = paciente.getPlanoAlimentarAtual();
        RegistroDiario registroDiario = planoAlimentarAtual.getRegistroDiarioByDate(data);
        if (registroDiario != null) {
            return planoAlimentarAtual.getRegistroDiarioByDate(data);
        } else {
            throw new ResourceNotFoundException("Nenhum registro diário encontrado para essa data.");
        }
    }
}