package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "atuacoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AtuacaoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(columnDefinition = "text")
    private String descricao;

    private boolean ativa;
}

