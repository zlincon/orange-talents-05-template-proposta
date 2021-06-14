package br.com.zupacademy.lincon.proposta.associacarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociaCarteiraRequest {
    @NotBlank
    @Email
    private String email;

    public String getEmail() {
        return email;
    }
}
