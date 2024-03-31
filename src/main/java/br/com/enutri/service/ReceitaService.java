package br.com.enutri.service;

import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Transactional
    public Receita save(ReceitaDTO receitaDTO) {

        Receita novaReceita = Receita.builder()
                .id(receitaDTO.getId())
                .nutricionista(receitaDTO.getNutricionista())
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
    }

    public boolean isExists(Long id) {
        return receitaRepository.existsById(id);
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
         }).orElseThrow(() -> new RuntimeException(("Receita não existe")));
    }

    public List<ReceitaDTO> retriveAllReceitas() {
        return receitaRepository.findAll().stream().map(ReceitaDTO::new).toList();
    }

    public void delete(Long id) {
        receitaRepository.deleteById(id);
    }
}
