package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "aulas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AulaDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoDataJpa curso;

    private Integer numeroAula;
    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    private LocalDate dataAula;
}

