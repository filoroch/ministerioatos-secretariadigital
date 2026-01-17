#!/bin/bash

# ═══════════════════════════════════════════════════════════════
# 🔧 INSTALADOR DE GIT HOOKS
# ═══════════════════════════════════════════════════════════════

HOOKS_DIR="git-hooks"
GIT_HOOKS_DIR=".git/hooks"

echo ""
echo "╔═════════════════════════════════════════════════════╗"
echo "║  🔧 INSTALANDO GIT HOOKS                            ║"
echo "╚═════════════════════════════════════════════════════╝"
echo ""

# Verifica se pasta existe
if [ ! -d "$HOOKS_DIR" ]; then
    echo "❌ Erro: Pasta $HOOKS_DIR não encontrada!"
    echo "💡 Execute este script da raiz do projeto"
    exit 1
fi

# Verifica se é repositório git
if [ ! -d ".git" ]; then
    echo "❌ Erro: Não é um repositório Git!"
    exit 1
fi

# Copia hooks
installed_count=0

for hook_file in "$HOOKS_DIR"/*; do
    hook_name=$(basename "$hook_file")
    
    # Ignora arquivos auxiliares
    if [[ "$hook_name" == "README.md" ]] || \
       [[ "$hook_name" == "install-hooks.sh" ]] || \
       [[ "$hook_name" == ".gitkeep" ]]; then
        continue
    fi
    
    echo "📝 Instalando: $hook_name"
    cp "$hook_file" "$GIT_HOOKS_DIR/$hook_name"
    chmod +x "$GIT_HOOKS_DIR/$hook_name"
    ((installed_count++))
done

echo ""
if [ $installed_count -eq 0 ]; then
    echo "⚠️  Nenhum hook encontrado para instalar"
else
    echo "✅ $installed_count hook(s) instalado(s) com sucesso!"
    echo ""
    echo "🛡️  Proteções ativadas:"
    echo "   • Bloqueia push direto para 'main'"
    echo "   • Push para 'dev' só de branches feature/fix/hotfix"
    echo "   • Valida se branch está atualizada com dev"
fi

echo ""
echo "╔═════════════════════════════════════════════════════╗"
echo "║  ✅ INSTALAÇÃO CONCLUÍDA                            ║"
echo "╚═════════════════════════════════════════════════════╝"
echo ""
