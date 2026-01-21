package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import br.org.ministerioatos.backend.domain.valueobjects.EstadoCivil;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PessoaDataJpa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String cpf;
    private String rg;
    private Genero genero;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String profissao;
    private EstadoCivil estadoCivil;
    private String obseravacoes;
}
