package br.com.zupacademy.lincon.proposta.novaproposta;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class BloqueiaDocumentoDuplicatoValidator {

    @PersistenceContext
    private EntityManager manager;

    public boolean estaValido(NovaPropostaRequest request) {
        return manager.createQuery("SELECT p.documento FROM Proposta p WHERE " +
                "p.documento = :documento").setParameter("documento",
                request.getDocumento()).getResultList().isEmpty();
    }
}
