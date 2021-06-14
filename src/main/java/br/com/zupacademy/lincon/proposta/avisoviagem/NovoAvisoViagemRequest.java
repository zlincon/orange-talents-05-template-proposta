package br.com.zupacademy.lincon.proposta.avisoviagem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {
    @NotBlank
    @JsonProperty
    private String destino;
    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private LocalDate validoAte;

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }

    @Override
    public String toString() {
        return "NovoAvisoViagemRequest{" +
                "destino='" + destino + '\'' +
                ", validoAte=" + validoAte +
                '}';
    }
}
