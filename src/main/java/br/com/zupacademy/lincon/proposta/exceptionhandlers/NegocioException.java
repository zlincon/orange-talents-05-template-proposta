package br.com.zupacademy.lincon.proposta.exceptionhandlers;

import org.springframework.http.HttpStatus;

public class NegocioException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
