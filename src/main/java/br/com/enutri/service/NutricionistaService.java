package br.com.enutri.service;

import java.util.List;

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
}
