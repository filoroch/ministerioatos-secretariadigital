package br.org.ministerioatos.backend.domain.valueobjects;

import br.org.ministerioatos.backend.domain.SituacaoEclesiastica;

import java.time.LocalDate;

public class DadosEclesiasticos {
    private SituacaoEclesiastica situacao;
    private StatusEclesiastico status;
    private LocalDate dataIncioSituacao;
    private LocalDate dataFimSituacao;
    private String observacoes;

    public DadosEclesiasticos(
            SituacaoEclesiastica situacao,
            StatusEclesiastico status,
            LocalDate dataIncioSituacao,
            String observacoes
    ) {
        this.situacao = situacao;
        this.status = status;

        if (dataIncioSituacao == null){
            this.dataIncioSituacao = LocalDate.now();
        } else  {
            this.dataIncioSituacao = dataIncioSituacao;
        }

        this.dataFimSituacao = null;

        if (observacoes != null || !observacoes.isEmpty()){
            this.observacoes = observacoes;
        }

        this.observacoes = "";
    }

    public DadosEclesiasticos(
            SituacaoEclesiastica situacao,
            LocalDate dataIncioSituacao,
            String observacoes
    ) {
        this.situacao = situacao;
        this.status = StatusEclesiastico.SEM_STATUS_REGISTRADO;

        if (dataIncioSituacao == null){
            this.dataIncioSituacao = LocalDate.now();
        } else  {
            this.dataIncioSituacao = dataIncioSituacao;
        }

        this.dataFimSituacao = null;

        if (observacoes != null || !observacoes.isEmpty()){
            this.observacoes = observacoes;
        }

        this.observacoes = "";
    }

    public SituacaoEclesiastica getSituacao() {
        return situacao;
    }

    public StatusEclesiastico getStatus() {
        return status;
    }

    public LocalDate getDataIncioSituacao() {
        return dataIncioSituacao;
    }

    public LocalDate getDataFimSituacao() {
        return dataFimSituacao;
    }

    public String getObservacoes() {
        return observacoes;
    }
}
