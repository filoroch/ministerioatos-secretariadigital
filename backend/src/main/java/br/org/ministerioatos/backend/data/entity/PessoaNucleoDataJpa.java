package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_nucleos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaNucleoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaDataJpa pessoa;

    @ManyToOne
    @JoinColumn(name = "nucleo_id", nullable = false)
    private NucleoDataJpa nucleo;

    private LocalDate dataInicio;
    private LocalDate dataFim;
}

