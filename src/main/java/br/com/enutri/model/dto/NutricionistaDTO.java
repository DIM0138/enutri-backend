package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import br.com.enutri.model.dto.validation.OnCreate;
import br.com.enutri.model.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NutricionistaDTO {

    @NotNull(message = "O ID deve ser informado.", groups = OnUpdate.class)
    private long id;

    @NotBlank(message = "O nome completo deve ser informado.", groups = OnCreate.class)
    private String nomeCompleto;

    @NotBlank(message = "O gênero deve ser informado.", groups = OnCreate.class)
    private String genero;

    @NotNull(message = "A data de nascimento deve ser informada.", groups = OnCreate.class)
    private LocalDate dataNascimento;

    private String endereco;

    @NotBlank(message = "O telefone deve ser informado.", groups = OnCreate.class)
    private String telefone;

    @Email(message = "Um e-mail válido deve ser informado.", groups = {OnCreate.class, OnUpdate.class})
    @NotBlank(message = "O e-mail deve ser informado.", groups = OnCreate.class)
    private String email;

    @NotBlank(message = "O CPF deve ser informado.", groups = OnCreate.class)
    private String cpf;

    @NotBlank(message = "O login deve ser informado.", groups = OnCreate.class)
    private String login;

    @NotBlank(message = "A senha deve ser informada.", groups = OnCreate.class)
    private String senha;

    @NotBlank(message = "O CRN deve ser informado.", groups = OnCreate.class)
    private String CRN;

    @NotBlank(message = "A formação deve ser informada.", groups = OnCreate.class)
    private String formacao;

    @NotBlank(message = "A especialidade deve ser informada.", groups = OnCreate.class)
    private String especialidade;

    @NotBlank(message = "O endereço profissional deve ser informado.", groups = OnCreate.class)
    private String enderecoProfissional;

    private List<Long> listaPacientesIds;

    public NutricionistaDTO() {}
    
    public NutricionistaDTO(Nutricionista nutricionistaConsultado) {
        this.id = nutricionistaConsultado.getId();
        this.nomeCompleto = nutricionistaConsultado.getNomeCompleto();
        this.genero = nutricionistaConsultado.getGenero();
        this.dataNascimento = nutricionistaConsultado.getDataNascimento();
        this.endereco = nutricionistaConsultado.getEndereco();
        this.telefone = nutricionistaConsultado.getTelefone();
        this.email = nutricionistaConsultado.getEmail();
        this.cpf = nutricionistaConsultado.getCPF();
        this.login = nutricionistaConsultado.getLogin();
        this.senha = nutricionistaConsultado.getSenha();
        this.CRN = nutricionistaConsultado.getCRN();
        this.formacao = nutricionistaConsultado.getFormacao();
        this.especialidade = nutricionistaConsultado.getEspecialidade();
        this.enderecoProfissional = nutricionistaConsultado.getEnderecoProfissional();
        this.listaPacientesIds = nutricionistaConsultado.getListaPacientes().stream().map(Paciente::getId).toList();
    }
}
