package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.NucleoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NucleoRepository extends JpaRepository<NucleoDataJpa, UUID> {

    /**
     * Busca núcleo por nome
     */
    Optional<NucleoDataJpa> findByNomeIgnoreCase(String nome);

    /**
     * Busca núcleos ativos
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE n.ativo = true")
    List<NucleoDataJpa> findAllAtivos();

    /**
     * Busca núcleos inativos
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE n.ativo = false")
    List<NucleoDataJpa> findAllInativos();

    /**
     * Busca núcleos por líder
     */
    List<NucleoDataJpa> findByLiderId(UUID liderId);

    /**
     * Busca núcleos por colíder
     */
    List<NucleoDataJpa> findByColiderId(UUID coliderId);

    /**
     * Busca núcleos por nome (case-insensitive, contendo)
     */
    List<NucleoDataJpa> findByNomeContainingIgnoreCase(String nome);

    /**
     * Verifica se nome do núcleo já existe
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Conta núcleos ativos
     */
    @Query("SELECT COUNT(n) FROM NucleoDataJpa n WHERE n.ativo = true")
    Long countAtivos();

    /**
     * Busca núcleos sem líder
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE n.lider IS NULL AND n.ativo = true")
    List<NucleoDataJpa> findSemLider();

    /**
     * Busca núcleos sem colíder
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE n.colider IS NULL AND n.ativo = true")
    List<NucleoDataJpa> findSemColider();

    /**
     * Busca núcleos com descrição
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE n.descricao IS NOT NULL AND n.descricao != ''")
    List<NucleoDataJpa> findWithDescricao();

    /**
     * Busca núcleos por texto na descrição
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE LOWER(n.descricao) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<NucleoDataJpa> findByDescricaoContaining(@Param("texto") String texto);

    /**
     * Busca núcleos onde uma pessoa é líder ou colíder
     */
    @Query("SELECT n FROM NucleoDataJpa n WHERE (n.lider.id = :pessoaId OR n.colider.id = :pessoaId) AND n.ativo = true")
    List<NucleoDataJpa> findByLiderOrColider(@Param("pessoaId") UUID pessoaId);
}
