package br.org.ministerioatos.backend.application.usecase;

import br.org.ministerioatos.backend.application.dto.RegisterPessoaInput;
import br.org.ministerioatos.backend.data.entity.PessoaDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaEnderecoDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaCriancaDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaCongregadoDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaMembroDataJpa;
import br.org.ministerioatos.backend.data.entity.PessoaHistoricoTipoDataJpa;
import br.org.ministerioatos.backend.data.entity.EnderecoDataJpa;
import br.org.ministerioatos.backend.domain.valueobjects.TipoPessoa;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegisterPessoaUseCase {

    @Autowired
    IPessoaRepository pessoaRepository;

    @Autowired
    IEnderecoRepository enderecoRepository;

    @Autowired
    EntityManager entityManager;

    public void execute (RegisterPessoaInput input) {

        /// Verifica se o endereço existe
        if (!enderecoRepository.existsByCep(input.CEP())) {
            var dataEndereco = new EnderecoDataJpa();
            dataEndereco.setRua(input.rua());
            dataEndereco.setNumero(input.numero());
            dataEndereco.setBairro(input.bairro());
            dataEndereco.setCidade(input.cidade());
            dataEndereco.setEstado(input.UF());

            enderecoRepository.saveAndFlush(dataEndereco);
        }

        var dataEndereco = enderecoRepository.findByCep(input.CEP());

        /// Cria a entidade de dados de pessoa
        var dataPessoa = new PessoaDataJpa();
        dataPessoa.setNome(input.nomeCompleto());
        dataPessoa.setCpf(input.cpf().isEmpty() ? "" : input.cpf());
        dataPessoa.setRg(input.rg().isEmpty() ? "" : input.rg());
        dataPessoa.setDataNascimento(input.dataNascimento());
        dataPessoa.setTelefone(input.telefone().isBlank() ? "" : input.telefone());
        dataPessoa.setEmail(input.email().isBlank() ? "" : input.email());

        var createdPessoa = pessoaRepository.saveAndFlush(dataPessoa);

        /// Associa os endereços a pessoa
        var pessoaEndereco = new PessoaEnderecoDataJpa();
        pessoaEndereco.setPessoa(createdPessoa);
        pessoaEndereco.setEndereco(dataEndereco);
        pessoaEndereco.setDataInicio(LocalDate.now());
        pessoaEndereco.setDataFim(null);

        entityManager.persist(pessoaEndereco);
        entityManager.flush();

        switch (input.tipo()) {
            case CRIANCA:
                saveHistoryOnPessoa(createdPessoa, TipoPessoa.CRIANCA);

                var pessoaCriancaData = new PessoaCriancaDataJpa();
                pessoaCriancaData.setPessoa(createdPessoa);
                pessoaCriancaData.setResponsaveis(input.relacoes());

                entityManager.persist(pessoaCriancaData);
                entityManager.flush();
                break;

            case CONGREGADO:
                saveHistoryOnPessoa(createdPessoa, TipoPessoa.CONGREGADO);

                var pessoaCongregadoData = new PessoaCongregadoDataJpa();
                pessoaCongregadoData.setPessoa(createdPessoa);
                pessoaCongregadoData.setDataConversao(input.dataConversao());
                pessoaCongregadoData.setSituacao(null);

                entityManager.persist(pessoaCongregadoData);
                entityManager.flush();
                break;

            case MEMBRO:
                saveHistoryOnPessoa(createdPessoa, TipoPessoa.MEMBRO);

                var pessoaMembroData = new PessoaMembroDataJpa();
                pessoaMembroData.setPessoa(createdPessoa);
                pessoaMembroData.setBatizadoAguas(input.batismoAguas());
                pessoaMembroData.setDataBatismoAguas(input.dataBatismoAguas());
                pessoaMembroData.setBatizadoEspirito(input.batismoEspiritoSanto());

                entityManager.persist(pessoaMembroData);
                entityManager.flush();
                break;

            case VISITANTE:
                saveHistoryOnPessoa(createdPessoa, TipoPessoa.VISITANTE);
                break;
        }


    }
    private void saveHistoryOnPessoa (PessoaDataJpa pessoa, TipoPessoa tipo){
        var pessoaHistoricoData = new PessoaHistoricoTipoDataJpa();
        pessoaHistoricoData.setPessoa(pessoa);
        pessoaHistoricoData.setTipo(tipo);
        pessoaHistoricoData.setDataInicio(LocalDate.now());
        pessoaHistoricoData.setDataFim(null);

        entityManager.persist(pessoaHistoricoData);
        entityManager.flush();
    }
}
