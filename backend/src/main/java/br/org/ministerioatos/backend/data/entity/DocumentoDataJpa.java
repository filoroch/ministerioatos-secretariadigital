package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "documentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentoDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaDataJpa pessoa;

    @Enumerated(EnumType.STRING)
    private DocumentoDocumentoTipo tipo;

    @Column(unique = true)
    private String numeroDocumento;

    private Integer versaoAtual;
    private LocalDate dataEmissao;
    private LocalDate dataValidade;
    private boolean ativo;

    @Column(columnDefinition = "text")
    private String observacoes;

    public enum DocumentoDocumentoTipo {
        CERTIDAO_BATISMO,
        CERTIDAO_APRESENTACAO,
        CARTEIRINHA_MEMBRO,
        CARTA_TRANSFERENCIA,
        DECLARACAO_FREQUENCIA,
        OUTRO
    }
}

