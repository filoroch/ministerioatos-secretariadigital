package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "presencas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PresencaDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_curso_id", nullable = false)
    private PessoaCursoDataJpa pessoaCurso;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private AulaDataJpa aula;

    @Enumerated(EnumType.STRING)
    private TipoPresenca tipoPresenca;

    @Column(columnDefinition = "text")
    private String justificativa;

    public enum TipoPresenca {
        PRESENTE, ATRASADO, JUSTIFICADO, FALTOU
    }
}

