package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.DocumentoVersaoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentoVersaoRepository extends JpaRepository<DocumentoVersaoDataJpa, UUID> {

    /**
     * Busca versões de um documento
     */
    List<DocumentoVersaoDataJpa> findByDocumentoIdOrderByVersaoDesc(UUID documentoId);

    /**
     * Busca versão específica de um documento
     */
    Optional<DocumentoVersaoDataJpa> findByDocumentoIdAndVersao(UUID documentoId, Integer versao);

    /**
     * Busca última versão de um documento
     */
    @Query("SELECT dv FROM DocumentoVersaoDataJpa dv WHERE dv.documento.id = :documentoId ORDER BY dv.versao DESC LIMIT 1")
    Optional<DocumentoVersaoDataJpa> findLatestByDocumentoId(@Param("documentoId") UUID documentoId);

    /**
     * Busca primeira versão de um documento
     */
    @Query("SELECT dv FROM DocumentoVersaoDataJpa dv WHERE dv.documento.id = :documentoId ORDER BY dv.versao ASC LIMIT 1")
    Optional<DocumentoVersaoDataJpa> findFirstByDocumentoId(@Param("documentoId") UUID documentoId);

    /**
     * Conta versões de um documento
     */
    Long countByDocumentoId(UUID documentoId);

    /**
     * Busca versões por nome do arquivo
     */
    List<DocumentoVersaoDataJpa> findByNomeArquivoContainingIgnoreCase(String nomeArquivo);

    /**
     * Busca versões por tipo MIME
     */
    List<DocumentoVersaoDataJpa> findByMimeType(String mimeType);

    /**
     * Busca versões por faixa de tamanho
     */
    List<DocumentoVersaoDataJpa> findByTamanhoBytesBetween(Long tamanhoMinimo, Long tamanhoMaximo);

    /**
     * Busca versões com alterações documentadas
     */
    @Query("SELECT dv FROM DocumentoVersaoDataJpa dv WHERE dv.alteracoes IS NOT NULL AND dv.alteracoes != ''")
    List<DocumentoVersaoDataJpa> findWithAlteracoes();

    /**
     * Busca versões por URL do arquivo
     */
    Optional<DocumentoVersaoDataJpa> findByUrlArquivo(String urlArquivo);

    /**
     * Verifica se URL do arquivo já existe
     */
    boolean existsByUrlArquivo(String urlArquivo);

    /**
     * Busca versões grandes (acima de um tamanho específico)
     */
    @Query("SELECT dv FROM DocumentoVersaoDataJpa dv WHERE dv.tamanhoBytes > :tamanhoLimite")
    List<DocumentoVersaoDataJpa> findLargeFiles(@Param("tamanhoLimite") Long tamanhoLimite);

    /**
     * Calcula tamanho total de arquivos de um documento
     */
    @Query("SELECT SUM(dv.tamanhoBytes) FROM DocumentoVersaoDataJpa dv WHERE dv.documento.id = :documentoId")
    Long sumTamanhoByDocumentoId(@Param("documentoId") UUID documentoId);

    /**
     * Busca versões por extensão do arquivo
     */
    @Query("SELECT dv FROM DocumentoVersaoDataJpa dv WHERE LOWER(dv.nomeArquivo) LIKE LOWER(CONCAT('%.', :extensao))")
    List<DocumentoVersaoDataJpa> findByExtensao(@Param("extensao") String extensao);

    /**
     * Busca próxima versão disponível para um documento
     */
    @Query("SELECT COALESCE(MAX(dv.versao), 0) + 1 FROM DocumentoVersaoDataJpa dv WHERE dv.documento.id = :documentoId")
    Integer getNextVersao(@Param("documentoId") UUID documentoId);
}
