package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.CursoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CursoRepository extends JpaRepository<CursoDataJpa, UUID> {

    /**
     * Busca curso por nome
     */
    Optional<CursoDataJpa> findByNomeIgnoreCase(String nome);

    /**
     * Busca cursos ativos
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.ativo = true")
    List<CursoDataJpa> findAllAtivos();

    /**
     * Busca cursos inativos
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.ativo = false")
    List<CursoDataJpa> findAllInativos();

    /**
     * Busca cursos por professor
     */
    List<CursoDataJpa> findByProfessorId(UUID professorId);

    /**
     * Busca cursos por nome (case-insensitive, contendo)
     */
    List<CursoDataJpa> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca cursos em andamento (data atual entre início e fim)
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.dataInicio <= CURRENT_DATE AND (c.dataFim IS NULL OR c.dataFim >= CURRENT_DATE) AND c.ativo = true")
    List<CursoDataJpa> findCursosEmAndamento();

    /**
     * Busca cursos finalizados
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.dataFim < CURRENT_DATE")
    List<CursoDataJpa> findCursosFinalizados();

    /**
     * Busca cursos que iniciam em um período
     */
    List<CursoDataJpa> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca cursos que terminam em um período
     */
    List<CursoDataJpa> findByDataFimBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca cursos por carga horária mínima
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.cargaHoraria >= :cargaMinima")
    List<CursoDataJpa> findByCargaHorariaMinima(@Param("cargaMinima") Integer cargaMinima);

    /**
     * Busca cursos por faixa de carga horária
     */
    List<CursoDataJpa> findByCargaHorariaBetween(Integer cargaMinima, Integer cargaMaxima);

    /**
     * Verifica se nome do curso já existe
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Conta cursos ativos
     */
    @Query("SELECT COUNT(c) FROM CursoDataJpa c WHERE c.ativo = true")
    Long countAtivos();

    /**
     * Busca cursos sem professor
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.professor IS NULL AND c.ativo = true")
    List<CursoDataJpa> findSemProfessor();

    /**
     * Busca cursos com descrição
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.descricao IS NOT NULL AND c.descricao != ''")
    List<CursoDataJpa> findWithDescricao();

    /**
     * Busca cursos por texto na descrição
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE LOWER(c.descricao) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<CursoDataJpa> findByDescricaoContaining(@Param("texto") String texto);

    /**
     * Busca cursos futuros (ainda não iniciados)
     */
    @Query("SELECT c FROM CursoDataJpa c WHERE c.dataInicio > CURRENT_DATE AND c.ativo = true")
    List<CursoDataJpa> findCursosFuturos();
}
