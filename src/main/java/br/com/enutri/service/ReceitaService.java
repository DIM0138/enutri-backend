package br.com.enutri.service;

import br.com.enutri.exception.DeleteOperationException;
import br.com.enutri.exception.ResourceNotFoundException;
import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.repository.NutricionistaRepository;
import br.com.enutri.repository.ReceitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    public Receita save(ReceitaDTO receitaDTO) {
        long idNutricionista = receitaDTO.getNutricionista();
        Optional<Nutricionista> nutricionistaExistente = nutricionistaRepository.findById(idNutricionista);
        return nutricionistaExistente.map(nutricionista -> {
            Receita novaReceita = Receita.builder()
                    .id(receitaDTO.getId())
                    .nutricionista(nutricionista)
                    .tipoRefeicao(receitaDTO.getTipoRefeicao())
                    .nome(receitaDTO.getNome())
                    .descricao(receitaDTO.getDescricao())
                    .tempoPreparo(receitaDTO.getTempoPreparo())
                    .calorias(receitaDTO.getCalorias())
                    .imagemURL(receitaDTO.getImagemURL())
                    .modoPreparo(receitaDTO.getModoPreparo())
                    .listaIngredientes(receitaDTO.getListaIngredientes())
                    .contemAlergicos(receitaDTO.getContemAlergicos())
                    .alergicos(receitaDTO.getAlergicos())
                    .build();

            return receitaRepository.save(novaReceita);
        }).orElseThrow(() -> new ResourceNotFoundException("Nutricionista com não encontrado."));
    }

    public Receita atualizar(Long id, ReceitaDTO receitaDTO) {
         return receitaRepository.findById(id).map(receitaExistente -> {
             Optional.ofNullable(receitaDTO.getTipoRefeicao()).ifPresent(receitaExistente::setTipoRefeicao);
             Optional.ofNullable(receitaDTO.getNome()).ifPresent(receitaExistente::setNome);
             Optional.ofNullable(receitaDTO.getDescricao()).ifPresent(receitaExistente::setDescricao);
             Optional.ofNullable(receitaDTO.getTempoPreparo()).ifPresent(receitaExistente::setTempoPreparo);
             Optional.ofNullable(receitaDTO.getCalorias()).ifPresent(receitaExistente::setCalorias);
             Optional.ofNullable(receitaDTO.getImagemURL()).ifPresent(receitaExistente::setImagemURL);
             Optional.ofNullable(receitaDTO.getModoPreparo()).ifPresent(receitaExistente::setModoPreparo);
             Optional.ofNullable(receitaDTO.getListaIngredientes()).ifPresent(receitaExistente::setListaIngredientes);
             Optional.ofNullable(receitaDTO.getContemAlergicos()).ifPresent(receitaExistente::setContemAlergicos);
             Optional.ofNullable(receitaDTO.getAlergicos()).ifPresent(receitaExistente::setAlergicos);
             return receitaRepository.save(receitaExistente);
         }).orElseThrow(() -> new ResourceNotFoundException("Receita "+id+" não encontrada."));
    }

    public List<ReceitaDTO> retriveAllReceitas() {
        return receitaRepository.findAll().stream().map(ReceitaDTO::new).toList();
    }

    public void delete(Long id) {
        if (!receitaRepository.existsById(id)){
            throw new ResourceNotFoundException("Receita não encontrada para remoção");
        }
        try {
            receitaRepository.deleteById(id);
        } catch (Exception e){
            throw new DeleteOperationException("Não foi possível deletar a receita com id: " + id);
        }
    }

    public Receita getById (long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita "+id+" não encontrada."));
    }
}