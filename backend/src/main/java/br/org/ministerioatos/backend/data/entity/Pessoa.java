package br.org.ministerioatos.backend.data.entity;

import br.org.ministerioatos.backend.data.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "pessoas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pessoa extends AuditableEntity {

    @Id
    private UUID id;

}
