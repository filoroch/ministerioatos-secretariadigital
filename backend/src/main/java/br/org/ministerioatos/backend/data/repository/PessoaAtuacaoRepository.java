package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaAtuacaoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaAtuacaoRepository extends JpaRepository<PessoaAtuacaoDataJpa, UUID> {

    /**
     * Busca atuações de uma pessoa
     */
    List<PessoaAtuacaoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca atuações ativas de uma pessoa (sem data fim)
     */
    @Query("SELECT pa FROM PessoaAtuacaoDataJpa pa WHERE pa.pessoa.id = :pessoaId AND pa.dataFim IS NULL")
    List<PessoaAtuacaoDataJpa> findActiveByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pessoas em uma atuação específica
     */
    List<PessoaAtuacaoDataJpa> findByAtuacaoId(UUID atuacaoId);

    /**
     * Busca pessoas ativas em uma atuação
     */
    @Query("SELECT pa FROM PessoaAtuacaoDataJpa pa WHERE pa.atuacao.id = :atuacaoId AND pa.dataFim IS NULL")
    List<PessoaAtuacaoDataJpa> findActiveByAtuacaoId(@Param("atuacaoId") UUID atuacaoId);

    /**
     * Busca atuações em um período
     */
    @Query("SELECT pa FROM PessoaAtuacaoDataJpa pa WHERE " +
           "pa.dataInicio <= :dataFim AND (pa.dataFim IS NULL OR pa.dataFim >= :dataInicio)")
    List<PessoaAtuacaoDataJpa> findByPeriod(@Param("dataInicio") LocalDate dataInicio,
                                           @Param("dataFim") LocalDate dataFim);

    /**
     * Conta pessoas ativas em uma atuação
     */
    @Query("SELECT COUNT(pa) FROM PessoaAtuacaoDataJpa pa WHERE pa.atuacao.id = :atuacaoId AND pa.dataFim IS NULL")
    Long countActiveByAtuacao(@Param("atuacaoId") UUID atuacaoId);

    /**
     * Verifica se pessoa está ativa em alguma atuação
     */
    @Query("SELECT COUNT(pa) > 0 FROM PessoaAtuacaoDataJpa pa WHERE pa.pessoa.id = :pessoaId AND pa.dataFim IS NULL")
    boolean isActiveInAnyAtuacao(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca atuações que começaram em um período específico
     */
    List<PessoaAtuacaoDataJpa> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca atuações que terminaram em um período
     */
    List<PessoaAtuacaoDataJpa> findByDataFimBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca atuações com observações
     */
    @Query("SELECT pa FROM PessoaAtuacaoDataJpa pa WHERE pa.observacoes IS NOT NULL AND pa.observacoes != ''")
    List<PessoaAtuacaoDataJpa> findWithObservacoes();

    /**
     * Verifica se pessoa já teve uma atuação específica
     */
    boolean existsByPessoaIdAndAtuacaoId(UUID pessoaId, UUID atuacaoId);

    /**
     * Busca pessoas que exerceram uma atuação específica (histórico completo)
     */
    @Query("SELECT DISTINCT pa.pessoa FROM PessoaAtuacaoDataJpa pa WHERE pa.atuacao.id = :atuacaoId")
    List<Object> findPessoasByAtuacaoId(@Param("atuacaoId") UUID atuacaoId);
}
