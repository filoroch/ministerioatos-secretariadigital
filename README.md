# Ministério Atos - Secretaria Digital

> Sistema web de gestão eclesiástica para controle de pessoas, eventos, departamentos e núcleos da Igreja AD Ministério Atos.

## 🎯 Visão Geral

Aplicação fullstack desenvolvida para digitalizar e otimizar os processos da secretaria da igreja, incluindo:

- **Gestão de Pessoas**: Cadastro completo de membros, congregados, visitantes e crianças com histórico eclesiástico
- **Controle de Membresia**: Fluxo de aprovação, batismos e transições de estado
- **Documentos**: Emissão de certificados (batismo, apresentação, membros)
- **Cursos e Treinamentos**: Escola bíblica, treinamento de líderes, controle de presença e notas
- **Departamentos e Núcleos**: Organização de equipes e grupos pequenos
- **Eventos**: Gestão de cultos, reuniões e atividades especiais

## 📊 Status do Projeto

**Fase Atual**: Planejamento e Definição de Arquitetura

- ✅ Regras de negócio documentadas
- ✅ Schema do banco de dados definido
- 🔄 Docker/Infraestrutura em preparação
- ⏳ Autenticação e autorização pendente
- ⏳ Implementação do backend pendente

## 🛠️ Stack Técnica

### Backend
- **Java 17+** com **Spring Boot 3.x**
- **Spring Security** + **Supabase Auth** (JWT)
- **Spring Data JPA** com **PostgreSQL**
- **Docker** para containerização
- **TDD** com JUnit 5 e Mockito

### Frontend
- **Angular 18+**
- **TypeScript**
- **Supabase Client** para autenticação
- **RxJS** para gerenciamento de estado
- **Angular Material** (planejado)

### Infraestrutura
- **Supabase**: Auth, Database (PostgreSQL), Storage
- **Koyeb**: Deploy do backend
- **Docker Compose**: Ambiente de desenvolvimento
- **GitHub Actions**: CI/CD

## 📚 Documentação Técnica

### Modelo de Dados

O sistema utiliza um modelo relacional normalizado com as seguintes entidades principais:

#### Pessoa
Entidade central com dados biográficos imutáveis (nome, CPF, gênero, data de nascimento).

#### Dados Eclesiásticos
Histórico de vínculos com a igreja por tipo:
- **Criança**: Até 12 anos, certificado de apresentação
- **Visitante**: Freqüenta esporadicamente, sem vínculos formais
- **Congregado**: Convertido, pode estar aguardando batismo ou membresia
- **Membro**: Batizado e aprovado oficialmente pela liderança

#### Outras Entidades
- **Endereço**: Normalizado e compartilhado (famílias, eventos, núcleos)
- **Relacionamento**: Estrutura familiar e parentesco
- **Departamento**: Equipes ministeriais (mídia, música, etc.)
- **Núcleo**: Grupos pequenos/células
- **Curso/Aula/Avaliação**: Sistema de treinamentos
- **Documento**: Metadados de certificados com storage no Supabase

### Regras de Negócio

#### Transições de Estado
- **Criança → Visitante/Congregado**: Aos 13 anos (manual ou sugestão automática)
- **Visitante → Congregado**: Ao registrar conversão
- **Congregado → Membro**: Após batismo + apresentação oficial + aprovação pastoral

#### Controle de Membresia
**Batismo ≠ Membresia Automática**

Mesmo batizado, a pessoa precisa:
1. Ser apresentada oficialmente à congregação
2. Ser declarada apta pela liderança
3. Ter aprovação manual registrada no sistema

Campos de controle: `situacao`, `apto_membresia`, `data_apresentacao_oficial`, `data_aprovacao_membresia`

#### Relacionamentos
Pessoas não registradas mencionadas em relacionamentos são automaticamente criadas como **Visitante** para manter integridade referencial.

#### Endereços
O sistema reutiliza endereços baseado em CEP + número + complemento, permitindo que famílias compartilhem o mesmo registro.

