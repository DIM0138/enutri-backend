package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.enutri.model.Paciente;
import br.com.enutri.model.Relatorio;
import br.com.enutri.model.dto.validation.OnCreate;
import br.com.enutri.model.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PacienteDTO{

    @NotNull(message = "O ID deve ser informado.", groups = OnCreate.class )
    private long id;

    @NotBlank(message = "O nome completo deve ser informado.", groups = OnCreate.class)
    private String nomeCompleto;

    @NotBlank(message = "O gênero deve ser informado.", groups = OnCreate.class)
    private String genero;

    @NotNull(message = "A data de nascimento deve ser informada.", groups = OnCreate.class)
    private LocalDate dataNascimento;

    private String endereco;

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

    private long nutricionistaResponsavelId;

    private List<Long> listaRelatoriosIds;

    @NotBlank(message = "O token deve ser informado.", groups = OnCreate.class)
    private String token;

    public PacienteDTO() {}

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nomeCompleto = paciente.getNomeCompleto();
        this.genero = paciente.getGenero();
        this.dataNascimento = paciente.getDataNascimento();
        this.endereco = paciente.getEndereco();
        this.telefone = paciente.getTelefone();
        this.email = paciente.getEmail();
        this.cpf = paciente.getCPF();
        this.login = paciente.getLogin();
        this.senha = paciente.getSenha();
        this.nutricionistaResponsavelId = paciente.getNutricionistaResponsavel().getId();
        this.listaRelatoriosIds = paciente.getListaRelatorios().stream().map(Relatorio::getId).toList();
        this.token = paciente.getToken().getToken();
    }
}
