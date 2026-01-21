package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import br.org.ministerioatos.backend.domain.valueobjects.TipoPessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_historico_tipo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaHistoricoTipoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaDataJpa pessoa;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Column(columnDefinition = "text")
    private String motivoTransicao;
}

