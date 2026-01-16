-- Seed Data for UUID Schema (PL/pgSQL Block for Sequential Execution)
-- Necessário para garantir que triggers vejam os dados inseridos anteriormente.

DO $$
DECLARE
    -- Variáveis para armazenar IDs gerados
    id_carlos UUID;
    id_ana UUID;
    id_pedro UUID;
    id_joao UUID;
    id_maria UUID;
    
    id_curso_batismo UUID;
    id_depto_midia UUID;
    id_depto_louvor UUID;
    id_aula_1 UUID;
    
BEGIN
    RAISE NOTICE 'Iniciando Seed de Dados...';

    -- 1. Inserir Pessoas e capturar IDs
    INSERT INTO pessoa (nome, cpf, genero, data_nascimento, profissao, estado_civil) 
    VALUES ('Carlos Silva', '11111100101', 'Masculino', '1980-01-01', 'Engenheiro', 'Casado')
    RETURNING id INTO id_carlos;

    INSERT INTO pessoa (nome, cpf, genero, data_nascimento, profissao, estado_civil) 
    VALUES ('Ana Silva', '11111100202', 'Feminino', '1982-02-02', 'Médica', 'Casado')
    RETURNING id INTO id_ana;

    INSERT INTO pessoa (nome, cpf, genero, data_nascimento, profissao, estado_civil) 
    VALUES ('Pedro Rocha', '11111100303', 'Masculino', '1990-03-03', 'Vendedor', 'Solteiro')
    RETURNING id INTO id_pedro;
    
    INSERT INTO pessoa (nome, cpf, genero, data_nascimento, profissao, estado_civil) 
    VALUES ('João Souza', '11111100404', 'Masculino', '1985-04-04', 'Professor', 'Divorciado')
    RETURNING id INTO id_joao;

    INSERT INTO pessoa (nome, cpf, genero, data_nascimento, profissao, estado_civil) 
    VALUES ('Maria Lima', '11111100505', 'Feminino', '1995-05-05', 'Designer', 'Solteiro')
    RETURNING id INTO id_maria;

    
    -- 2. Endereços
    INSERT INTO endereco (pessoa_id, logradouro, numero, bairro, cidade, estado, cep, endereco_atual) VALUES
    (id_carlos, 'Rua das Flores', '100', 'Centro', 'São Paulo', 'SP', '01001000', TRUE),
    (id_ana, 'Rua das Flores', '100', 'Centro', 'São Paulo', 'SP', '01001000', TRUE),
    (id_pedro, 'Av Paulista', '900', 'Bela Vista', 'São Paulo', 'SP', '01311000', TRUE);

    -- 3. Contatos
    INSERT INTO contato (pessoa_id, tipo, valor, principal) VALUES
    (id_carlos, 'Email', 'carlos@email.com', TRUE),
    (id_carlos, 'WhatsApp', '11999990001', TRUE),
    (id_ana, 'Email', 'ana@email.com', TRUE);

    -- 4. Dados Eclesiásticos (CRÍTICO: Inserir ANTES de cadastrar como líder/professor)
    -- Carlos: Membro Ativo
    INSERT INTO dados_eclesiasticos (pessoa_id, tipo, situacao, data_batismo) 
    VALUES (id_carlos, 'Membro', 'Ativo', '2010-01-01');

    -- Ana: Membro Ativo
    INSERT INTO dados_eclesiasticos (pessoa_id, tipo, situacao, data_batismo) 
    VALUES (id_ana, 'Membro', 'Ativo', '2012-05-05');

    -- Pedro: Congregado
    INSERT INTO dados_eclesiasticos (pessoa_id, tipo, situacao) 
    VALUES (id_pedro, 'Congregado', 'Aguardando Batismo');

    -- João: Membro (Professor)
    INSERT INTO dados_eclesiasticos (pessoa_id, tipo, situacao, data_batismo) 
    VALUES (id_joao, 'Membro', 'Ativo', '2015-10-10');

    -- Maria: Visitante
    INSERT INTO dados_eclesiasticos (pessoa_id, tipo, data_primeira_visita) 
    VALUES (id_maria, 'Visitante', '2024-01-01');


    -- 5. Organização (Agora que já são membros, podem liderar)
    
    -- Departamentos
    INSERT INTO departamento (nome, descricao) VALUES ('Mídia', 'Multimídia') RETURNING id INTO id_depto_midia;
    INSERT INTO departamento (nome, descricao) VALUES ('Louvor', 'Música') RETURNING id INTO id_depto_louvor;

    -- Vínculo Departamento
    INSERT INTO participante_departamento (pessoa_id, departamento_id, funcao) VALUES
    (id_carlos, id_depto_midia, 'Líder'),
    (id_ana, id_depto_louvor, 'Integrante');

    -- Núcleos (Carlos é Líder - Trigger vai checar e APROVAR pois ele já tem dados_eclesiasticos)
    INSERT INTO nucleo (nome, endereco, lider_id) VALUES
    ('Núcleo Central', 'Rua A, 1', id_carlos);


    -- 6. Cursos
    INSERT INTO curso (nome, descricao) VALUES ('Batismo', 'Iniciantes') RETURNING id INTO id_curso_batismo;

    -- Professor (João é Membro - Trigger APROVA)
    INSERT INTO professor_curso (curso_id, pessoa_id) VALUES (id_curso_batismo, id_joao);

    -- Turmas/Aulas
    INSERT INTO aula (curso_id, data_aula, pauta) VALUES
    (id_curso_batismo, '2024-02-01', 'Aula 1: Introdução') RETURNING id INTO id_aula_1;

    -- Matrículas (Maria e Pedro matriculados)
    INSERT INTO matricula_curso (curso_id, pessoa_id, status) VALUES
    (id_curso_batismo, id_maria, 'Ativo'),
    (id_curso_batismo, id_pedro, 'Ativo');

    -- Presença
    INSERT INTO presenca (aula_id, pessoa_id, presente) VALUES
    (id_aula_1, id_maria, TRUE),
    (id_aula_1, id_pedro, FALSE);

    RAISE NOTICE 'Seed finalizado com sucesso!';
END;
$$;
