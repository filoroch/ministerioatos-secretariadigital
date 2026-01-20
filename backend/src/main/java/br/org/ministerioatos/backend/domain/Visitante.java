package br.org.ministerioatos.backend.domain;

import br.org.ministerioatos.backend.domain.valueobjects.DadosEclesiasticos;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import br.org.ministerioatos.backend.domain.valueobjects.StatusEclesiastico;

import java.time.LocalDate;

public class Visitante extends Pessoa{
    public Visitante(
            String nome,
            Genero genero,
            String dataNascimento,
            String telefone,
            SituacaoEclesiastica situacaoEclesiastica,
            LocalDate dataInicioSituacaoElesiastica,
            String observacao
    ) {
        super(nome, genero);

        if (dataNascimento == null || dataNascimento.isBlank()) {
            this.dataNascimento = null;
        } else {
            this.dataNascimento = normalizeDataNascimento(dataNascimento);
        }

        if (telefone == null || telefone.isBlank()) {
            this.telefone = "";
        }  else {
            this.telefone = telefone;
        }

        this.dadosEclesiasticos = new DadosEclesiasticos(situacaoEclesiastica, dataInicioSituacaoElesiastica, observacao);
    }

    public Visitante(String nome, Genero genero){
       super(nome, genero);
        this.dataNascimento = null;
        this.dadosEclesiasticos = new DadosEclesiasticos(
                SituacaoEclesiastica.VISITANTE,
                LocalDate.now(),
                "");
    }
}
