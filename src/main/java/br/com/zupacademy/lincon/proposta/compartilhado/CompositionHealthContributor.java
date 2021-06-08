//package br.com.zupacademy.lincon.proposta.compartilhado;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.health.CompositeHealthContributor;
//import org.springframework.boot.actuate.health.HealthContributor;
//import org.springframework.boot.actuate.health.NamedContributor;
//import org.springframework.stereotype.Component;
//
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Component("HealthCompose")
//public class CompositionHealthContributor implements CompositeHealthContributor {
//
//    private Map<String, HealthContributor> contributors = new LinkedHashMap<>();
//
//    @Autowired
//    public CompositionHealthContributor(ApiSolicitacaoHealthIndicator apiSolicitacaoHealthIndicator) {
//        this.contributors.put("api/solicitacao",apiSolicitacaoHealthIndicator);
//    }
//
//    @Override
//    public Iterator<NamedContributor<HealthContributor>> iterator() {
//        return contributors.entrySet().stream().map(entry -> NamedContributor.of(entry.getKey(),
//                entry.getValue())).iterator();
//    }
//
//    @Override
//    public HealthContributor getContributor(String name) {
//        return contributors.get(name);
//    }
//}
