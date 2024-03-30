package br.com.enutri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.TokenCadastro;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.PacienteRepository;
import br.com.enutri.repository.TokenCadastroRepository;

@Service
public class TokenCadastroService {

    @Autowired
    private TokenCadastroRepository tokens;

    @Autowired
    private NutricionistaRepository nutricionistas;

    @Autowired
    private PacienteRepository pacientes;

    public TokenCadastro generateNewToken(String nomePaciente, long idNutricionista) {
        TokenCadastro novoToken = new TokenCadastro();
        
        boolean tokenExiste = tokens.existsById(novoToken.getToken());

        while(tokenExiste) {
            novoToken.newToken();
        }

        TokenCadastro tokenSalvo = tokens.save(novoToken);

        Paciente novoPaciente = new Paciente();
        novoPaciente.setNomeCompleto(nomePaciente);
        novoPaciente.setToken(tokenSalvo);

        tokenSalvo.setPaciente(novoPaciente);

        Nutricionista nutricionistaResponsavel = nutricionistas.getReferenceById(idNutricionista);
        
        novoPaciente.setNutricionistaResponsavel(nutricionistaResponsavel);
        nutricionistaResponsavel.getListaPacientes().add(novoPaciente);

        pacientes.save(novoPaciente);
        nutricionistas.save(nutricionistaResponsavel);

        return tokenSalvo;
    }

    public TokenCadastro getByToken(String token) {
        return tokens.getReferenceById(token);
    }
    
}
