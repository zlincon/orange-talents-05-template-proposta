package br.com.zupacademy.lincon.proposta.novaproposta;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    private String email;
    private String nome;
    @NotBlank
    private String endereco;
    @Positive
    private BigDecimal salario;
    @CPForCNPJ
    @NotBlank
    private String documento;

    public Proposta(String email,
                    String nome,
                    String endereco,
                    BigDecimal salario,
                    String documento) {
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.documento = documento;
    }

    public Long getId() {
        Assert.notNull(id, "O objeto não possui id.");
        return id;
    }
}
