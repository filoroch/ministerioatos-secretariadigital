package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaMembroDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaMembroRepository extends JpaRepository<PessoaMembroDataJpa, UUID> {

    /**
     * Busca membro pelo ID da pessoa
     */
    Optional<PessoaMembroDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca membros ativos
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.ativo = true")
    List<PessoaMembroDataJpa> findAllAtivos();

    /**
     * Busca membros inativos
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.ativo = false")
    List<PessoaMembroDataJpa> findAllInativos();

    /**
     * Busca membros batizados nas águas
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.batizadoAguas = true")
    List<PessoaMembroDataJpa> findBatizadosAguas();

    /**
     * Busca membros não batizados nas águas
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.batizadoAguas = false")
    List<PessoaMembroDataJpa> findNaoBatizadosAguas();

    /**
     * Busca membros batizados no Espírito Santo
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.batizadoEspirito = true")
    List<PessoaMembroDataJpa> findBatizadosEspiritoSanto();

    /**
     * Busca membros batizados em um período específico
     */
    List<PessoaMembroDataJpa> findByDataBatismoAguasBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca membros que receberam batismo no Espírito em um período
     */
    List<PessoaMembroDataJpa> findByDataBatismoEspiritoBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Conta membros ativos
     */
    @Query("SELECT COUNT(pm) FROM PessoaMembroDataJpa pm WHERE pm.ativo = true")
    Long countAtivos();

    /**
     * Conta membros batizados nas águas
     */
    @Query("SELECT COUNT(pm) FROM PessoaMembroDataJpa pm WHERE pm.batizadoAguas = true")
    Long countBatizadosAguas();

    /**
     * Conta membros batizados no Espírito Santo
     */
    @Query("SELECT COUNT(pm) FROM PessoaMembroDataJpa pm WHERE pm.batizadoEspirito = true")
    Long countBatizadosEspiritoSanto();

    /**
     * Busca membros com ambos os batismos
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.batizadoAguas = true AND pm.batizadoEspirito = true")
    List<PessoaMembroDataJpa> findComAmbosBatismos();

    /**
     * Verifica se pessoa é membro
     */
    boolean existsByPessoaId(UUID pessoaId);

    /**
     * Busca membros batizados recentemente (últimos 30 dias)
     */
    @Query("SELECT pm FROM PessoaMembroDataJpa pm WHERE pm.dataBatismoAguas >= :dataLimite")
    List<PessoaMembroDataJpa> findRecentlyBaptized(@Param("dataLimite") LocalDate dataLimite);
}
