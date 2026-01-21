package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaHistoricoTipoDataJpa;
import br.org.ministerioatos.backend.domain.valueobjects.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaHistoricoTipoRepository extends JpaRepository<PessoaHistoricoTipoDataJpa, UUID> {

    /**
     * Busca histórico de uma pessoa
     */
    List<PessoaHistoricoTipoDataJpa> findByPessoaIdOrderByDataInicioDesc(UUID pessoaId);

    /**
     * Busca tipo atual de uma pessoa (sem data fim)
     */
    @Query("SELECT pht FROM PessoaHistoricoTipoDataJpa pht WHERE pht.pessoa.id = :pessoaId AND pht.dataFim IS NULL")
    Optional<PessoaHistoricoTipoDataJpa> findCurrentByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pessoas por tipo atual
     */
    @Query("SELECT pht FROM PessoaHistoricoTipoDataJpa pht WHERE pht.tipo = :tipo AND pht.dataFim IS NULL")
    List<PessoaHistoricoTipoDataJpa> findByCurrentTipo(@Param("tipo") TipoPessoa tipo);

    /**
     * Busca histórico por tipo específico
     */
    List<PessoaHistoricoTipoDataJpa> findByTipo(TipoPessoa tipo);

    /**
     * Busca histórico por período
     */
    @Query("SELECT pht FROM PessoaHistoricoTipoDataJpa pht WHERE " +
           "pht.dataInicio <= :dataFim AND (pht.dataFim IS NULL OR pht.dataFim >= :dataInicio)")
    List<PessoaHistoricoTipoDataJpa> findByPeriod(@Param("dataInicio") LocalDate dataInicio,
                                                  @Param("dataFim") LocalDate dataFim);

    /**
     * Conta pessoas por tipo atual
     */
    @Query("SELECT COUNT(pht) FROM PessoaHistoricoTipoDataJpa pht WHERE pht.tipo = :tipo AND pht.dataFim IS NULL")
    Long countByCurrentTipo(@Param("tipo") TipoPessoa tipo);

    /**
     * Busca transições de tipo de uma pessoa
     */
    @Query("SELECT pht FROM PessoaHistoricoTipoDataJpa pht WHERE pht.pessoa.id = :pessoaId AND pht.motivoTransicao IS NOT NULL ORDER BY pht.dataInicio DESC")
    List<PessoaHistoricoTipoDataJpa> findTransitionsByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Verifica se pessoa teve um tipo específico em algum momento
     */
    @Query("SELECT COUNT(pht) > 0 FROM PessoaHistoricoTipoDataJpa pht WHERE pht.pessoa.id = :pessoaId AND pht.tipo = :tipo")
    boolean hasEverBeenType(@Param("pessoaId") UUID pessoaId, @Param("tipo") TipoPessoa tipo);
}
