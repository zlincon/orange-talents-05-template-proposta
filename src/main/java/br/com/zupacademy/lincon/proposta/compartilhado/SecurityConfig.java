package br.com.zupacademy.lincon.proposta.compartilhado;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequest ->
                authorizeRequest
                        .antMatchers(HttpMethod.GET, "/api/propostas/**")
                        .hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.GET, "/api/cartoes/**")
                        .hasAuthority("SCOPE_cartoes")
                        .antMatchers(HttpMethod.POST, "/api/cartoes/**")
                        .hasAuthority("SCOPE_cartoes")
                        .antMatchers(HttpMethod.POST, "/api/propostas/**")
                        .hasAuthority("SCOPE_propostas")
                        .anyRequest().permitAll()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }
}
