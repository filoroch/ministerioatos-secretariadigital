package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "relacionamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RelacionamentoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_origem_id", nullable = false)
    private PessoaDataJpa origem;

    @ManyToOne
    @JoinColumn(name = "pessoa_destino_id", nullable = false)
    private PessoaDataJpa destino;

    @Enumerated(EnumType.STRING)
    private TipoRelacao tipo;

    @Column(columnDefinition = "text")
    private String observacoes;

    private LocalDateTime deletedAt;
}

