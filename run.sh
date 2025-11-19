#!/bin/bash

echo "🚀 Iniciando Task Manager Application..."
echo ""

# Verificar se Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não está instalado. Por favor, instale Java 17 ou superior."
    exit 1
fi

# Verificar versão do Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 ou superior é necessário. Versão atual: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java $JAVA_VERSION detectado"
echo ""

# Verificar se Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não está instalado. Por favor, instale Maven 3.8 ou superior."
    exit 1
fi

echo "✅ Maven detectado"
echo ""

# Limpar e compilar o projeto
echo "📦 Compilando projeto..."
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Falha na compilação do projeto"
    exit 1
fi

echo "✅ Projeto compilado com sucesso"
echo ""

# Executar testes
echo "🧪 Executando testes..."
mvn test

if [ $? -ne 0 ]; then
    echo "⚠️  Alguns testes falharam"
else
    echo "✅ Todos os testes passaram"
fi

echo ""

# Gerar relatório de cobertura
echo "📊 Gerando relatório de cobertura..."
mvn jacoco:report

echo "✅ Relatório de cobertura gerado em: target/site/jacoco/index.html"
echo ""

# Iniciar aplicação
echo "🌟 Iniciando aplicação..."
echo "📍 Acesse: http://localhost:8080"
echo "📍 API REST: http://localhost:8080/api/tasks"
echo ""
echo "Pressione Ctrl+C para parar a aplicação"
echo ""

mvn spring-boot:run
