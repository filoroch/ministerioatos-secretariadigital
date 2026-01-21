package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaDepartamentoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaDepartamentoRepository extends JpaRepository<PessoaDepartamentoDataJpa, UUID> {

    /**
     * Busca participações de uma pessoa em departamentos
     */
    List<PessoaDepartamentoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca participações ativas de uma pessoa (sem data fim)
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE pd.pessoa.id = :pessoaId AND pd.dataFim IS NULL")
    List<PessoaDepartamentoDataJpa> findActiveByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pessoas de um departamento
     */
    List<PessoaDepartamentoDataJpa> findByDepartamentoId(UUID departamentoId);

    /**
     * Busca pessoas ativas de um departamento
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE pd.departamento.id = :departamentoId AND pd.dataFim IS NULL")
    List<PessoaDepartamentoDataJpa> findActiveByDepartamentoId(@Param("departamentoId") UUID departamentoId);

    /**
     * Busca líderes de departamentos
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE pd.ehLider = true AND pd.dataFim IS NULL")
    List<PessoaDepartamentoDataJpa> findLideres();

    /**
     * Busca líderes de um departamento específico
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE pd.departamento.id = :departamentoId AND pd.ehLider = true AND pd.dataFim IS NULL")
    List<PessoaDepartamentoDataJpa> findLideresByDepartamento(@Param("departamentoId") UUID departamentoId);

    /**
     * Busca por cargo específico
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE LOWER(pd.cargo) LIKE LOWER(CONCAT('%', :cargo, '%')) AND pd.dataFim IS NULL")
    List<PessoaDepartamentoDataJpa> findByCargo(@Param("cargo") String cargo);

    /**
     * Busca participações em um período
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE " +
           "pd.dataInicio <= :dataFim AND (pd.dataFim IS NULL OR pd.dataFim >= :dataInicio)")
    List<PessoaDepartamentoDataJpa> findByPeriod(@Param("dataInicio") LocalDate dataInicio,
                                                 @Param("dataFim") LocalDate dataFim);

    /**
     * Conta pessoas ativas em um departamento
     */
    @Query("SELECT COUNT(pd) FROM PessoaDepartamentoDataJpa pd WHERE pd.departamento.id = :departamentoId AND pd.dataFim IS NULL")
    Long countActiveByDepartamento(@Param("departamentoId") UUID departamentoId);

    /**
     * Verifica se pessoa está ativa em algum departamento
     */
    @Query("SELECT COUNT(pd) > 0 FROM PessoaDepartamentoDataJpa pd WHERE pd.pessoa.id = :pessoaId AND pd.dataFim IS NULL")
    boolean isActiveInAnyDepartamento(@Param("pessoaId") UUID pessoaId);

    /**
     * Verifica se pessoa é líder de algum departamento
     */
    @Query("SELECT COUNT(pd) > 0 FROM PessoaDepartamentoDataJpa pd WHERE pd.pessoa.id = :pessoaId AND pd.ehLider = true AND pd.dataFim IS NULL")
    boolean isLiderInAnyDepartamento(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca líder principal de um departamento (assumindo um líder por departamento)
     */
    @Query("SELECT pd FROM PessoaDepartamentoDataJpa pd WHERE pd.departamento.id = :departamentoId AND pd.ehLider = true AND pd.dataFim IS NULL")
    Optional<PessoaDepartamentoDataJpa> findLiderByDepartamento(@Param("departamentoId") UUID departamentoId);
}
