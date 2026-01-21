package br.org.ministerioatos.backend.application.dto;

import java.time.LocalDate;

public record RegisterVisistanteInput(
        String nomeCompleto,
        String cpf,
        String rg,
        String telefone,
        String email,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String UF,
        String CEP,
        LocalDate dataNascimento
) {
}
