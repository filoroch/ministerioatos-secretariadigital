# Diagrama Entidade-Relacionamento Conceitual

Este digrama reflete o modelo refatorado para suportar histórico eclesiástico e dados normalizados de contato.

```mermaid
erDiagram
    %% TABELA PRINCIPAL - Dados Básicos Imutáveis
    PESSOA {
        string id PK
        string nome
        string cpf UK
        string genero
        date data_nascimento
        datetime created_at
    }

    %% Subtabela 1: Dados Eclesiásticos (Específicos por Papel)
    DADOS_ECLESIASTICOS {
        string id PK
        string pessoa_id FK
        string tipo "ENUM: Membro, Congregado, Criança, Visitante"
        date data_inicio_vinculo
        date data_fim_vinculo "Nullable (Ativo se NULL)"
        %% Específicos
        string situacao "Ativo, Falecido, Transferido, Novo Convertido"
        date data_batismo
        boolean batizado_espirito_santo
        string atuacao "Midia, Louvor"
        string observacoes
    }

    %% Subtabela 2: Contatos
    CONTATO {
        string id PK
        string pessoa_id FK
        string tipo "Email, Telefone, WhatsApp"
        string valor
        boolean principal
    }

    %% Subtabela 3: Endereço
    ENDERECO {
        string id PK
        string pessoa_id FK
        string logradouro
        string cidade
        string estado
        boolean endereco_atual
    }

    RELACIONAMENTO {
        string id PK
        string pessoa_origem_id FK
        string pessoa_destino_id FK
        string tipo_relacionamento "Pai, Mãe, Cônjuge, etc"
    }

    %% Cursos e Deptos
    CURSO { string id PK string nome }
    DEPARTAMENTO { string id PK string nome }
    NUCLEO { string id PK string nome }

    %% Relacionamentos
    PESSOA ||--o{ DADOS_ECLESIASTICOS : "possui histórico"
    PESSOA ||--o{ CONTATO : "possui"
    PESSOA ||--o{ ENDERECO : "reside"
    PESSOA ||--o{ RELACIONAMENTO : "vinculo familiar"
    
    PESSOA }|--o{ DEPARTAMENTO : "participa"
    PESSOA }|--o{ CURSO : "matriculado"
```

## Descrição das Mudanças

### Normalização
*   **DADOS_ECLESIASTICOS**: Substitui as tabelas `MEMBRO`, `CONGREGADO`, `VISITANTE`. Agora, uma mudança de status (ex: Visitante vira Membro) é apenas um novo registro nesta tabela, mantendo o histórico anterior.
*   **CONTATO e ENDERECO**: Saíram da tabela `PESSOA` para permitir múltiplos registros (ex: Telefone Residencial e Celular; Endereço Atual e Antigo).

### Identidade
*   **PESSOA**: Mantém apenas os dados vitais e imutáveis do indivíduo.
