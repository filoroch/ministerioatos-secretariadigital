package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.PessoaDataJpa;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaDataJpa, UUID> {

    /**
     * Busca pessoa por CPF
     */
    Optional<PessoaDataJpa> findByCpf(String cpf);

    /**
     * Busca pessoa por RG
     */
    Optional<PessoaDataJpa> findByRg(String rg);

    /**
     * Busca pessoas por nome (case-insensitive)
     */
    List<PessoaDataJpa> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca pessoas por email
     */
    Optional<PessoaDataJpa> findByEmail(String email);

    /**
     * Busca pessoas por gênero
     */
    List<PessoaDataJpa> findByGenero(Genero genero);

    /**
     * Busca pessoas nascidas entre duas datas
     */
    List<PessoaDataJpa> findByDataNascimentoBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca pessoas por telefone
     */
    List<PessoaDataJpa> findByTelefoneContaining(String telefone);

    /**
     * Verifica se CPF já existe
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se RG já existe
     */
    boolean existsByRg(String rg);

    /**
     * Verifica se email já existe
     */
    boolean existsByEmail(String email);

    /**
     * Busca pessoas ativas (não soft-deleted)
     */
    @Query("SELECT p FROM PessoaDataJpa p WHERE p.deletedAt IS NULL")
    List<PessoaDataJpa> findAllActive();

    /**
     * Conta total de pessoas ativas
     */
    @Query("SELECT COUNT(p) FROM PessoaDataJpa p WHERE p.deletedAt IS NULL")
    Long countActive();
}
