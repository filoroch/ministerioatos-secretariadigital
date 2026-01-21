package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.RelacionamentoDataJpa;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelacionamentoRepository extends JpaRepository<RelacionamentoDataJpa, UUID> {

    /**
     * Busca relacionamentos de uma pessoa como origem
     */
    List<RelacionamentoDataJpa> findByOrigemIdAndDeletedAtIsNull(UUID pessoaId);

    /**
     * Busca relacionamentos de uma pessoa como destino
     */
    List<RelacionamentoDataJpa> findByDestinoIdAndDeletedAtIsNull(UUID pessoaId);

    /**
     * Busca todos os relacionamentos de uma pessoa (origem ou destino)
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE (r.origem.id = :pessoaId OR r.destino.id = :pessoaId) AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca relacionamentos por tipo
     */
    List<RelacionamentoDataJpa> findByTipoAndDeletedAtIsNull(TipoRelacao tipo);

    /**
     * Busca relacionamentos entre duas pessoas específicas
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE " +
           "((r.origem.id = :pessoa1Id AND r.destino.id = :pessoa2Id) OR " +
           "(r.origem.id = :pessoa2Id AND r.destino.id = :pessoa1Id)) AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findBetweenPessoas(@Param("pessoa1Id") UUID pessoa1Id,
                                                   @Param("pessoa2Id") UUID pessoa2Id);

    /**
     * Busca relacionamentos de um tipo específico de uma pessoa
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE r.origem.id = :pessoaId AND r.tipo = :tipo AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findByOrigemAndTipo(@Param("pessoaId") UUID pessoaId,
                                                   @Param("tipo") TipoRelacao tipo);

    /**
     * Busca relacionamentos ativos (não soft-deleted)
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findAllActive();

    /**
     * Busca relacionamentos soft-deleted
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE r.deletedAt IS NOT NULL")
    List<RelacionamentoDataJpa> findAllDeleted();

    /**
     * Conta relacionamentos ativos de uma pessoa
     */
    @Query("SELECT COUNT(r) FROM RelacionamentoDataJpa r WHERE (r.origem.id = :pessoaId OR r.destino.id = :pessoaId) AND r.deletedAt IS NULL")
    Long countActiveByPessoa(@Param("pessoaId") UUID pessoaId);

    /**
     * Conta relacionamentos por tipo
     */
    Long countByTipoAndDeletedAtIsNull(TipoRelacao tipo);

    /**
     * Verifica se existe relacionamento entre duas pessoas
     */
    @Query("SELECT COUNT(r) > 0 FROM RelacionamentoDataJpa r WHERE " +
           "((r.origem.id = :pessoa1Id AND r.destino.id = :pessoa2Id) OR " +
           "(r.origem.id = :pessoa2Id AND r.destino.id = :pessoa1Id)) AND r.deletedAt IS NULL")
    boolean existsRelationship(@Param("pessoa1Id") UUID pessoa1Id,
                              @Param("pessoa2Id") UUID pessoa2Id);

    /**
     * Busca relacionamentos com observações
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE r.observacoes IS NOT NULL AND r.observacoes != '' AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findWithObservacoes();

    /**
     * Busca familiares diretos (pais, filhos, cônjuge, irmãos)
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE " +
           "(r.origem.id = :pessoaId OR r.destino.id = :pessoaId) AND " +
           "r.tipo IN ('PAI', 'MAE', 'FILHO', 'FILHA', 'CONJUGE', 'IRMAO', 'IRMA') AND " +
           "r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findFamiliaresDiretos(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca cônjuges de uma pessoa
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE " +
           "(r.origem.id = :pessoaId OR r.destino.id = :pessoaId) AND " +
           "r.tipo IN ('CONJUGE', 'NOIVO', 'NOIVA') AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findConjuges(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca filhos de uma pessoa
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE " +
           "r.origem.id = :pessoaId AND r.tipo IN ('FILHO', 'FILHA') AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findFilhos(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pais de uma pessoa
     */
    @Query("SELECT r FROM RelacionamentoDataJpa r WHERE " +
           "r.destino.id = :pessoaId AND r.tipo IN ('PAI', 'MAE') AND r.deletedAt IS NULL")
    List<RelacionamentoDataJpa> findPais(@Param("pessoaId") UUID pessoaId);
}
