package br.com.zupacademy.lincon.proposta.compartilhado;

import feign.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
@Configuration
public class MyTracerConfig {

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        return io.opentracing.noop.NoopTracerFactory.create();
    }

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }

}

