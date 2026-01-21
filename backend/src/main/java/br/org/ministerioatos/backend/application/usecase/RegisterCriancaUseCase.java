package br.org.ministerioatos.backend.application.usecase;

import br.org.ministerioatos.backend.application.dto.RegisterCriancaInput;
import br.org.ministerioatos.backend.application.dto.RegisterCriancaOutput;
import br.org.ministerioatos.backend.data.entity.PessoaCriancaDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaHistoricoTipoDataJpa;
import br.org.ministerioatos.backend.data.entity.RelacionamentoDataJpa;
import br.org.ministerioatos.backend.data.repository.PessoaCriancaRepository;
import br.org.ministerioatos.backend.data.repository.PessoaHistoricoTipoRepository;
import br.org.ministerioatos.backend.data.repository.PessoaRepository;
import br.org.ministerioatos.backend.data.repository.RelacionamentoRepository;
import br.org.ministerioatos.backend.domain.valueobjects.EstadoCivil;
import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import br.org.ministerioatos.backend.domain.valueobjects.TipoPessoa;
import br.org.ministerioatos.backend.domain.valueobjects.TipoRelacao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RegisterCriancaUseCase {

    /// Repositorios de pessoa, criança, historico e relacionamento
    private PessoaRepository pessoaRepository;
    private PessoaCriancaRepository criancaRepository;
    private PessoaHistoricoTipoRepository historicoTipoRepository;
    private RelacionamentoRepository relacionamentoRepository;

    public RegisterCriancaUseCase(
            PessoaRepository pessoaRepository,
            PessoaCriancaRepository criancaRepository,
            PessoaHistoricoTipoRepository historicoTipoRepository,
            RelacionamentoRepository relacionamentoRepository
    ) {
        this.pessoaRepository = pessoaRepository;
        this.criancaRepository = criancaRepository;
        this.historicoTipoRepository = historicoTipoRepository;
        this.relacionamentoRepository = relacionamentoRepository;
    }

    private PessoaDataJpa validadeResponsavelInput(RegisterCriancaInput input) {
        PessoaDataJpa responsavel;

        if (!input.idResponsavel().isEmpty()){
            responsavel = pessoaRepository
                    .findById(UUID.fromString(input.idResponsavel()))
                    .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));

            return responsavel;
        }

        if (!input.nomeResponsavel().isBlank() || !input.nomeResponsavel().isEmpty()) {
            responsavel = PessoaDataJpa.builder()
                    .nome(input.nomeResponsavel())
                    .cpf("")
                    .rg("")
                    .dataNascimento(null)
                    .telefone(input.telefoneResponsavel().isBlank() ? "" : input.telefoneResponsavel())
                    .email("")
                    .build();

            responsavel = pessoaRepository.saveAndFlush(responsavel);

            var responsavelHistoricoTipoData = PessoaHistoricoTipoDataJpa.builder()
                    .pessoa(responsavel)
                    .tipo(TipoPessoa.VISITANTE)
                    .dataInicio(LocalDate.now())
                    .build();

            historicoTipoRepository.saveAndFlush(responsavelHistoricoTipoData);

            return responsavel;
        } else {
            throw new IllegalArgumentException("É necessário informar o responsável pela criança");
        }
    }

    public RegisterCriancaOutput execute(RegisterCriancaInput input) {

        /// Verificar os campos que o DTO e o frontend podem ter deixado passar
        var responsavel = validadeResponsavelInput(input);

        /// Cria a entidade de dados da criança
        var crianca = pessoaRepository.saveAndFlush(
                PessoaDataJpa.builder()
                .nome(input.nome())
                .genero(input.genero())
                .estadoCivil(EstadoCivil.SOLTEIRO)
                .obseravacoes("")
                .cpf("")
                .rg("")
                .dataNascimento(input.dataNascimento())
                .telefone("")
                .email("")
                .build()
        );

        var pessoaCrianca = criancaRepository.saveAndFlush(
                PessoaCriancaDataJpa.builder()
                .pessoa(crianca)
                .obsCrianca("")
                .build()
        );

        var criancaHistoricoTipoData = historicoTipoRepository.saveAndFlush(PessoaHistoricoTipoDataJpa.builder()
                .pessoa(crianca)
                .tipo(TipoPessoa.CRIANCA)
                .dataInicio(LocalDate.now())
                .build()
        );

        var relacao = relacionamentoRepository.saveAndFlush(RelacionamentoDataJpa.builder()
                .origem(crianca)
                .destino(responsavel)
                .tipo((responsavel.getGenero() == Genero.MASCULINO) ? TipoRelacao.PAI : TipoRelacao.MAE)
                .build()
        );

        return new RegisterCriancaOutput(
                crianca.getId().toString(),
                crianca.getNome(),
                crianca.getGenero().toString(),
                crianca.getDataNascimento(),
                responsavel.getId().toString(),
                responsavel.getNome(),
                responsavel.getTelefone(),
                responsavel.getGenero().toString(),
                relacao.getTipo().toString()
        );
    }
}

