package br.com.enutri.service;

import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.ReceitaDTO;
import br.com.enutri.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
