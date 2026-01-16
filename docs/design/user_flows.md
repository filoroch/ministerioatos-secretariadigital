# Fluxos de Usuário - Exemplos Práticos (Modelo Refatorado)

Este documento descreve como o novo modelo de dados (Composição) atende aos cenários de cadastro.

## Personagens
1.  **Carlos Silva** (Membro, Pai)
2.  **Ana Silva** (Criança, Filha)
3.  **Marcos Oliveira** (Visitante, Primo do Carlos)

## 1. Cadastro de Membro (Carlos)
Carlos é cadastrado pela Secretaria.
*   **Tabela PESSOA**: Cria registro (Nome: "Carlos Silva", CPF: "111..."). ID: `P01`.
*   **Tabela ENDERECO**: Cria registro vinculado a `P01` (Rua A, Atual=true).
*   **Tabela CONTATO**: Cria emails e telefones vinculados a `P01`.
*   **Tabela DADOS_ECLESIASTICOS**:
    *   Cria registro vinculado a `P01`.
    *   Tipo: "Membro", Situação: "Ativo", Data Início: Hoje.
    *   Data Batismo: "2010-01-15".

## 2. Cadastro de Criança (Ana)
Ana é apresentada.
*   **Tabela PESSOA**: Cria registro (Nome: "Ana Silva", Nascimento: "2020..."). ID: `P02`.
*   **Tabela ENDERECO**: Pode copiar o endereço do pai ou criar novo registro vinculado a `P02`.
*   **Tabela RELACIONAMENTO**:
    *   Origem: `P01` (Carlos) -> Destino: `P02` (Ana), Tipo: "Pai".
*   **Tabela DADOS_ECLESIASTICOS**:
    *   Cria registro vinculado a `P02`.
    *   Tipo: "Criança".

## 3. Transição: Visitante vira Membro
Marcos (Visitante, ID `P03`) decide se batizar.
1.  **Encerrar Vínculo Anterior**:
    *   Na tabela `DADOS_ECLESIASTICOS`, busca o registro onde pessoa_id=`P03` e Tipo="Visitante".
    *   Atualiza `data_fim_vinculo` = Ontem.
2.  **Criar Novo Vínculo**:
    *   Insere novo registro em `DADOS_ECLESIASTICOS` para `P03`.
    *   Tipo: "Membro", Data Início: Hoje, Situação: "Novo Convertido" (ou Batizado).

Isso mantém o histórico de que Marcos foi visitante por 6 meses antes de se tornar membro.

## 4. Gestão de Grupos
*   **Departamentos**: Tabela `participante_departamento` vincula `PESSOA` e `DEPARTAMENTO` com uma função. Regras de negócio na aplicação validam se a pessoa tem status de Membro ativo em `DADOS_ECLESIASTICOS` antes de permitir ser Líder.
