package br.com.zupacademy.lincon.proposta.novaproposta;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusAvaliacaoProposta statusAvaliacao;
    @OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

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
        this.statusAvaliacao = StatusAvaliacaoProposta.NAO_ELEGIVEL;
    }

    public Long getId() {
        Assert.notNull(id, "O objeto não possui id.");
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public void atualizaStatus(StatusAvaliacaoProposta avaliacao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.NAO_ELEGIVEL), "Não pode mais trocar uma vez que a proposta é elegível.");
        this.statusAvaliacao = avaliacao;
    }

    public void associaCartao(String numero) {
        Assert.isNull(cartao, "Já associaou o cartão");
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.ELEGIVEL), "Não rola associar cartão com proposta nao elegível");
        this.cartao = new Cartao(this, numero);
    }
}
