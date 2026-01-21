package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_departamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaDepartamentoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaDataJpa pessoa;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private DepartamentoDataJpa departamento;

    private boolean ehLider;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}

