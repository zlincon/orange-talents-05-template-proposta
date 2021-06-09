package br.com.zupacademy.lincon.proposta.acompanhaproposta;

import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;
import br.com.zupacademy.lincon.proposta.novaproposta.StatusAvaliacaoProposta;

public class DetalhePropostaResponse {
    private Long id;
    private String email;
    private String nome;
    private String documento;
    private StatusAvaliacaoProposta status;

    public DetalhePropostaResponse(Proposta proposta) {
        this.status = proposta.getStatusAvaliacao();
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.id = proposta.getId();
    }

    public StatusAvaliacaoProposta getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }
}
