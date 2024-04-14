package br.com.enutri.model.dto;

import br.com.enutri.model.IngredienteReceita;
import br.com.enutri.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceitaDTO {
    private long id;
    private long nutricionista;
    private Receita.TipoRefeicao tipoRefeicao;
    private String nome;
    private String descricao;
    private int tempoPreparo;
    private int calorias;
    private String imagemURL;
    private List<String> modoPreparo;
    private List<IngredienteReceita> ingredientes;
    private Boolean contemAlergicos;
    private List<String> alergicos;

    public ReceitaDTO(Receita receita){
        this.id = receita.getId();
        this.nutricionista = receita.getNutricionista().getId();
        this.tipoRefeicao = receita.getTipoRefeicao();
        this.nome = receita.getNome();
        this.descricao = receita.getDescricao();
        this.tempoPreparo = receita.getTempoPreparo();
        this.calorias = receita.getCalorias();
        this.imagemURL = receita.getImagemURL();
        this.modoPreparo = receita.getModoPreparo();
        this.ingredientes = receita.getIngredientes();
        this.contemAlergicos = receita.getContemAlergicos();
        this.alergicos = receita.getAlergicos();
    }
}