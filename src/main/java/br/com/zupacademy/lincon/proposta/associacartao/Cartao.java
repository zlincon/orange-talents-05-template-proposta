package br.com.zupacademy.lincon.proposta.associacartao;

import br.com.zupacademy.lincon.proposta.criabiometria.Biometria;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull @Valid Proposta proposta,
                  @NotBlank String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

}
