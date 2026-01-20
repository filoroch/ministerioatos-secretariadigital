package br.org.ministerioatos.backend.domain;

import br.org.ministerioatos.backend.domain.valueobjects.DadosEclesiasticos;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import org.apache.el.stream.Optional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Pessoa {
    protected String nome;
    protected Genero genero;
    protected LocalDate dataNascimento;
    protected String telefone;
    protected DadosEclesiasticos dadosEclesiasticos;
    protected Map<TipoRelacao, Pessoa> relacoes = new EnumMap<>(TipoRelacao.class);

    /// construir minimo geral
    public Pessoa(String nome, Genero genero) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }

        if (genero == null) {
            throw new IllegalArgumentException("Gênero obrigatório");
        }

        this.nome = nome;
        this.genero = genero;
    }


    protected static LocalDate normalizeDataNascimento(String dataNascimento) {
        try {
            return LocalDate.parse(dataNascimento);
        } catch (DateTimeParseException ex) {
            var brazilianFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dataNascimento, brazilianFormater);
        }
    }

    public String getNome() {
        return nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
