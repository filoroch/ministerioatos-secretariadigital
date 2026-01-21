package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaCursoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaDataJpa pessoa;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoDataJpa curso;

    private LocalDate dataMatricula;
    private boolean concluido;
    private LocalDate dataConclusao;
}