### Sistema de Roles

| Role | Descrição | Permissões |
|------|-------------|-------------|
| `ADMIN` | Administrador total | CRUD completo em todas as entidades |
| `SECRETARIA` | Secretário(a) | CRUD de pessoas, documentos, cursos |
| `LIDERANCA` | Pastor/Líder | Leitura de dados, aprovação de membresia |
| `MEMBRO` | Membro comum | Leitura de dados próprios apenas |

Roles armazenadas em `auth.users.raw_user_meta_data` no Supabase.

## 🚀 Roadmap

### Fase 1: Infraestrutura (🔄 Em Andamento)
- [ ] Dockerfile e docker-compose para desenvolvimento local ([ATOS-24](https://github.com/filoroch/ministerioatos-secretariadigital/issues/3))
- [ ] Autenticação com Supabase Auth + Spring Security ([ATOS-25](https://github.com/filoroch/ministerioatos-secretariadigital/issues/4))
- [ ] Configuração de RLS (Row Level Security) no Supabase

### Fase 2: Módulo de Pessoas
- [ ] Implementação do domain model com TDD ([ATOS-26](https://github.com/filoroch/ministerioatos-secretariadigital/issues/5))
- [ ] API REST para CRUD de pessoas
- [ ] Validações de regras de negócio
- [ ] Processamento de endereços e relacionamentos
- [ ] Telas de cadastro no frontend

### Fase 3: Gestão de Membresia
- [ ] Fluxo de aprovação de membros
- [ ] Dashboard de candidatos à membresia
- [ ] Controle de batismos
- [ ] Histórico de transições de estado

### Fase 4: Documentos
- [ ] Integração com Supabase Storage
- [ ] Templates de certificados
- [ ] Emissão de PDF
- [ ] Versionamento de documentos

### Fase 5: Cursos e Eventos
- [ ] Módulo de cursos e escola bíblica
- [ ] Controle de presença e notas
- [ ] Gestão de eventos
- [ ] Calendário de atividades

### Fase 6: Relatórios e Analytics
- [ ] Dashboard administrativo
- [ ] Relatórios estatísticos
- [ ] Exportação de dados
- [ ] Gráficos de crescimento

## 💻 Desenvolvimento Local

### Pré-requisitos
- Docker e Docker Compose
- Java 17+
- Node.js 18+ e npm
- Git

### Setup

```bash
# Clonar repositório
git clone https://github.com/filoroch/ministerioatos-secretariadigital.git secretaria-digital
cd secretaria-digital

# Configurar variáveis de ambiente
cp .env.example .env
# Editar .env com suas credenciais do Supabase

# Subir ambiente completo (backend + frontend + database)
docker-compose up -d

# Backend estará em http://localhost:8080
# Frontend estará em http://localhost:4200
```

### Estrutura do Projeto

```
ministerioatos-secretariadigital/
├── backend/              # Spring Boot API
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/             # Angular SPA
│   ├── src/
│   ├── package.json
│   └── Dockerfile
├── docker-compose.yml
├── .env.example
└── README.md
```

## 🔗 Links Úteis

- **GitHub Issues**: [github.com/filoroch/ministerioatos-secretariadigital/issues](https://github.com/filoroch/ministerioatos-secretariadigital/issues)
- **Linear Project**: [Ministério Atos: Secretaria Digital](https://linear.app/waverider/project/ministerio-atos-secretaria-digital-867a759e111b)
- **Documentação do Supabase**: [supabase.com/docs](https://supabase.com/docs)
- **Spring Boot Docs**: [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- **Angular Docs**: [angular.io/docs](https://angular.io/docs)

## 👥 Equipe

- **Project Lead**: Filipe Oliveira Rocha ([@filoroch](https://github.com/filoroch))
- **Organização**: Igreja AD Ministério Atos

## 📝 Licença

Este projeto é propriedade da Igreja AD Ministério Atos e está em desenvolvimento para uso interno.

---

**Última atualização**: Janeiro 2026
