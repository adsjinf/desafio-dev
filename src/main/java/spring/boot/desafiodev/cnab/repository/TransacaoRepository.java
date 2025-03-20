package spring.boot.desafiodev.cnab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.boot.desafiodev.cnab.model.Transacao;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t from Transacao t WHERE t.tipo = :tipo")
    List<Transacao> findByTipo(@Param("tipo") String tipo);
}
