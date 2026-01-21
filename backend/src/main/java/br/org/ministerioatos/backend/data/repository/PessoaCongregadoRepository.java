package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaCongregadoDataJpa;
import br.org.ministerioatos.backend.domain.valueobjects.SituacaoEclesiastica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaCongregadoRepository extends JpaRepository<PessoaCongregadoDataJpa, UUID> {

    /**
     * Busca congregado pelo ID da pessoa
     */
    Optional<PessoaCongregadoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca congregados por situação
     */
    List<PessoaCongregadoDataJpa> findBySituacao(SituacaoEclesiastica situacao);

    /**
     * Busca congregados em discipulado
     */
    @Query("SELECT pc FROM PessoaCongregadoDataJpa pc WHERE pc.discipulado = true")
    List<PessoaCongregadoDataJpa> findInDiscipulado();

    /**
     * Busca congregados por discipulador
     */
    List<PessoaCongregadoDataJpa> findByDiscipuladorId(UUID discipuladorId);

    /**
     * Busca congregados convertidos em um período
     */
    List<PessoaCongregadoDataJpa> findByDataConversaoBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca congregados que concluíram discipulado
     */
    @Query("SELECT pc FROM PessoaCongregadoDataJpa pc WHERE pc.dataConclusaoDiscipulado IS NOT NULL")
    List<PessoaCongregadoDataJpa> findDiscipuladoConcluido();

    /**
     * Busca congregados em discipulado ativo (não concluído)
     */
    @Query("SELECT pc FROM PessoaCongregadoDataJpa pc WHERE pc.discipulado = true AND pc.dataConclusaoDiscipulado IS NULL")
    List<PessoaCongregadoDataJpa> findDiscipuladoAtivo();

    /**
     * Conta congregados por situação
     */
    Long countBySituacao(SituacaoEclesiastica situacao);

    /**
     * Conta congregados em discipulado por discipulador
     */
    @Query("SELECT COUNT(pc) FROM PessoaCongregadoDataJpa pc WHERE pc.discipuladorId = :discipuladorId AND pc.discipulado = true")
    Long countByDiscipuladorAtivo(@Param("discipuladorId") UUID discipuladorId);

    /**
     * Busca congregados convertidos recentemente (últimos 30 dias)
     */
    @Query("SELECT pc FROM PessoaCongregadoDataJpa pc WHERE pc.dataConversao >= :dataLimite")
    List<PessoaCongregadoDataJpa> findRecentlyConverted(@Param("dataLimite") LocalDate dataLimite);

    /**
     * Busca congregados com observações
     */
    @Query("SELECT pc FROM PessoaCongregadoDataJpa pc WHERE pc.observacoes IS NOT NULL AND pc.observacoes != ''")
    List<PessoaCongregadoDataJpa> findWithObservacoes();

    /**
     * Verifica se pessoa é congregado
     */
    boolean existsByPessoaId(UUID pessoaId);
}
