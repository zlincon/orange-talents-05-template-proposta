package br.com.zupacademy.lincon.proposta.compartilhado;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue,
        Object> {
    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object o,
                           ConstraintValidatorContext constraintValidatorContext) {

        List resultList =
                manager.createQuery("SELECT 1 FROM " + klass.getName() +
                        " WHERE " + domainAttribute + " = :value")
                        .setParameter("value", o).getResultList();

        Assert.state(resultList.size() <= 1, "Foi encontrado mais de um " +
                klass + " com o atributo " + domainAttribute + " = " + o);

        return resultList.isEmpty();

    }
}
