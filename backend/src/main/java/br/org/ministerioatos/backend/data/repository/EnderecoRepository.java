package br.org.ministerioatos.backend.data.repository;

import br.org.ministerioatos.backend.data.entity.EnderecoDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoDataJpa, UUID> {

    /**
     * Busca endereço por CEP
     */
    Optional<EnderecoDataJpa> findByCep(String cep);

    /**
     * Verifica se endereço existe por CEP
     */
    boolean existsByCep(String cep);

    /**
     * Busca endereços por cidade
     */
    List<EnderecoDataJpa> findByCidadeIgnoreCase(String cidade);

    /**
     * Busca endereços por estado
     */
    List<EnderecoDataJpa> findByEstadoIgnoreCase(String estado);

    /**
     * Busca endereços por bairro
     */
    List<EnderecoDataJpa> findByBairroContainingIgnoreCase(String bairro);

    /**
     * Busca endereços por logradouro
     */
    List<EnderecoDataJpa> findByRuaContainingIgnoreCase(String rua);

    /**
     * Busca endereços por cidade e estado
     */
    List<EnderecoDataJpa> findByCidadeIgnoreCaseAndEstadoIgnoreCase(String cidade, String estado);

    /**
     * Busca endereços próximos por coordenadas (exemplo básico)
     */
    @Query("SELECT e FROM EnderecoDataJpa e WHERE " +
           "e.latitude IS NOT NULL AND e.longitude IS NOT NULL AND " +
           "e.latitude BETWEEN :latMin AND :latMax AND " +
           "e.longitude BETWEEN :lngMin AND :lngMax")
    List<EnderecoDataJpa> findByCoordinateRange(@Param("latMin") Double latMin,
                                               @Param("latMax") Double latMax,
                                               @Param("lngMin") Double lngMin,
                                               @Param("lngMax") Double lngMax);
}
