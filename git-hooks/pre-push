#!/bin/bash

# ═══════════════════════════════════════════════════════════════
# 🛡️  GIT HOOK: PRE-PUSH
# ═══════════════════════════════════════════════════════════════
# Regras:
# 1. NUNCA push direto para 'main'
# 2. Push para 'dev' só de branches feature/fix/hotfix/*
# 3. Branch deve estar atualizada com dev antes de push
# ═══════════════════════════════════════════════════════════════

# Pega a branch atual
current_branch=$(git symbolic-ref --short HEAD 2>/dev/null)

# Se não conseguir pegar a branch, permite (caso detached HEAD)
if [ -z "$current_branch" ]; then
    exit 0
fi

# Lê o que está sendo enviado (remote e branch de destino)
while read local_ref local_sha remote_ref remote_sha; do
    remote_branch=$(echo "$remote_ref" | sed 's/refs\/heads\///')
    
    # ═══════════════════════════════════════════════════════════
    # 🚫 REGRA 1: NUNCA push para 'main'
    # ═══════════════════════════════════════════════════════════
    if [ "$remote_branch" = "main" ]; then
        echo ""
        echo "╔═════════════════════════════════════════════════════╗"
        echo "║  🚫 PUSH BLOQUEADO PARA 'main'                     ║"
        echo "╚═════════════════════════════════════════════════════╝"
        echo ""
        echo "❌ Push direto para 'main' NÃO é permitido!"
        echo ""
        echo "✅ Fluxo correto:"
        echo "   1. Suas mudanças devem estar em 'dev'"
        echo "   2. Abra um Pull Request: dev → main"
        echo ""
        echo "🔗 Abrir PR:"
        echo "   https://github.com/filoroch/ministerioatos-secretariadigital/compare/main...dev"
        echo ""
        exit 1
    fi
    
    # ═══════════════════════════════════════════════════════════
    # 🚫 REGRA 2: Push para 'dev' só de branches corretas
    # ═══════════════════════════════════════════════════════════
    if [ "$remote_branch" = "dev" ]; then
        # Verifica se a branch atual é feature/fix/hotfix/chore/docs/refactor/test
        if [[ ! "$current_branch" =~ ^(feature|fix|hotfix|chore|docs|refactor|test|infra)/ ]]; then
            echo ""
            echo "╔═════════════════════════════════════════════════════╗"
            echo "║  🚫 PUSH BLOQUEADO PARA 'dev'                      ║"
            echo "╚═════════════════════════════════════════════════════╝"
            echo ""
            echo "❌ Push para 'dev' só é permitido de branches:"
            echo "   • feature/*"
            echo "   • fix/*"
            echo "   • hotfix/*"
            echo "   • chore/*"
            echo "   • docs/*"
            echo "   • refactor/*"
            echo "   • test/*"
            echo ""
            echo "📛 Você está em: $current_branch"
            echo ""
            echo "✅ Fluxo correto:"
            echo "   1. Crie uma branch de trabalho:"
            echo "      git checkout -b feature/nome-da-feature"
            echo ""
            echo "   2. Faça suas alterações e commit"
            echo ""
            echo "   3. Envie para o GitHub:"
            echo "      git push origin feature/nome-da-feature"
            echo ""
            echo "   4. Abra Pull Request: feature/... → dev"
            echo ""
            exit 1
        fi
        
        # ═══════════════════════════════════════════════════════
        # 🚫 REGRA 3: Branch deve estar atualizada com dev
        # ═══════════════════════════════════════════════════════
        echo "🔍 Verificando se branch está atualizada com dev..."
        
        # Busca atualizações de dev sem fazer merge
        git fetch origin dev --quiet
        
        # Pega o commit mais recente de dev remoto
        REMOTE_DEV=$(git rev-parse origin/dev)
        
        # Verifica se a branch atual contém todos os commits de dev
        # (se dev estiver no histórico da branch atual)
        MERGE_BASE=$(git merge-base HEAD origin/dev)
        
        if [ "$MERGE_BASE" != "$REMOTE_DEV" ]; then
            # Calcula quantos commits dev tem que a branch não tem
            COMMITS_BEHIND=$(git rev-list --count HEAD..origin/dev)
            
            echo ""
            echo "╔═════════════════════════════════════════════════════╗"
            echo "║  ⚠️  BRANCH DESATUALIZADA                           ║"
            echo "╚═════════════════════════════════════════════════════╝"
            echo ""
            echo "❌ Sua branch está $COMMITS_BEHIND commit(s) atrás de 'dev'"
            echo ""
            echo "⚠️  Se você fizer push agora, pode:"
            echo "   • Sobrescrever trabalho de outras pessoas"
            echo "   • Causar conflitos difíceis de resolver"
            echo "   • Perder alterações importantes"
            echo ""
            echo "✅ Atualize sua branch primeiro:"
            echo ""
            echo "   OPÇÃO 1 - Rebase (recomendado - histórico limpo):"
            echo "   ──────────────────────────────────────────────────"
            echo "   git fetch origin dev"
            echo "   git rebase origin/dev"
            echo "   # Resolva conflitos se houver"
            echo "   git push origin $current_branch"
            echo ""
            echo "   OPÇÃO 2 - Merge (mais simples):"
            echo "   ──────────────────────────────────────────────────"
            echo "   git fetch origin dev"
            echo "   git merge origin/dev"
            echo "   # Resolva conflitos se houver"
            echo "   git push origin $current_branch"
            echo ""
            echo "💡 Dica: Use rebase para manter histórico linear"
            echo ""
            exit 1
        fi
        
        # Se chegou aqui, está atualizado e numa branch válida
        echo "✅ Branch atualizada com dev"
        echo "✅ Push permitido: $current_branch → dev"
    fi
done

exit 0
