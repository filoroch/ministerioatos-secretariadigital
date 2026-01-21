package br.org.ministerioatos.backend.application.dto;

import br.org.ministerioatos.backend.domain.valueobjects.EstadoCivil;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import br.org.ministerioatos.backend.domain.valueobjects.TipoPessoa;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record RegisterPessoaInput(
        String nomeCompleto,
        String cpf,
        String rg,
        LocalDate dataNascimento,
        Genero genero,
        EstadoCivil estadoCivil,
        String telefone,
        String email,

        /// Endereço
        String rua,
        String numero,
        String bairro,
        String cidade,
        String CEP,
        String UF,

        ///  Relações
        Map<TipoRelacao, String> relacoes,

        /// Dados eleciasticos
        TipoPessoa tipo,
        LocalDate dataConversao,
        boolean batismoAguas,
        LocalDate dataBatismoAguas,
        boolean batismoEspiritoSanto,
        List<String> departamentos
) {
}
