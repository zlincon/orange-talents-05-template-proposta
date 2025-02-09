package br.com.zupacademy.lincon.proposta.sistemasexternos;

import br.com.zupacademy.lincon.proposta.novaproposta.CPForCNPJ;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NovoDocumentoRequest {

    @CPForCNPJ
    private String documento;

    @Deprecated
    public NovoDocumentoRequest() {

    }

    public NovoDocumentoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
    }

    public String getDocumento() {
        return documento;
    }
}
