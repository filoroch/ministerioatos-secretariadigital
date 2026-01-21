package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PresencaDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PresencaRepository extends JpaRepository<PresencaDataJpa, UUID> {

    /**
     * Busca todas as presenças de um curso específico
     */
    @Query("SELECT p FROM PresencaDataJpa p WHERE p.pessoaCurso.curso.id = :cursoId")
    List<PresencaDataJpa> findByCursoId(@Param("cursoId") UUID cursoId);

    /**
     * Busca todas as presenças de uma pessoa em um curso
     */
    List<PresencaDataJpa> findByPessoaCursoId(UUID pessoaCursoId);

    /**
     * Busca presenças por aula
     */
    List<PresencaDataJpa> findByAulaId(UUID aulaId);

    /**
     * Busca presenças por tipo
     */
    List<PresencaDataJpa> findByTipoPresenca(PresencaDataJpa.TipoPresenca tipoPresenca);

    /**
     * Conta presenças por tipo para um curso
     */
    @Query("SELECT COUNT(p) FROM PresencaDataJpa p WHERE p.pessoaCurso.curso.id = :cursoId AND p.tipoPresenca = :tipoPresenca")
    Long countByTipoPresencaAndCurso(@Param("cursoId") UUID cursoId, @Param("tipoPresenca") PresencaDataJpa.TipoPresenca tipoPresenca);

    /**
     * Verifica se existe presença para uma pessoa em uma aula específica
     */
    boolean existsByPessoaCursoIdAndAulaId(UUID pessoaCursoId, UUID aulaId);
}
