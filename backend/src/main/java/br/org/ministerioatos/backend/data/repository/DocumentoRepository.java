package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.DocumentoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoDataJpa, UUID> {

    /**
     * Busca documentos de uma pessoa
     */
    List<DocumentoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca documentos ativos de uma pessoa
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.pessoa.id = :pessoaId AND d.ativo = true")
    List<DocumentoDataJpa> findActiveByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca documento por número
     */
    Optional<DocumentoDataJpa> findByNumeroDocumento(String numeroDocumento);

    /**
     * Busca documentos por tipo
     */
    List<DocumentoDataJpa> findByTipo(DocumentoDataJpa.DocumentoDocumentoTipo tipo);

    /**
     * Busca documentos ativos por tipo
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.tipo = :tipo AND d.ativo = true")
    List<DocumentoDataJpa> findActiveByTipo(@Param("tipo") DocumentoDataJpa.DocumentoDocumentoTipo tipo);

    /**
     * Busca documentos de uma pessoa por tipo
     */
    List<DocumentoDataJpa> findByPessoaIdAndTipo(UUID pessoaId, DocumentoDataJpa.DocumentoDocumentoTipo tipo);

    /**
     * Busca documentos emitidos em um período
     */
    List<DocumentoDataJpa> findByDataEmissaoBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca documentos que vencem em um período
     */
    List<DocumentoDataJpa> findByDataValidadeBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca documentos vencidos
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.dataValidade < CURRENT_DATE AND d.ativo = true")
    List<DocumentoDataJpa> findVencidos();

    /**
     * Busca documentos que vencem em breve (próximos 30 dias)
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.dataValidade BETWEEN CURRENT_DATE AND :dataLimite AND d.ativo = true")
    List<DocumentoDataJpa> findVencendoEm30Dias(@Param("dataLimite") LocalDate dataLimite);

    /**
     * Verifica se número do documento já existe
     */
    boolean existsByNumeroDocumento(String numeroDocumento);

    /**
     * Conta documentos ativos de uma pessoa
     */
    @Query("SELECT COUNT(d) FROM DocumentoDataJpa d WHERE d.pessoa.id = :pessoaId AND d.ativo = true")
    Long countActiveByPessoa(@Param("pessoaId") UUID pessoaId);

    /**
     * Conta documentos por tipo
     */
    Long countByTipoAndAtivoTrue(DocumentoDataJpa.DocumentoDocumentoTipo tipo);

    /**
     * Busca documentos com observações
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.observacoes IS NOT NULL AND d.observacoes != ''")
    List<DocumentoDataJpa> findWithObservacoes();

    /**
     * Busca documentos sem validade (permanentes)
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.dataValidade IS NULL AND d.ativo = true")
    List<DocumentoDataJpa> findPermanentes();

    /**
     * Busca documentos emitidos recentemente (últimos 30 dias)
     */
    @Query("SELECT d FROM DocumentoDataJpa d WHERE d.dataEmissao >= :dataLimite")
    List<DocumentoDataJpa> findEmitidosRecentemente(@Param("dataLimite") LocalDate dataLimite);
}
