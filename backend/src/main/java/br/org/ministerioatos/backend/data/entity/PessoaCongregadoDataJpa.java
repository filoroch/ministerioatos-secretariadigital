package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import br.org.ministerioatos.backend.domain.valueobjects.SituacaoEclesiastica;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_congregados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaCongregadoDataJpa extends AuditableEntity {

    @Id
    @Column(name = "pessoa_id")
    private UUID pessoaId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaDataJpa pessoa;

    private LocalDate dataConversao;

    @Enumerated(EnumType.STRING)
    private SituacaoEclesiastica situacao;

    @ManyToOne
    @JoinColumn(name = "discipulador_id")
    private PessoaDataJpa discipulador;

    private LocalDate dataInicioDiscipulado;
    private LocalDate dataConclusaoDiscipulado;

    @Column(columnDefinition = "text")
    private String observacoes;
    private boolean discipulado;
}

