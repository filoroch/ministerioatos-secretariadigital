package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.DepartamentoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoDataJpa, UUID> {

    /**
     * Busca departamento por nome
     */
    Optional<DepartamentoDataJpa> findByNomeIgnoreCase(String nome);

    /**
     * Busca departamentos ativos
     */
    @Query("SELECT d FROM DepartamentoDataJpa d WHERE d.ativo = true")
    List<DepartamentoDataJpa> findAllAtivos();

    /**
     * Busca departamentos inativos
     */
    @Query("SELECT d FROM DepartamentoDataJpa d WHERE d.ativo = false")
    List<DepartamentoDataJpa> findAllInativos();

    /**
     * Busca departamentos por líder
     */
    List<DepartamentoDataJpa> findByLiderId(UUID liderId);

    /**
     * Busca departamentos por nome (case-insensitive, contendo)
     */
    List<DepartamentoDataJpa> findByNomeContainingIgnoreCase(String nome);

    /**
     * Verifica se nome do departamento já existe
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Conta departamentos ativos
     */
    @Query("SELECT COUNT(d) FROM DepartamentoDataJpa d WHERE d.ativo = true")
    Long countAtivos();

    /**
     * Busca departamentos sem líder
     */
    @Query("SELECT d FROM DepartamentoDataJpa d WHERE d.lider IS NULL AND d.ativo = true")
    List<DepartamentoDataJpa> findSemLider();

    /**
     * Busca departamentos com descrição
     */
    @Query("SELECT d FROM DepartamentoDataJpa d WHERE d.descricao IS NOT NULL AND d.descricao != ''")
    List<DepartamentoDataJpa> findWithDescricao();

    /**
     * Busca departamentos por texto na descrição
     */
    @Query("SELECT d FROM DepartamentoDataJpa d WHERE LOWER(d.descricao) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<DepartamentoDataJpa> findByDescricaoContaining(@Param("texto") String texto);
}
