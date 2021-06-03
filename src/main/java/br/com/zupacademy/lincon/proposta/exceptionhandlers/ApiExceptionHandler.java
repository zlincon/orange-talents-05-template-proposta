package br.com.zupacademy.lincon.proposta.exceptionhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex,
                                                WebRequest request){
        var status = ex.getStatus() == null ? HttpStatus.BAD_REQUEST :
                ex.getStatus();
        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitulo(ex.getMessage());
        problem.setDataHora(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status
                , request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var campos = new ArrayList<Problem.Campo>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String message = messageSource.getMessage(error,
                    LocaleContextHolder.getLocale());

            campos.add(new Problem.Campo(nome, message));
        }

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitulo("Um ou mais campos estão inválidos. Preecha " +
                "corretamente e tente novamente.");
        problem.setDataHora(OffsetDateTime.now());
        problem.setCampos(campos);

        return super.handleExceptionInternal(ex, problem, headers, status,
                request);
    }
}
