package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "departamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepartamentoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(columnDefinition = "text")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "lider_id")
    private PessoaDataJpa lider;

    private boolean ativo;
}

