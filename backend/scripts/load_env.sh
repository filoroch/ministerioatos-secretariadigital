#!/bin/bash

# Script para carregar variáveis de ambiente e rodar a aplicação
# Funciona em Linux e macOS

echo "🔧 Carregando variáveis de ambiente..."

# Verificar se arquivo .env existe
if [ ! -f .env ]; then
    echo "❌ Arquivo .env não encontrado!"
    echo "📝 Copie o arquivo .env.example para .env e preencha os valores:"
    echo "   cp .env.example .env"
    exit 1
fi

# Carregar variáveis do .env (ignora linhas vazias e comentários)
export $(cat .env | grep -v '^#' | grep -v '^$' | xargs)

echo "✅ Variáveis carregadas!"
echo ""
echo "🚀 Iniciando aplicação..."
echo ""

# Rodar aplicação Spring Boot
echo "Variaveis carregadas"
