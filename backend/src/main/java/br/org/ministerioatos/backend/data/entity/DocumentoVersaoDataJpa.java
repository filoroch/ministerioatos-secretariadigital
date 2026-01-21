package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "documentos_versoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentoVersaoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false)
    private DocumentoDataJpa documento;

    private Integer versao;

    @Column(columnDefinition = "text")
    private String urlArquivo;

    private String nomeArquivo;
    private Long tamanhoBytes;
    private String mimeType;

    @Column(columnDefinition = "text")
    private String alteracoes;
}

