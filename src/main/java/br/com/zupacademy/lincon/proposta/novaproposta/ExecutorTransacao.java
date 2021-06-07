package br.com.zupacademy.lincon.proposta.novaproposta;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ExecutorTransacao {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public <T> T saveAndCommit(T object) {
        manager.persist(object);
        return object;
    }

    @Transactional
    public <T> T updateAndCommit(T object) {
        return manager.merge(object);
    }

}
