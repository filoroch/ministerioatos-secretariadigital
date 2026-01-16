-- ============================================
-- SISTEMA SECRETARIA DIGITAL - MINISTÉRIO ATOS
-- Schema Refatorado com Segurança e Integridade
-- ============================================

-- Habilitar extensão para UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Limpeza Inicial (DROP TABLE)
DROP TABLE IF EXISTS documento CASCADE;
DROP TABLE IF EXISTS professor_curso CASCADE;
DROP TABLE IF EXISTS nota CASCADE;
DROP TABLE IF EXISTS avaliacao CASCADE;
DROP TABLE IF EXISTS presenca CASCADE;
DROP TABLE IF EXISTS matricula_curso CASCADE;
DROP TABLE IF EXISTS aula CASCADE;
DROP TABLE IF EXISTS frequencia_nucleo CASCADE;
DROP TABLE IF EXISTS participante_departamento CASCADE;
DROP TABLE IF EXISTS curso CASCADE;
DROP TABLE IF EXISTS nucleo CASCADE;
DROP TABLE IF EXISTS departamento CASCADE;
DROP TABLE IF EXISTS relacionamento CASCADE;
DROP TABLE IF EXISTS dados_eclesiasticos CASCADE;
DROP TABLE IF EXISTS rede_social CASCADE;
DROP TABLE IF EXISTS contato CASCADE;
DROP TABLE IF EXISTS endereco CASCADE;
DROP TABLE IF EXISTS pessoa CASCADE;

