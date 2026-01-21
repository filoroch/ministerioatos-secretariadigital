package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaNucleoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaNucleoRepository extends JpaRepository<PessoaNucleoDataJpa, UUID> {

    /**
     * Busca participações de uma pessoa em núcleos
     */
    List<PessoaNucleoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca participações ativas de uma pessoa (sem data fim)
     */
    @Query("SELECT pn FROM PessoaNucleoDataJpa pn WHERE pn.pessoa.id = :pessoaId AND pn.dataFim IS NULL")
    List<PessoaNucleoDataJpa> findActiveByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pessoas de um núcleo
     */
    List<PessoaNucleoDataJpa> findByNucleoId(UUID nucleoId);

    /**
     * Busca pessoas ativas de um núcleo
     */
    @Query("SELECT pn FROM PessoaNucleoDataJpa pn WHERE pn.nucleo.id = :nucleoId AND pn.dataFim IS NULL")
    List<PessoaNucleoDataJpa> findActiveByNucleoId(@Param("nucleoId") UUID nucleoId);

    /**
     * Busca participações em um período
     */
    @Query("SELECT pn FROM PessoaNucleoDataJpa pn WHERE " +
           "pn.dataInicio <= :dataFim AND (pn.dataFim IS NULL OR pn.dataFim >= :dataInicio)")
    List<PessoaNucleoDataJpa> findByPeriod(@Param("dataInicio") LocalDate dataInicio,
                                          @Param("dataFim") LocalDate dataFim);

    /**
     * Conta pessoas ativas em um núcleo
     */
    @Query("SELECT COUNT(pn) FROM PessoaNucleoDataJpa pn WHERE pn.nucleo.id = :nucleoId AND pn.dataFim IS NULL")
    Long countActiveByNucleo(@Param("nucleoId") UUID nucleoId);

    /**
     * Verifica se pessoa está ativa em algum núcleo
     */
    @Query("SELECT COUNT(pn) > 0 FROM PessoaNucleoDataJpa pn WHERE pn.pessoa.id = :pessoaId AND pn.dataFim IS NULL")
    boolean isActiveInAnyNucleo(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca participações que começaram em um período específico
     */
    List<PessoaNucleoDataJpa> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca pessoas que saíram de núcleos em um período
     */
    List<PessoaNucleoDataJpa> findByDataFimBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca núcleos frequentados por uma pessoa (histórico completo)
     */
    @Query("SELECT DISTINCT pn.nucleo FROM PessoaNucleoDataJpa pn WHERE pn.pessoa.id = :pessoaId")
    List<Object> findNucleosByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pessoas que frequentaram um núcleo específico (histórico completo)
     */
    @Query("SELECT DISTINCT pn.pessoa FROM PessoaNucleoDataJpa pn WHERE pn.nucleo.id = :nucleoId")
    List<Object> findPessoasByNucleoId(@Param("nucleoId") UUID nucleoId);

    /**
     * Verifica se pessoa já frequentou um núcleo específico
     */
    boolean existsByPessoaIdAndNucleoId(UUID pessoaId, UUID nucleoId);
}
