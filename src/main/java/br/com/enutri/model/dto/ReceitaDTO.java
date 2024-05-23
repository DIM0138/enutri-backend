package br.com.enutri.model.dto;

import br.com.enutri.model.IngredienteReceita;
import br.com.enutri.model.Receita;
import br.com.enutri.model.dto.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O ID do nutricionista responsável deve ser informado.", groups = OnCreate.class)
    private long nutricionista;

    @NotNull(message = "O tipo de refeição deve ser informado.", groups = OnCreate.class)
    private Receita.TipoRefeicao tipoRefeicao;

    @NotBlank(message = "O nome da receita deve ser informado.", groups = OnCreate.class)
    private String nome;

    private String descricao;

    @NotNull(message = "O tempo de preparo deve ser informado.", groups = OnCreate.class)
    private int tempoPreparo;

    @NotNull(message = "A quantidade de calorias deve ser informada.", groups = OnCreate.class)
    private int calorias;

    private String imagemURL;

    @NotEmpty(message = "Pelo menos um passo deve ser informado.", groups = OnCreate.class)
    private List<String> modoPreparo;

    @NotEmpty(message = "Pelo menos um ingrediente deve ser informado.", groups = OnCreate.class)
    private List<IngredienteReceita> ingredientes;

    @NotNull(message = "A presença ou não de alérgicos deve ser informada.", groups = OnCreate.class)
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