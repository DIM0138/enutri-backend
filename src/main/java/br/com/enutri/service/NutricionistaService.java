package br.com.enutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.dto.NutricionistaDTO;

@Service
public class NutricionistaService {

    @Autowired
    public NutricionistaRepository nutricionistas;

    public Nutricionista save(NutricionistaDTO nutricionistaDTO){
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
    public List<Nutricionista> getAll(){
        return nutricionistas.findAll();
    }

    public Nutricionista getById(long id){
        return nutricionistas.getReferenceById(id);
    }

    public Boolean existsById(long id){
        return nutricionistas.existsById(id);
    }

    public Nutricionista atualizar(Long id, NutricionistaDTO nutricionistaDTO) {
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
         }).orElseThrow(() -> new RuntimeException(("Nutricionista não existe.")));
    }

    public void delete(long id){
        nutricionistas.deleteById(id);
    }

}
