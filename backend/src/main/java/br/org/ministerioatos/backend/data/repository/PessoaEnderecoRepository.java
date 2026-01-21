package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaEnderecoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaEnderecoRepository extends JpaRepository<PessoaEnderecoDataJpa, UUID> {

    /**
     * Busca endereços de uma pessoa
     */
    List<PessoaEnderecoDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca endereços ativos de uma pessoa (sem data fim)
     */
    @Query("SELECT pe FROM PessoaEnderecoDataJpa pe WHERE pe.pessoa.id = :pessoaId AND pe.dataFim IS NULL")
    List<PessoaEnderecoDataJpa> findActiveByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca endereço principal de uma pessoa
     */
    @Query("SELECT pe FROM PessoaEnderecoDataJpa pe WHERE pe.pessoa.id = :pessoaId AND pe.principal = true AND pe.dataFim IS NULL")
    Optional<PessoaEnderecoDataJpa> findPrincipalByPessoaId(@Param("pessoaId") UUID pessoaId);

    /**
     * Busca pessoas que moram em um endereço
     */
    List<PessoaEnderecoDataJpa> findByEnderecoId(UUID enderecoId);

    /**
     * Busca pessoas ativas em um endereço
     */
    @Query("SELECT pe FROM PessoaEnderecoDataJpa pe WHERE pe.endereco.id = :enderecoId AND pe.dataFim IS NULL")
    List<PessoaEnderecoDataJpa> findActiveByEnderecoId(@Param("enderecoId") UUID enderecoId);

    /**
     * Busca por tipo de endereço
     */
    List<PessoaEnderecoDataJpa> findByTipo(PessoaEnderecoDataJpa.EnderecoTipo tipo);

    /**
     * Busca endereços de uma pessoa por período
     */
    @Query("SELECT pe FROM PessoaEnderecoDataJpa pe WHERE pe.pessoa.id = :pessoaId AND " +
           "pe.dataInicio <= :dataFim AND (pe.dataFim IS NULL OR pe.dataFim >= :dataInicio)")
    List<PessoaEnderecoDataJpa> findByPessoaIdAndPeriod(@Param("pessoaId") UUID pessoaId,
                                                        @Param("dataInicio") LocalDate dataInicio,
                                                        @Param("dataFim") LocalDate dataFim);

    /**
     * Verifica se pessoa tem endereço ativo
     */
    @Query("SELECT COUNT(pe) > 0 FROM PessoaEnderecoDataJpa pe WHERE pe.pessoa.id = :pessoaId AND pe.dataFim IS NULL")
    boolean hasActiveAddress(@Param("pessoaId") UUID pessoaId);
}
