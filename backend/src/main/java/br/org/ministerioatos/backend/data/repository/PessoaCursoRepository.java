package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaCursoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaCursoRepository extends JpaRepository<PessoaCursoDataJpa, UUID> {

    /**
     * Busca matrículas de uma pessoa
     */
    List<PessoaCursoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca alunos de um curso
     */
    List<PessoaCursoDataJpa> findByCursoId(UUID cursoId);

    /**
     * Busca matrícula específica de pessoa em curso
     */
    Optional<PessoaCursoDataJpa> findByPessoaIdAndCursoId(UUID pessoaId, UUID cursoId);

    /**
     * Busca matrículas concluídas
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.concluido = true")
    List<PessoaCursoDataJpa> findConcluidos();

    /**
     * Busca matrículas não concluídas (em andamento)
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.concluido = false")
    List<PessoaCursoDataJpa> findEmAndamento();

    /**
     * Busca matrículas concluídas de um curso
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.curso.id = :cursoId AND pc.concluido = true")
    List<PessoaCursoDataJpa> findConcluidosByCurso(@Param("cursoId") UUID cursoId);

    /**
     * Busca matrículas em andamento de um curso
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.curso.id = :cursoId AND pc.concluido = false")
    List<PessoaCursoDataJpa> findEmAndamentoByCurso(@Param("cursoId") UUID cursoId);

    /**
     * Busca matrículas por período de matrícula
     */
    List<PessoaCursoDataJpa> findByDataMatriculaBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca conclusões por período
     */
    List<PessoaCursoDataJpa> findByDataConclusaoBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Conta total de alunos em um curso
     */
    Long countByCursoId(UUID cursoId);

    /**
     * Conta alunos que concluíram um curso
     */
    @Query("SELECT COUNT(pc) FROM PessoaCursoDataJpa pc WHERE pc.curso.id = :cursoId AND pc.concluido = true")
    Long countConcluidosByCurso(@Param("cursoId") UUID cursoId);

    /**
     * Conta alunos em andamento em um curso
     */
    @Query("SELECT COUNT(pc) FROM PessoaCursoDataJpa pc WHERE pc.curso.id = :cursoId AND pc.concluido = false")
    Long countEmAndamentoByCurso(@Param("cursoId") UUID cursoId);

    /**
     * Verifica se pessoa está matriculada em um curso
     */
    boolean existsByPessoaIdAndCursoId(UUID pessoaId, UUID cursoId);

    /**
     * Busca cursos concluídos por uma pessoa
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.pessoa.id = :pessoaId AND pc.concluido = true")
    List<PessoaCursoDataJpa> findCursosConcluidos(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca cursos em andamento de uma pessoa
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.pessoa.id = :pessoaId AND pc.concluido = false")
    List<PessoaCursoDataJpa> findCursosEmAndamento(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca matrículas recentes (últimos 30 dias)
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.dataMatricula >= :dataLimite")
    List<PessoaCursoDataJpa> findMatriculasRecentes(@Param("dataLimite") LocalDate dataLimite);

    /**
     * Busca conclusões recentes (últimos 30 dias)
     */
    @Query("SELECT pc FROM PessoaCursoDataJpa pc WHERE pc.dataConclusao >= :dataLimite")
    List<PessoaCursoDataJpa> findConclusoesRecentes(@Param("dataLimite") LocalDate dataLimite);
}
