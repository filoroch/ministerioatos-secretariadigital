<div align="center">

<h1>Ministério Atos - Secretaria Digital</h1>
<p>Sistema web de gestão eclesiástica para controle de pessoas, eventos, departamentos e núcleos da Igreja AD Ministério Atos.

</div>

## 🎯 Visão Geral

Aplicação fullstack desenvolvida para digitalizar e otimizar os processos da secretaria da igreja, incluindo:

- **Gestão de Pessoas**: Cadastro completo de membros, congregados, visitantes e crianças com histórico eclesiástico
- **Controle de Membresia**: Fluxo de aprovação, batismos e transições de estado
- **Documentos**: Emissão de certificados (batismo, apresentação, membros)
- **Cursos e Treinamentos**: Escola bíblica, treinamento de líderes, controle de presença e notas
- **Departamentos e Núcleos**: Organização de equipes e grupos pequenos
- **Eventos**: Gestão de cultos, reuniões e atividades especiais

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
````
Futuramente o projeto pode ser divido em 2 repositórios, um para o backend e outro para o frontend ou em submodulos do git.

## 🔀 Workflow de Contribuição

Este projeto segue **Git Flow simplificado** com proteções automatizadas:

### Estrutura de Branches

- `main` - Produção (protegida, apenas merges de `dev`)
- `dev` - Desenvolvimento e integração
- `feature/*` - Novas funcionalidades
- `fix/*` - Correções de bugs

### Fluxo de Trabalho

```bash
# 1. Sincronize com dev
git checkout dev
git pull origin dev

# 2. Crie sua branch
git checkout -b feature/nome-da-feature
# ou
git checkout -b fix/nome-do-bug

# 3. Desenvolva e commite
git add .
git commit -m "feat: descrição da feature"

# 4. Envie para o repositório
git push origin feature/nome-da-feature

# 5. Abra Pull Request para dev (nunca para main)
```

## 👥 Equipe

- **Project Lead**: Filipe Oliveira Rocha ([@filoroch](https://github.com/filoroch))
- **Organização**: Igreja AD Ministério Atos

## 📝 Licença

Este projeto é propriedade da Igreja AD Ministério Atos e está em desenvolvimento publico para uso interno. O projeto pode ficar privado a qualquer momento
 
---

**Última atualização**: Janeiro 2026
