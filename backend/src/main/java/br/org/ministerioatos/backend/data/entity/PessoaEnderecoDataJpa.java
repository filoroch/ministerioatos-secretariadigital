package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_enderecos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaEnderecoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaDataJpa pessoa;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private EnderecoDataJpa endereco;

    @Enumerated(EnumType.STRING)
    private EnderecoTipo tipo;

    private boolean principal;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    public enum EnderecoTipo {
        RESIDENCIAL,
        COMERCIAL,
        CORRESPONDENCIA
    }
}

