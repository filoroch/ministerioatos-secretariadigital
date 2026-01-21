package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.AtuacaoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AtuacaoRepository extends JpaRepository<AtuacaoDataJpa, UUID> {

    /**
     * Busca atuação por nome
     */
    Optional<AtuacaoDataJpa> findByNomeIgnoreCase(String nome);

    /**
     * Busca atuações ativas
     */
    @Query("SELECT a FROM AtuacaoDataJpa a WHERE a.ativa = true")
    List<AtuacaoDataJpa> findAllAtivas();

    /**
     * Busca atuações inativas
     */
    @Query("SELECT a FROM AtuacaoDataJpa a WHERE a.ativa = false")
    List<AtuacaoDataJpa> findAllInativas();

    /**
     * Busca atuações por nome (case-insensitive, contendo)
     */
    List<AtuacaoDataJpa> findByNomeContainingIgnoreCase(String nome);

    /**
     * Verifica se nome da atuação já existe
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Conta atuações ativas
     */
    @Query("SELECT COUNT(a) FROM AtuacaoDataJpa a WHERE a.ativa = true")
    Long countAtivas();

    /**
     * Busca atuações com descrição
     */
    @Query("SELECT a FROM AtuacaoDataJpa a WHERE a.descricao IS NOT NULL AND a.descricao != ''")
    List<AtuacaoDataJpa> findWithDescricao();

    /**
     * Busca atuações por texto na descrição
     */
    @Query("SELECT a FROM AtuacaoDataJpa a WHERE LOWER(a.descricao) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<AtuacaoDataJpa> findByDescricaoContaining(@Param("texto") String texto);
}
