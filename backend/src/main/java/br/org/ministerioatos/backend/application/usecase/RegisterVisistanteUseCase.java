package br.org.ministerioatos.backend.application.usecase;

import br.org.ministerioatos.backend.data.repository.PessoaCriancaRepository;
import br.org.ministerioatos.backend.data.repository.PessoaHistoricoTipoRepository;
import br.org.ministerioatos.backend.data.repository.PessoaRepository;
import br.org.ministerioatos.backend.data.repository.RelacionamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterVisistanteUseCase {

    /// Repositorios de pessoa e historico
    private PessoaRepository pessoaRepository;
    private PessoaHistoricoTipoRepository historicoTipoRepository;

    public RegisterVisistanteUseCase(
            PessoaRepository pessoaRepository,
            PessoaHistoricoTipoRepository historicoTipoRepository,
    ) {
        this.pessoaRepository = pessoaRepository;
        this.historicoTipoRepository = historicoTipoRepository;
    }

    public RegisterVisistanteOuput execute (RegisterVisistanteInput input) {

    }
}
