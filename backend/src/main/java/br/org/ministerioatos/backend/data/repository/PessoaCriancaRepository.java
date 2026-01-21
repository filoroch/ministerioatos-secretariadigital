package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaCriancaDataJpa;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaCriancaRepository extends JpaRepository<PessoaCriancaDataJpa, UUID> {

    /**
     * Busca criança pelo ID da pessoa
     */
    Optional<PessoaCriancaDataJpa> findByPessoaId(UUID pessoaId);

    /**
     * Busca todas as crianças
     */
    @Query("SELECT pc FROM PessoaCriancaDataJpa pc")
    List<PessoaCriancaDataJpa> findAllCriancas();

    /**
     * Busca crianças por responsável
     */
    @Query("SELECT pc FROM PessoaCriancaDataJpa pc JOIN pc.responsaveis r WHERE r = :responsavelNome")
    List<PessoaCriancaDataJpa> findByResponsavel(@Param("responsavelNome") String responsavelNome);

    /**
     * Busca crianças que têm um tipo específico de responsável
     */
    @Query("SELECT pc FROM PessoaCriancaDataJpa pc WHERE KEY(pc.responsaveis) = :tipoRelacao")
    List<PessoaCriancaDataJpa> findByTipoResponsavel(@Param("tipoRelacao") TipoRelacao tipoRelacao);

    /**
     * Conta total de crianças
     */
    @Query("SELECT COUNT(pc) FROM PessoaCriancaDataJpa pc")
    Long countAllCriancas();

    /**
     * Verifica se pessoa é criança
     */
    boolean existsByPessoaId(UUID pessoaId);

    /**
     * Busca crianças com observações
     */
    @Query("SELECT pc FROM PessoaCriancaDataJpa pc WHERE pc.obsCrianca IS NOT NULL AND pc.obsCrianca != ''")
    List<PessoaCriancaDataJpa> findWithObservacoes();

    /**
     * Busca crianças por nome do responsável (case-insensitive)
     */
    @Query("SELECT pc FROM PessoaCriancaDataJpa pc JOIN pc.responsaveis r WHERE LOWER(r) LIKE LOWER(CONCAT('%', :nomeResponsavel, '%'))")
    List<PessoaCriancaDataJpa> findByResponsavelNomeContaining(@Param("nomeResponsavel") String nomeResponsavel);
}
