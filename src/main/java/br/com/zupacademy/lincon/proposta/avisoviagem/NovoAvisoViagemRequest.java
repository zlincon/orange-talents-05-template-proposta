package br.com.zupacademy.lincon.proposta.avisoviagem;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {
    @NotBlank
    private String destino;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataTermino;

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public String getDestino() {
        return destino;
    }
}
