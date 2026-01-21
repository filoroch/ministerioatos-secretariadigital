package br.org.ministerioatos.backend.application.dto;

import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;

import java.time.LocalDate;

public record RegisterCriancaOutput(
        String id,
        String nome,
        String genero,
        LocalDate dataNascimento,
        String idResponsavel,
        String nomeResponsavel,
        String telefoneResponsavel,
        String generoResponsavel,
        String tipoRelacao
){}
