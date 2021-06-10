package br.com.zupacademy.lincon.proposta.criabiometria;

import javax.validation.constraints.NotBlank;

public class NovaBiometriaRequest {
    @NotBlank
    private String digital;

    @Deprecated
    public NovaBiometriaRequest() {
    }

    public NovaBiometriaRequest(String digital) {
        this.digital = digital;
    }

    public String getDigital() {
        return digital;
    }
}
