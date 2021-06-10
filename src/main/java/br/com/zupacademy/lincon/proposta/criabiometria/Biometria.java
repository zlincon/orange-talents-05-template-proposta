package br.com.zupacademy.lincon.proposta.criabiometria;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;

import javax.persistence.*;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Base64;

//@Embeddable
@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] digital;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;
    private final OffsetDateTime instante = OffsetDateTime.now();

    @Deprecated
    public Biometria() {
    }

    public Biometria(Cartao cartao, String digital) {
        this.digital = Base64.getEncoder().encode(digital.getBytes(StandardCharsets.UTF_8));
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
