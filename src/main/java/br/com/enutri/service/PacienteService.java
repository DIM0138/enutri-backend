package br.com.enutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.TokenCadastro;
import br.com.enutri.model.dto.PacienteDTO;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.TokenCadastroRepository;

@Service
public class PacienteService {

    @Autowired
    public PacienteRepository pacientesRepository;
    
    @Autowired
    private NutricionistaRepository nutricionistasRepository;

    @Autowired
    private TokenCadastroRepository tokensRepository;

    public TokenCadastro generateNewToken(String nomePaciente, long idNutricionista) {
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

        Nutricionista nutricionistaResponsavel = nutricionistasRepository.getReferenceById(idNutricionista);
        
        novoPaciente.setNutricionistaResponsavel(nutricionistaResponsavel);
        nutricionistaResponsavel.getListaPacientes().add(novoPaciente);

        pacientesRepository.save(novoPaciente);
        nutricionistasRepository.save(nutricionistaResponsavel);

        return tokenSalvo;
    }

    public TokenCadastro getByToken(String token) {
        return tokensRepository.getReferenceById(token);
    }

    public Paciente preSignup(PacienteDTO pacienteDTO) {
        Paciente novoPaciente = new Paciente();

        novoPaciente.setNomeCompleto(pacienteDTO.getNomeCompleto());

        pacientesRepository.save(novoPaciente);

        return novoPaciente;
    }
    
    public void signup(PacienteDTO pacienteDTO) {
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
        }).orElseThrow(() -> new RuntimeException(("Paciente não existe.")));
    }

    public List<Paciente> getAll() {
        return pacientesRepository.findAll();
    }

    public Paciente getById(long id) {
        return pacientesRepository.getReferenceById(id);
    }

    public void delete(Long id) {
        pacientesRepository.deleteById(id);
    }
}
