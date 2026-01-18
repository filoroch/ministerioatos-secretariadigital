package br.org.ministerioatos.backend.domain;

import br.org.ministerioatos.backend.domain.valueobjects.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.Map;

import static br.org.ministerioatos.backend.domain.Pessoa.normalizeDataNascimento;
import static org.junit.jupiter.api.Assertions.*;


class PessoaTest {

    @BeforeEach
    void setUp() {

    }

    @Nested
    @DisplayName("normalizeDataNascimento")
    class NormalizeDataNascimento {

        @Test
        @DisplayName("Deve retornar a data de nascimento no formato iso 8601")
        void normalizeDataNascimentoToBrazilianFormat (){
            var genericDate = "08/03/2004";
            var expectedDate = LocalDate.of(2004, 3, 8);

            var formattedDate = normalizeDataNascimento(genericDate);

            assertNotNull(formattedDate);
            assertInstanceOf(LocalDate.class, formattedDate);
            assertEquals(expectedDate, formattedDate);
        }
    }

    @Nested
    @DisplayName("Criar pessoas")
    class CriarPessoas{


        @Test
        @DisplayName("Deve criar um visitante quando informar parametros minimos")
        void deveCriarUmVisitanteComParametrosMinimos(){
            //cenário
            var expectedName = "Neliel Odeshowak";
            var expectedGender = Genero.FEMININO;
            var expectedBirthDate = "";
            var expectedPhone = "27 9 9999 9999";
            var expectedType = SituacaoEclesiastica.VISITANTE;
            var expectedInicioSituacaoEclesiastica = LocalDate.now();
            var expectedObs = "";

            //ação
            var visitante = new Visitante(
                    expectedName,
                    expectedGender,
                    expectedBirthDate,
                    expectedPhone,
                    expectedType,
                    expectedInicioSituacaoEclesiastica,
                    expectedObs
            );

            //verificação
            assertNotNull(visitante);
            assertEquals(expectedName, visitante.getNome());
            assertEquals(expectedGender, visitante.getGenero());
            assertNull(visitante.getDataNascimento());
            assertNotNull(visitante.dadosEclesiasticos);
            assertEquals(expectedType, visitante.dadosEclesiasticos.getSituacao());
            assertEquals(expectedInicioSituacaoEclesiastica, visitante.dadosEclesiasticos.getDataIncioSituacao());
            assertNull(visitante.dadosEclesiasticos.getDataFimSituacao());
            assertEquals(expectedObs, visitante.dadosEclesiasticos.getObservacoes());
        }

        @Test
        @DisplayName("Deve criar uma criança quando informar parametros minimos")
        void deveCriarUmaCriancaComCamposMinimos(){
            //cenário
            var expectedName = "Son Gohan";
            var expectedGender = Genero.MASCULINO;
            var expectedBirthDate = "2020-11-20";
            var expectedType = SituacaoEclesiastica.CRIANCA;
            var expectedDataInicioSituacaoEclesiastica = LocalDate.now();
            var expectedRelationType = TipoRelacao.PAI;
            var expectedRelationName = "Nnoitora Schulztafel";
            var expectedRelationGender = Genero.MASCULINO;

            //ação
            var crianca = new Crianca(expectedName, expectedGender, expectedBirthDate, expectedType, expectedDataInicioSituacaoEclesiastica, expectedRelationType, expectedRelationName, expectedRelationGender);

            Map<TipoRelacao, Pessoa> relations = crianca.relacoes;

            //verificação
            assertNotNull(crianca);
            assertEquals(expectedName, crianca.getNome());
            assertEquals(expectedGender, crianca.getGenero());
            assertInstanceOf(LocalDate.class, crianca.getDataNascimento());
            assertEquals(LocalDate.parse(expectedBirthDate), crianca.getDataNascimento());
            assertEquals(expectedType, crianca.dadosEclesiasticos.getSituacao());
            assertEquals(expectedDataInicioSituacaoEclesiastica, crianca.dadosEclesiasticos.getDataIncioSituacao());
            assertNull(crianca.dadosEclesiasticos.getDataFimSituacao());
            assertEquals(expectedRelationName, relations.get(TipoRelacao.PAI).getNome());
            assertTrue(!relations.isEmpty());
        }
    }

}