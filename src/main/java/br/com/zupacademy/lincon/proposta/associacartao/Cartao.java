package br.com.zupacademy.lincon.proposta.associacartao;

import br.com.zupacademy.lincon.proposta.bloqueiocartao.PossiveisStatusUso;
import br.com.zupacademy.lincon.proposta.bloqueiocartao.StatusUso;
import br.com.zupacademy.lincon.proposta.criabiometria.Biometria;
import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Proposta proposta;
    @NotBlank
//    @CreditCardNumber(ignoreNonDigitCharacters = true)
    private String numero;
//    @ElementCollection
//    @Embedded
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Biometria> biometrias = new HashSet<>();
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<StatusUso> statusUsos = new LinkedList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull @Valid Proposta proposta,
                  @NotBlank String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

    /**
     *
     * @param userAgent navegador que solicitou o bloqueio
     * @param ipRemoto ip da solicitação
     */
    @Transactional
    public void bloquear(@NotBlank String userAgent,@NotBlank String ipRemoto) {
        if(this.biometrias.isEmpty()) {
            throw new NegocioException("É necessário cadastro de biometria " +
                    "para bloquear cartão.",
                    HttpStatus.BAD_REQUEST);
        }
        Assert.state(!this.biometrias.isEmpty(), "Nenhum cartão pode ser " +
                "bloqueado se não tiver digital associada");
        if(!this.statusUsos.isEmpty() && this.statusUsos.get(statusUsos.size() - 1).getStatusSolicitado().equals(PossiveisStatusUso.BLOQUEADO)){
            throw new NegocioException("Cartão já está com status: BLOQUEADO",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        this.statusUsos.add(new StatusUso(PossiveisStatusUso.BLOQUEADO, this
                , userAgent, ipRemoto));
    }

    public String getNumero() {
        return numero;
    }
}
