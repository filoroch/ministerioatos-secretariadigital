package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas_membros")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaMembroDataJpa extends AuditableEntity {

    @Id
    @Column(name = "pessoa_id")
    private UUID pessoaId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaDataJpa pessoa;

    private boolean batizadoAguas;
    private LocalDate dataBatismoAguas;
    private boolean batizadoEspirito;
    private LocalDate dataBatismoEspirito;
    private boolean ativo;
}

