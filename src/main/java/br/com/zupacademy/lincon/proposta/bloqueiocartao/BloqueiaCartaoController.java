package br.com.zupacademy.lincon.proposta.bloqueiocartao;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;
import br.com.zupacademy.lincon.proposta.criabiometria.CartaoRepository;
import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import br.com.zupacademy.lincon.proposta.sistemasexternos.BloqueaCartaoRequest;
import br.com.zupacademy.lincon.proposta.sistemasexternos.IntegracoesCartoes;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class BloqueiaCartaoController {

    private static final Logger log =
            LoggerFactory.getLogger(BloqueiaCartaoController.class);
    private CartaoRepository cartaoRepository;
    private IntegracoesCartoes integracoesCartoes;

    @Autowired
    public BloqueiaCartaoController(CartaoRepository cartaoRepository,
                                    IntegracoesCartoes integracoesCartoes) {
        this.cartaoRepository = cartaoRepository;
        this.integracoesCartoes = integracoesCartoes;
    }

    @PostMapping("/api/cartoes/{id}/bloqueia")
    @Transactional
    public void bloqueia(@PathVariable("id") Long id,
                         @RequestHeader HttpHeaders headers,
                         HttpServletRequest httpServletRequest) {

        log.debug("Tentativa bloqueio cartao [{}] vindo do user-agent {}", id
                , headers.get(HttpHeaders.USER_AGENT));

        String userAgent = headers.get(HttpHeaders.USER_AGENT).get(0);
        if (!headers.containsKey(HttpHeaders.USER_AGENT) || userAgent.isEmpty()) {
            log.debug("Não foi possível bloquar o cartão {id} por falta de " +
                    "user agent");
            throw new NegocioException("Missing User Agent",
                    HttpStatus.PRECONDITION_FAILED);
        }

        String ipRemoto = httpServletRequest.getRemoteAddr();
        log.debug("Tentativa bloqueio cartão [{}] vindo do ip {}", id,
                ipRemoto);
        if (!StringUtils.hasText(ipRemoto)) {
            log.debug("Não é possível bloqueio do cartão {} por falta de ip " +
                    "remoto", id);
            throw new NegocioException("Missing ip",
                    HttpStatus.PRECONDITION_FAILED);
        }



        try {
            Cartao cartao =
                    cartaoRepository.findById(id).orElseThrow(() -> new NegocioException(
                            "Cartão não encontrado. id: " + id,
                            HttpStatus.NOT_FOUND));
            cartao.bloquear(userAgent, ipRemoto);
            integracoesCartoes.bloquearCartao(cartao.getNumero(),
                    new BloqueaCartaoRequest(userAgent));
        } catch (FeignException.FeignClientException e) {
            throw new NegocioException("Feign Client Exception: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (FeignException.FeignServerException e) {
            throw new NegocioException("Feign Server Exception: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (RetryableException e) {
            throw new NegocioException("Feign Connection Exception: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.debug("Solicitação de bloqueio finalizada. Cartão id: {}", id);
    }
}
