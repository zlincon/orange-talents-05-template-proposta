package br.com.zupacademy.lincon.proposta.criabiometria;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
