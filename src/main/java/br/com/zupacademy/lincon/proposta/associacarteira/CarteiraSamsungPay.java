package br.com.zupacademy.lincon.proposta.associacarteira;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;

import javax.persistence.*;

@Entity
public class CarteiraSamsungPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cartao cartao;
    private String email;

    @Deprecated
    public CarteiraSamsungPay() {
    }

    public CarteiraSamsungPay(Cartao cartao, String email) {
        this.cartao = cartao;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
}
