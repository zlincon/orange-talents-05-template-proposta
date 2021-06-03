package br.com.zupacademy.lincon.proposta.novaproposta;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BloqueiaDocumentoDuplicatoValidator {
    public static boolean estaValido(EntityManager manager,
                              NovaPropostaRequest request) {
        return manager.createQuery("SELECT p.documento FROM Proposta p WHERE " +
                "p.documento = :documento").setParameter("documento",
                request.getDocumento()).getResultList().isEmpty();
    }
}
