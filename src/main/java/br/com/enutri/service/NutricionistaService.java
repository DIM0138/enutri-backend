package br.com.enutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.exception.DuplicateResourceException;
import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.exception.UnauthorizedAccessException;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.dto.NutricionistaDTO;
import br.com.enutri.model.dto.PacienteDTO;

@Service
public class NutricionistaService {

    @Autowired
    public NutricionistaRepository nutricionistas;

    public Nutricionista save(NutricionistaDTO nutricionistaDTO){

        if(existsByLogin(nutricionistaDTO.getLogin())) {
            throw new DuplicateResourceException("O login " + nutricionistaDTO.getLogin() + " já está sendo usado");
        }

        if(existsByCpf(nutricionistaDTO.getCpf())) {
            throw new DuplicateResourceException("O CPF " + nutricionistaDTO.getCpf() + " já está sendo usado");
        }

        if(existsByEmail(nutricionistaDTO.getEmail())) {
            throw new DuplicateResourceException("O e-mail " + nutricionistaDTO.getEmail() + " já está sendo usado");
        }

        if(existsByCrn(nutricionistaDTO.getCRN())) {
            throw new DuplicateResourceException("O CRN " + nutricionistaDTO.getCRN() + " já está sendo usado");
        }

        Nutricionista novoNutricionista = new Nutricionista();

        novoNutricionista.setNomeCompleto(nutricionistaDTO.getNomeCompleto());
        novoNutricionista.setGenero(nutricionistaDTO.getGenero());
        novoNutricionista.setDataNascimento(nutricionistaDTO.getDataNascimento());
        novoNutricionista.setEndereco(nutricionistaDTO.getEndereco());
        novoNutricionista.setTelefone(nutricionistaDTO.getTelefone());
        novoNutricionista.setEmail(nutricionistaDTO.getEmail());
        novoNutricionista.setCPF(nutricionistaDTO.getCpf());
        novoNutricionista.setLogin(nutricionistaDTO.getLogin());
        novoNutricionista.setSenha(nutricionistaDTO.getSenha());
        novoNutricionista.setCRN(nutricionistaDTO.getCRN());
        novoNutricionista.setFormacao(nutricionistaDTO.getFormacao());
        novoNutricionista.setEspecialidade(nutricionistaDTO.getEspecialidade());
        novoNutricionista.setEnderecoProfissional(nutricionistaDTO.getEnderecoProfissional());

        nutricionistas.save(novoNutricionista);

        return novoNutricionista;
    }

    public List<PacienteDTO> getListaPacientes(Long id) {
        Nutricionista nutricionista = getNutricionistaById(id);
        return nutricionista.getListaPacientes().stream().map(PacienteDTO::new).toList();
    }

    public Nutricionista login(String login, String senha) throws ResourceNotFoundException, UnauthorizedAccessException {
        Nutricionista nutricionista = getNutricionistaByLogin(login);

        if(!nutricionista.getSenha().equals(senha)) {
            throw new UnauthorizedAccessException("Accesso não permitido. Senha inválida.");
        }

        return nutricionista;
    }

    public Nutricionista getNutricionistaById(long id) throws ResourceNotFoundException{
        return nutricionistas.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nutricionista de id " + id + " não encontrado"));
    }

    public Nutricionista getNutricionistaByLogin(String login) throws ResourceNotFoundException{
            return nutricionistas.findByLogin(login)
                    .orElseThrow(() -> new ResourceNotFoundException("Nutricionista de login " + login + " não encontrado"));
    }

    public Boolean existsById(long id){
        return nutricionistas.existsById(id);
    }

    public Boolean existsByLogin(String login){
        return nutricionistas.existsByLogin(login);
    }

    public Boolean existsByEmail(String email){
        return nutricionistas.existsByEmail(email);
    }

    public Boolean existsByCpf(String cpf){
        return nutricionistas.existsByCPF(cpf);
    }

    public Boolean existsByCrn(String crn){
        return nutricionistas.existsByCRN(crn);
    }

    public Nutricionista atualizar(Long id, NutricionistaDTO nutricionistaDTO) {

        Nutricionista nutricionista = getNutricionistaById(id);

        if(existsByLogin(nutricionistaDTO.getLogin()) && !nutricionista.getLogin().equals(nutricionistaDTO.getLogin())) {
            throw new DuplicateResourceException("O login " + nutricionistaDTO.getLogin() + " já está sendo usado");
        }

        if(existsByCpf(nutricionistaDTO.getCpf()) && !nutricionista.getCPF().equals(nutricionistaDTO.getCpf())) {
            throw new DuplicateResourceException("O CPF " + nutricionistaDTO.getCpf() + " já está sendo usado");
        }

        if(existsByEmail(nutricionistaDTO.getEmail()) && !nutricionista.getEmail().equals(nutricionistaDTO.getEmail())) {
            throw new DuplicateResourceException("O e-mail " + nutricionistaDTO.getEmail() + " já está sendo usado");
        }

        if(existsByCrn(nutricionistaDTO.getCRN()) && !nutricionista.getCRN().equals(nutricionistaDTO.getCRN())) {
            throw new DuplicateResourceException("O CRN " + nutricionistaDTO.getCRN() + " já está sendo usado");
        }
        
        return nutricionistas.findById(id).map(nutricionistaExistente -> {
             Optional.ofNullable(nutricionistaDTO.getNomeCompleto()).ifPresent(nutricionistaExistente::setNomeCompleto);
             Optional.ofNullable(nutricionistaDTO.getGenero()).ifPresent(nutricionistaExistente::setGenero);
             Optional.ofNullable(nutricionistaDTO.getDataNascimento()).ifPresent(nutricionistaExistente::setDataNascimento);
             Optional.ofNullable(nutricionistaDTO.getEndereco()).ifPresent(nutricionistaExistente::setEndereco);
             Optional.ofNullable(nutricionistaDTO.getTelefone()).ifPresent(nutricionistaExistente::setTelefone);
             Optional.ofNullable(nutricionistaDTO.getEmail()).ifPresent(nutricionistaExistente::setEmail);
             Optional.ofNullable(nutricionistaDTO.getCpf()).ifPresent(nutricionistaExistente::setCPF);
             Optional.ofNullable(nutricionistaDTO.getLogin()).ifPresent(nutricionistaExistente::setLogin);
             Optional.ofNullable(nutricionistaDTO.getSenha()).ifPresent(nutricionistaExistente::setSenha);
             Optional.ofNullable(nutricionistaDTO.getCRN()).ifPresent(nutricionistaExistente::setCRN);
             Optional.ofNullable(nutricionistaDTO.getFormacao()).ifPresent(nutricionistaExistente::setFormacao);
             Optional.ofNullable(nutricionistaDTO.getEspecialidade()).ifPresent(nutricionistaExistente::setEspecialidade);
             Optional.ofNullable(nutricionistaDTO.getEnderecoProfissional()).ifPresent(nutricionistaExistente::setEnderecoProfissional);
             return nutricionistas.save(nutricionistaExistente);
         }).orElseThrow(() -> new ResourceNotFoundException("Nutricionista de id " + id + " não encontrado"));
    }

    public void delete(long id){
        Nutricionista nutricionista = getNutricionistaById(id);
        nutricionistas.delete(nutricionista);
    }
}
