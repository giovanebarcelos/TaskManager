@echo off
echo 🚀 Iniciando Task Manager Application...
echo.

REM Verificar se Java está instalado
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java não está instalado. Por favor, instale Java 17 ou superior.
    exit /b 1
)

echo ✅ Java detectado
echo.

REM Detectar JAVA_HOME automaticamente
for /f "tokens=*" %%i in ('where java') do set JAVA_PATH=%%i
for %%i in ("%JAVA_PATH%") do set JAVA_BIN=%%~dpi
for %%i in ("%JAVA_BIN:~0,-5%") do set JAVA_HOME=%%~fi

echo 📂 JAVA_HOME definido como: %JAVA_HOME%
echo.

REM Limpar e compilar o projeto
echo 📦 Compilando projeto...
call mvnw.cmd clean install -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Falha na compilação do projeto
    exit /b 1
)

echo ✅ Projeto compilado com sucesso
echo.

REM Executar testes
echo 🧪 Executando testes...
call mvnw.cmd test

if %ERRORLEVEL% NEQ 0 (
    echo ⚠️  Alguns testes falharam
) else (
    echo ✅ Todos os testes passaram
)

echo.

REM Gerar relatório de cobertura
echo 📊 Gerando relatório de cobertura...
call mvnw.cmd jacoco:report

echo ✅ Relatório de cobertura gerado em: target\site\jacoco\index.html
echo.

REM Iniciar aplicação
echo 🌟 Iniciando aplicação...
echo 📍 Acesse: http://localhost:8080
echo 📍 API REST: http://localhost:8080/api/tasks
echo.
echo Pressione Ctrl+C para parar a aplicação
echo.

call mvnw.cmd spring-boot:run
