package br.org.ministerioatos.backend.application.dto;

import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import br.org.ministerioatos.backend.domain.valueobjects.TipoPessoa;

import java.time.LocalDate;

public record RegisterCriancaInput(
        String nome,
        Genero genero,
        LocalDate dataNascimento,
        String nomeResponsavel,
        TipoPessoa tipoPessoaResponsavel,
        Genero generoResponsavel,
        String idResponsavel,
        String telefoneResponsavel
) {
}
