package br.com.enutri.model.dto;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Receita;
import lombok.*;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceitaDTO {
    private long id;
    private Nutricionista nutricionista;
    private Receita.TipoRefeicao tipoRefeicao;
    private String nome;
    private String descricao;
    private int tempoPreparo;
    private int calorias;
    private String imagemURL;
    private List<String> modoPreparo;
    private Map<String, String> listaIngredientes;
    private Boolean contemAlergicos;
    private List<String> alergicos;

    public ReceitaDTO(Receita receita){
        this.id = receita.getId();
        this.nutricionista = receita.getNutricionista();
        this.tipoRefeicao = receita.getTipoRefeicao();
        this.nome = receita.getDescricao();
        this.tempoPreparo = receita.getTempoPreparo();
        this.calorias = receita.getCalorias();
        this.imagemURL = receita.getImagemURL();
        this.modoPreparo = receita.getModoPreparo();
        this.listaIngredientes = receita.getListaIngredientes();
        this.contemAlergicos = receita.getContemAlergicos();
        this.alergicos = receita.getAlergicos();
    }

}
