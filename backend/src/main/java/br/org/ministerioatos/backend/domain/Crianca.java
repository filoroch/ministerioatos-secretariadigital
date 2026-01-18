package br.org.ministerioatos.backend.domain;

import br.org.ministerioatos.backend.domain.valueobjects.DadosEclesiasticos;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;

import java.time.LocalDate;

public class Crianca extends Pessoa {

    public Crianca(
            String nome,
            Genero genero,
            String dataNascimento,
            SituacaoEclesiastica situacao,
            LocalDate inicioSituacao,
            TipoRelacao tipoRelacao,
            String nomePessoaRelacao,
            Genero generoPessoaRelacao
    ) {
        super(nome,  genero);
        this.dataNascimento = normalizeDataNascimento(dataNascimento);
        this.dadosEclesiasticos = new DadosEclesiasticos(situacao, inicioSituacao, "");
        this.relacoes.put(tipoRelacao, new Visitante(nomePessoaRelacao, generoPessoaRelacao));
    }
}
