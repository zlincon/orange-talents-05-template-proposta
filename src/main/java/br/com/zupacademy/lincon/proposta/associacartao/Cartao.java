package br.com.zupacademy.lincon.proposta.associacartao;

import br.com.zupacademy.lincon.proposta.associacarteira.CarteiraPaypal;
import br.com.zupacademy.lincon.proposta.associacarteira.CarteiraSamsungPay;
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
    //@CreditCardNumber(ignoreNonDigitCharacters = true)
    private String numero;
    //@ElementCollection
    //@Embedded
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Biometria> biometrias = new HashSet<>();
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<StatusUso> statusUsos = new LinkedList<>();
    @OneToOne(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private CarteiraPaypal carteiraPaypal;
    @OneToOne(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private CarteiraSamsungPay carteiraSamsungPay;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull @Valid Proposta proposta,
                  @NotBlank String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

    /**
     * @param userAgent navegador que solicitou o bloqueio
     * @param ipRemoto  ip da solicitação
     */
    @Transactional
    public void bloquear(@NotBlank String userAgent, @NotBlank String ipRemoto) {
        if (this.biometrias.isEmpty()) {
            throw new NegocioException("É necessário cadastro de biometria " +
                    "para bloquear cartão.",
                    HttpStatus.BAD_REQUEST);
        }
        Assert.state(!this.biometrias.isEmpty(), "Nenhum cartão pode ser " +
                "bloqueado se não tiver digital associada");
        if (isBloqueado()) {
            throw new NegocioException("Cartão já está com status: BLOQUEADO",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        this.statusUsos.add(new StatusUso(PossiveisStatusUso.BLOQUEADO, this
                , userAgent, ipRemoto));
    }

    public boolean isBloqueado() {
        return !this.statusUsos.isEmpty() && this.statusUsos.get(statusUsos.size() - 1).getStatusSolicitado().equals(PossiveisStatusUso.BLOQUEADO);
    }

    public String getNumero() {
        return numero;
    }

    public Optional<CarteiraPaypal> adicionaPaypal(String email) {
        if (this.carteiraPaypal != null) {
            return Optional.empty();
        }

        this.carteiraPaypal = new CarteiraPaypal(this, email);
        return Optional.of(this.carteiraPaypal);
    }

    public Optional<CarteiraSamsungPay> adicionaSamsungPay(String email) {
        if (this.carteiraSamsungPay != null) {
            return Optional.empty();
        }

        this.carteiraSamsungPay = new CarteiraSamsungPay(this, email);
        return Optional.of(this.carteiraSamsungPay);
    }
}
