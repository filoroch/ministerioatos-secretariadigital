# Casos de Uso - Secretaria Digital

Baseado no PRD fornecido.

## Glossário

*   **Membros**: Pessoas batizadas e atuantes (Departamentos, Lideranças).
*   **Crianças**: Até 12 anos (Filhos de membros ou visitantes).
*   **Visitantes**: Visitam mas não são atuantes.
*   **Cursos**: Ensino com professor, pauta e certificado.
*   **Departamentos**: Organizações com líderes e tarefas (Obreiros, Intercessoras, Jovens, Mídia, Louvor, Sonoplastia).
*   **Núcleos**: Apoio na fé, semanal, com líder.

## Diagrama de Casos de Uso

```plantuml
@startuml
left to right direction
actor "Secretaria (Admin)" as Secretaria
actor "Público Externo" as Publico

package "Gestão de Pessoas" {
    usecase "Cadastrar Pessoa" as UC_Cadastrar
    usecase "Cadastrar via Form. Interno" as UC_Cad_Int
    usecase "Cadastrar via Form. Externo" as UC_Cad_Ext
    usecase "Listar Pessoas" as UC_Listar
    usecase "Filtrar por Status" as UC_Listar_Status
    usecase "Filtrar por Idade" as UC_Listar_Idade
}

package "Gestão de Documentos" {
    usecase "Gerar Certificado Apresentação" as UC_Cert_Apres
    usecase "Gerar Certificado Batismo" as UC_Cert_Bat
    usecase "Gerar Carteirinha Membro" as UC_Cart
    usecase "Upload de Documentos" as UC_Upload
    usecase "Versionar Documentos" as UC_Vers
}

Secretaria --> UC_Cad_Int
Publico --> UC_Cad_Ext
UC_Cad_Int --|> UC_Cadastrar
UC_Cad_Ext --|> UC_Cadastrar

Secretaria --> UC_Listar
UC_Listar ..> UC_Listar_Status : <<extend>>
UC_Listar ..> UC_Listar_Idade : <<extend>>

Secretaria --> UC_Cert_Apres
note right of UC_Cert_Apres: Somente crianças

Secretaria --> UC_Cert_Bat
note right of UC_Cert_Bat: Somente membros

Secretaria --> UC_Cart

Secretaria --> UC_Upload
Secretaria --> UC_Vers
@enduml
```

## Requisitos Analisados

### Pessoas
1.  **Cadastro**:
    *   Membros, Crianças, Visitantes.
    *   Via Formulário Interno (Secretaria).
    *   Via Formulário Externo (Auto-cadastro/Público).
2.  **Listagem**:
    *   Filtros: Status, Idade.

### Documentos
1.  **Geração**:
    *   Certificado de Apresentação (Crianças).
    *   Certificado de Batismo (Membros).
    *   Carteirinha de Membros.
2.  **Gerenciamento**:
    *   Upload.
    *   Versionamento.
