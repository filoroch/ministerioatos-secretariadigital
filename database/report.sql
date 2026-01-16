-- Relatório Ficha 360 (Compatível com Schema UUID)
-- Exibe dados pessoais, eclesiásticos, contato, endereço e envolvimento ministerial/ensino.

SELECT 
    p.id AS pessoa_id,
    p.nome,
    p.cpf,
    p.genero,
    TO_CHAR(p.data_nascimento, 'DD/MM/YYYY') AS data_nascimento,
    
    -- Status Eclesiástico (Busca o vínculo ATIVO)
    COALESCE(de.tipo, 'Sem Vínculo') AS tipo_eclesiastico,
    COALESCE(de.situacao, '-') AS situacao,
    TO_CHAR(de.data_inicio_vinculo, 'DD/MM/YYYY') AS desde,
    
    -- Endereço Atual
    COALESCE(
        e.logradouro || ', ' || e.numero || ' - ' || e.bairro || ' (' || e.cidade || '/' || e.estado || ')', 
        'Endereço não cadastrado'
    ) AS endereco,
    
    -- Contatos (Agrupados numa string)
    COALESCE(
        (SELECT STRING_AGG(c.tipo || ': ' || c.valor, ' | ' ORDER BY c.principal DESC) 
         FROM contato c 
         WHERE c.pessoa_id = p.id),
        'Sem contatos'
    ) AS contatos,
    
    -- Redes Sociais
    COALESCE(
        (SELECT STRING_AGG(rs.plataforma || ': ' || rs.url_perfil, ' | ') 
         FROM rede_social rs 
         WHERE rs.pessoa_id = p.id),
        '-'
    ) AS redes_sociais,
    
    -- Família (Quem essa pessoa "tem" como parente)
    COALESCE(
        (SELECT STRING_AGG(rel.tipo_relacionamento || ': ' || p2.nome, '; ')
         FROM relacionamento rel
         JOIN pessoa p2 ON rel.pessoa_destino_id = p2.id
         WHERE rel.pessoa_origem_id = p.id),
        '-'
    ) AS familia,

    -- Departamentos (Participação)
    COALESCE(
        (SELECT STRING_AGG(d.nome || ' (' || pd.funcao || ')', '; ')
         FROM participante_departamento pd
         JOIN departamento d ON pd.departamento_id = d.id
         WHERE pd.pessoa_id = p.id),
        '-'
    ) AS departamentos,
    
    -- Núcleos (Liderança ou Frequência)
    -- Une liderança (via tabela nucleo) e frequência (via tabela frequencia_nucleo)
    COALESCE(
        (
            SELECT STRING_AGG(texto, '; ') FROM (
                -- Caso seja líder
                SELECT 'Líder do ' || n.nome AS texto
                FROM nucleo n WHERE n.lider_id = p.id
                UNION ALL
                -- Caso seja frequentador
                SELECT 'Participa do ' || n2.nome || ' (' || fn.papel || ')' 
                FROM frequencia_nucleo fn
                JOIN nucleo n2 ON fn.nucleo_id = n2.id
                WHERE fn.pessoa_id = p.id
            ) t
        ),
        '-'
    ) AS nucleos,
    
    -- Cursos (Matrículas Ativas ou Concluídas)
    COALESCE(
        (SELECT STRING_AGG(cur.nome || ' [' || mc.status || ']', '; ')
         FROM matricula_curso mc
         JOIN curso cur ON mc.curso_id = cur.id
         WHERE mc.pessoa_id = p.id),
        '-'
    ) AS cursos

FROM pessoa p
-- Join para trazer dados eclesiásticos ATIVOS (data_fim_vinculo IS NULL)
LEFT JOIN dados_eclesiasticos de ON p.id = de.pessoa_id AND de.data_fim_vinculo IS NULL
-- Join para trazer endereço ATUAL
LEFT JOIN endereco e ON p.id = e.pessoa_id AND e.endereco_atual = TRUE

ORDER BY p.nome;
