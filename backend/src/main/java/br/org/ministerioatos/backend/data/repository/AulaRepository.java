package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.AulaDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AulaRepository extends JpaRepository<AulaDataJpa, UUID> {

    /**
     * Busca aulas de um curso específico
     */
    List<AulaDataJpa> findByCursoIdOrderByNumeroAula(UUID cursoId);

    /**
     * Busca aula por número e curso
     */
    Optional<AulaDataJpa> findByCursoIdAndNumeroAula(UUID cursoId, Integer numeroAula);

    /**
     * Busca aulas por data
     */
    List<AulaDataJpa> findByDataAula(LocalDate dataAula);

    /**
     * Busca aulas em um período
     */
    List<AulaDataJpa> findByDataAulaBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca aulas por título (case-insensitive, contendo)
     */
    List<AulaDataJpa> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca aulas de hoje
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE a.dataAula = CURRENT_DATE")
    List<AulaDataJpa> findAulasDeHoje();

    /**
     * Busca próximas aulas (futuras)
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE a.dataAula > CURRENT_DATE ORDER BY a.dataAula ASC")
    List<AulaDataJpa> findProximasAulas();

    /**
     * Busca aulas passadas
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE a.dataAula < CURRENT_DATE ORDER BY a.dataAula DESC")
    List<AulaDataJpa> findAulasPassadas();

    /**
     * Conta aulas de um curso
     */
    Long countByCursoId(UUID cursoId);

    /**
     * Busca última aula de um curso (por número)
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE a.curso.id = :cursoId ORDER BY a.numeroAula DESC LIMIT 1")
    Optional<AulaDataJpa> findUltimaAulaByCurso(@Param("cursoId") UUID cursoId);

    /**
     * Busca primeira aula de um curso (por número)
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE a.curso.id = :cursoId ORDER BY a.numeroAula ASC LIMIT 1")
    Optional<AulaDataJpa> findPrimeiraAulaByCurso(@Param("cursoId") UUID cursoId);

    /**
     * Busca aulas por texto na descrição
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE LOWER(a.descricao) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<AulaDataJpa> findByDescricaoContaining(@Param("texto") String texto);

    /**
     * Busca aulas de um curso em uma data específica
     */
    List<AulaDataJpa> findByCursoIdAndDataAula(UUID cursoId, LocalDate dataAula);

    /**
     * Verifica se existe aula em uma data para um curso
     */
    boolean existsByCursoIdAndDataAula(UUID cursoId, LocalDate dataAula);

    /**
     * Busca aulas com descrição
     */
    @Query("SELECT a FROM AulaDataJpa a WHERE a.descricao IS NOT NULL AND a.descricao != ''")
    List<AulaDataJpa> findWithDescricao();
}
