package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "pessoas_criancas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaCriancaDataJpa extends AuditableEntity {

    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaDataJpa pessoa;

    @Column(name = "obs_crianca")
    private String obsCrianca;
}
