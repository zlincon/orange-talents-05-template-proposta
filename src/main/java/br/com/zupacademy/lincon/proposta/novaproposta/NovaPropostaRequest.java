package br.com.zupacademy.lincon.proposta.novaproposta;

import br.com.zupacademy.lincon.proposta.compartilhado.UniqueValue;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String endereco;
    @NotNull
    @Positive
    private final BigDecimal salario;
    @NotBlank
    @CPForCNPJ
    private final String documento;

    public NovaPropostaRequest(String email, String nome, String endereco,
                               BigDecimal salario, String documento) {
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.documento = documento;
    }


    public Proposta toModel() {
        return new Proposta(email, nome, endereco, salario, documento);
    }

    public String getDocumento() {
        return documento;
    }
}