-- 1. Tabela Base: Pessoas (Identidade Imutável)
CREATE TABLE pessoa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE CHECK (cpf ~ '^\d{11}$'), -- Apenas números, validado
    genero VARCHAR(20) CHECK (genero IN ('Masculino', 'Feminino')),
    data_nascimento DATE,
    profissao VARCHAR(100),
    estado_civil VARCHAR(50) CHECK (estado_civil IN ('Solteiro', 'Casado', 'Divorciado', 'Viúvo')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices essenciais
CREATE INDEX idx_pessoa_cpf ON pessoa(cpf);
CREATE INDEX idx_pessoa_nome ON pessoa(nome);


-- 2. Tabela de Endereços (1:N)
CREATE TABLE endereco (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pessoa_id UUID NOT NULL,
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado CHAR(2) CHECK (estado ~ '^[A-Z]{2}$'),
    cep CHAR(8) CHECK (cep ~ '^\d{8}$'), -- Apenas números
    complemento VARCHAR(100),
    ponto_referencia VARCHAR(255),
    endereco_atual BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

-- Garantir apenas um endereço atual por pessoa
CREATE UNIQUE INDEX idx_unico_endereco_atual ON endereco(pessoa_id) WHERE endereco_atual = TRUE;

-- Índice para busca rápida
CREATE INDEX idx_endereco_pessoa ON endereco(pessoa_id);


-- 3. Tabela de Contatos (1:N)
CREATE TABLE contato (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pessoa_id UUID NOT NULL,
    tipo VARCHAR(50) CHECK (tipo IN ('Telefone', 'Email', 'WhatsApp')),
    valor VARCHAR(255) NOT NULL,
    principal BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

-- Garantir apenas um contato principal por tipo por pessoa
CREATE UNIQUE INDEX idx_unico_contato_principal ON contato(pessoa_id, tipo) WHERE principal = TRUE;

-- Índices para performance
CREATE INDEX idx_contato_pessoa ON contato(pessoa_id);
CREATE INDEX idx_contato_valor ON contato(valor);


-- 4. Tabela de Redes Sociais (1:N)
CREATE TABLE rede_social (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pessoa_id UUID NOT NULL,
    plataforma VARCHAR(50) CHECK (plataforma IN ('Facebook', 'Instagram', 'TikTok', 'Twitter')),
    url_perfil VARCHAR(255),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE INDEX idx_rede_social_pessoa ON rede_social(pessoa_id);


-- 5. Dados Eclesiásticos (Histórico de Papéis)
CREATE TABLE dados_eclesiasticos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pessoa_id UUID NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('Membro', 'Congregado', 'Criança', 'Visitante')),
    data_inicio_vinculo DATE DEFAULT CURRENT_DATE,
    data_fim_vinculo DATE, -- Se NULL, vínculo está ativo
    
    -- Campos Específicos (Nullable)
    situacao VARCHAR(50) CHECK (situacao IN ('Ativo', 'Em Disciplina', 'Aguardando Batismo')),
    data_batismo DATE,
    batizado_espirito_santo BOOLEAN DEFAULT FALSE,
    atuacao VARCHAR(100), -- Instrumentista, Midia
    data_conversao DATE,
    data_primeira_visita DATE,
    observacoes TEXT,
    
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

-- Garantir apenas UM vínculo ativo por pessoa (CRÍTICO!)
CREATE UNIQUE INDEX idx_unico_vinculo_ativo ON dados_eclesiasticos(pessoa_id) WHERE data_fim_vinculo IS NULL;

-- Índices para queries frequentes
CREATE INDEX idx_dados_eclesiasticos_pessoa ON dados_eclesiasticos(pessoa_id);
CREATE INDEX idx_dados_eclesiasticos_tipo ON dados_eclesiasticos(tipo);
CREATE INDEX idx_dados_eclesiasticos_ativo ON dados_eclesiasticos(pessoa_id, data_fim_vinculo) WHERE data_fim_vinculo IS NULL;


-- 6. Tabela de Relacionamentos (Vínculo Familiar e Social)
CREATE TABLE relacionamento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pessoa_origem_id UUID NOT NULL,
    pessoa_destino_id UUID NOT NULL,
    tipo_relacionamento VARCHAR(50) NOT NULL CHECK (tipo_relacionamento IN ('Pai', 'Mãe', 'Filho', 'Cônjuge', 'Noivo(a)', 'Padrasto', 'Madrasta', 'Primo', 'Prima', 'Irmão', 'Irmã')),
    FOREIGN KEY (pessoa_origem_id) REFERENCES pessoa(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_destino_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE INDEX idx_relacionamento_origem ON relacionamento(pessoa_origem_id);
CREATE INDEX idx_relacionamento_destino ON relacionamento(pessoa_destino_id);


-- 7. Entidades Organizacionais
CREATE TABLE departamento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT
);

CREATE TABLE nucleo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255),
    lider_id UUID, -- Líder do Núcleo
    FOREIGN KEY (lider_id) REFERENCES pessoa(id) ON DELETE SET NULL
);

CREATE INDEX idx_nucleo_lider ON nucleo(lider_id);


CREATE TABLE curso (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);


-- 8. Associação e Gestão

-- Pessoa participa de Departamento
CREATE TABLE participante_departamento (
    pessoa_id UUID NOT NULL,
    departamento_id UUID NOT NULL,
    funcao VARCHAR(100) CHECK (funcao IN ('Líder', 'Integrante', 'Coordenador')),
    PRIMARY KEY (pessoa_id, departamento_id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE,
    FOREIGN KEY (departamento_id) REFERENCES departamento(id) ON DELETE CASCADE
);

CREATE INDEX idx_participante_departamento_pessoa ON participante_departamento(pessoa_id);
CREATE INDEX idx_participante_departamento_depto ON participante_departamento(departamento_id);


-- Pessoa frequenta Núcleo
CREATE TABLE frequencia_nucleo (
    pessoa_id UUID NOT NULL,
    nucleo_id UUID NOT NULL,
    papel VARCHAR(50) CHECK (papel IN ('Visitante', 'Frequentador Assíduo', 'Anfitrião')),
    PRIMARY KEY (pessoa_id, nucleo_id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE,
    FOREIGN KEY (nucleo_id) REFERENCES nucleo(id) ON DELETE CASCADE
);

CREATE INDEX idx_frequencia_nucleo_pessoa ON frequencia_nucleo(pessoa_id);
CREATE INDEX idx_frequencia_nucleo_nucleo ON frequencia_nucleo(nucleo_id);


-- 9. Gestão de Cursos

CREATE TABLE aula (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    curso_id UUID NOT NULL,
    data_aula DATE NOT NULL,
    pauta TEXT,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);

CREATE INDEX idx_aula_curso ON aula(curso_id);
CREATE INDEX idx_aula_data ON aula(data_aula);


CREATE TABLE matricula_curso (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    curso_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    data_matricula DATE DEFAULT CURRENT_DATE,
    status VARCHAR(50) CHECK (status IN ('Ativo', 'Concluido', 'Desistente')),
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

-- Garantir apenas uma matrícula ativa por pessoa por curso
CREATE UNIQUE INDEX idx_unica_matricula_ativa ON matricula_curso(curso_id, pessoa_id) WHERE status = 'Ativo';

CREATE INDEX idx_matricula_curso_pessoa ON matricula_curso(pessoa_id);
CREATE INDEX idx_matricula_curso_curso ON matricula_curso(curso_id);


CREATE TABLE presenca (
    aula_id UUID NOT NULL,
    pessoa_id UUID NOT NULL, 
    presente BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (aula_id, pessoa_id),
    FOREIGN KEY (aula_id) REFERENCES aula(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE INDEX idx_presenca_aula ON presenca(aula_id);
CREATE INDEX idx_presenca_pessoa ON presenca(pessoa_id);


CREATE TABLE avaliacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    curso_id UUID NOT NULL,
    titulo VARCHAR(100),
    data_aplicacao DATE,
    nota_maxima DECIMAL(5,2) DEFAULT 10.0,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);

CREATE INDEX idx_avaliacao_curso ON avaliacao(curso_id);


CREATE TABLE nota (
    avaliacao_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    valor DECIMAL(5,2) CHECK (valor >= 0 AND valor <= 10),
    comentarios TEXT,
    PRIMARY KEY (avaliacao_id, pessoa_id),
    FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE INDEX idx_nota_pessoa ON nota(pessoa_id);


CREATE TABLE professor_curso (
    curso_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    PRIMARY KEY (curso_id, pessoa_id),
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE INDEX idx_professor_curso_pessoa ON professor_curso(pessoa_id);


-- 10. Documentos
CREATE TABLE documento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pessoa_id UUID NOT NULL,
    tipo VARCHAR(50) CHECK (tipo IN ('Batismo', 'Apresentacao', 'Carteirinha', 'Outros')),
    url_arquivo VARCHAR(255),
    versao VARCHAR(20),
    data_emissao DATE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE INDEX idx_documento_pessoa ON documento(pessoa_id);


-- ============================================
-- VALIDAÇÕES DE NEGÓCIO (Triggers)
-- ============================================

-- Trigger: Garantir que professor seja Membro ativo
CREATE OR REPLACE FUNCTION check_professor_membro()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM dados_eclesiasticos 
        WHERE pessoa_id = NEW.pessoa_id 
        AND tipo = 'Membro' 
        AND data_fim_vinculo IS NULL
    ) THEN
        RAISE EXCEPTION 'Professor deve ser um Membro ativo';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_professor
BEFORE INSERT OR UPDATE ON professor_curso
FOR EACH ROW EXECUTE FUNCTION check_professor_membro();

-- Trigger: Garantir que líder de núcleo seja Membro ativo
CREATE OR REPLACE FUNCTION check_lider_nucleo()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.lider_id IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM dados_eclesiasticos 
        WHERE pessoa_id = NEW.lider_id 
        AND tipo = 'Membro' 
        AND data_fim_vinculo IS NULL
    ) THEN
        RAISE EXCEPTION 'Líder de núcleo deve ser um Membro ativo';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_lider_nucleo
BEFORE INSERT OR UPDATE ON nucleo
FOR EACH ROW EXECUTE FUNCTION check_lider_nucleo();
