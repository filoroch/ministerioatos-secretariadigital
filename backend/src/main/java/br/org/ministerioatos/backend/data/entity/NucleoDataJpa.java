package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "nucleos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NucleoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(columnDefinition = "text")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "lider_id")
    private PessoaDataJpa lider;

    @ManyToOne
    @JoinColumn(name = "colider_id")
    private PessoaDataJpa colider;

    private boolean ativo;
}

