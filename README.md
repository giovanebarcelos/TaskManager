# Gerenciador de Tarefas - Task Manager

![CI/CD Pipeline](https://github.com/giovanebarcelos/Gerenciamento-de-Tarefas/workflows/CI/CD%20Pipeline/badge.svg)
![Coverage](https://img.shields.io/badge/coverage-80%25-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![Deploy](https://img.shields.io/badge/deploy-Render.com-brightgreen)

Sistema completo de gerenciamento de tarefas desenvolvido em Java com Spring Boot, incluindo interface web, API REST, testes completos e **deploy automático no Render.com**.

## 🌐 **Demo Online**

**Acesse a aplicação funcionando:** (após configurar o Render)
```
https://task-manager-XXXXX.onrender.com
```

## 🚀 Características

- ✅ Interface Web responsiva com Bootstrap
- ✅ API REST completa
- ✅ Banco de dados SQLite
- ✅ Testes unitários completos (56 testes)
- ✅ Testes de API com REST Assured
- ✅ Cobertura de código com JaCoCo (mínimo 80%)
- ✅ CI/CD com GitHub Actions
- ✅ **Deploy automático no Render.com** 🆕
- ✅ Validação de dados
- ✅ Tratamento de exceções global
- ✅ Docker support

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- Docker (opcional)
- Conta no Render.com (para deploy online - gratuito)

## 🔧 Instalação Local

### Clone o repositório
```bash
git clone https://github.com/giovanebarcelos/Gerenciamento-de-Tarefas.git
cd Gerenciamento-de-Tarefas
```

### Compile o projeto
```bash
mvn clean install
```

### Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 🌐 Deploy Online (Render.com)

**Veja o guia completo:** [RENDER-DEPLOY.md](RENDER-DEPLOY.md)

### Passos Rápidos:

1. Crie conta em https://render.com (grátis)
2. Conecte seu repositório GitHub
3. Clique em "New Web Service"
4. Selecione este repositório
5. Configure:
   - Runtime: **Docker**
   - Branch: **main**
   - Plan: **Free**
6. Deploy! 🚀

**Pronto!** Sua aplicação estará online em: `https://task-manager-XXXXX.onrender.com`

## 🧪 Testes

### Executar todos os testes
```bash
mvn test
```

### Executar testes e gerar relatório de cobertura
```bash
mvn clean test jacoco:report
```

O relatório estará disponível em: `target/site/jacoco/index.html`

### Verificar cobertura mínima (80%)
```bash
mvn jacoco:check
```

## 🐳 Docker

### Build da imagem
```bash
mvn clean package
docker build -t task-manager:latest .
```

### Executar container
```bash
docker run -p 8080:8080 task-manager:latest
```

## 📚 API REST

### Endpoints disponíveis

#### Listar todas as tarefas
```
GET /api/tasks
```

#### Buscar tarefa por ID
```
GET /api/tasks/{id}
```

#### Criar nova tarefa
```
POST /api/tasks
Content-Type: application/json

{
  "title": "Minha Tarefa",
  "description": "Descrição da tarefa",
  "priority": "HIGH"
}
```

#### Atualizar tarefa
```
PUT /api/tasks/{id}
Content-Type: application/json

{
  "title": "Título Atualizado",
  "description": "Descrição atualizada",
  "status": "IN_PROGRESS",
  "priority": "MEDIUM"
}
```

#### Completar tarefa
```
PATCH /api/tasks/{id}/complete
```

#### Cancelar tarefa
```
PATCH /api/tasks/{id}/cancel
```

#### Deletar tarefa
```
DELETE /api/tasks/{id}
```

#### Listar tarefas por status
```
GET /api/tasks/status/{status}
```
Status: `PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

#### Listar tarefas por prioridade
```
GET /api/tasks/priority/{priority}
```
Prioridade: `LOW`, `MEDIUM`, `HIGH`, `URGENT`

#### Listar tarefas pendentes
```
GET /api/tasks/pending
```

#### Listar tarefas concluídas
```
GET /api/tasks/completed
```

#### Contar tarefas
```
GET /api/tasks/count
```

#### Contar tarefas por status
```
GET /api/tasks/count/status/{status}
```

## 🌐 Interface Web

Acesse a interface web em: `http://localhost:8080/tasks`

### Funcionalidades da interface:
- Visualizar todas as tarefas
- Filtrar por status (Todas, Pendentes, Concluídas)
- Criar nova tarefa
- Editar tarefa existente
- Completar tarefa
- Cancelar tarefa
- Deletar tarefa
- Cards coloridos por prioridade
- Design responsivo

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/taskmanager/
│   │       ├── controller/         # Controllers REST e Web
│   │       ├── model/              # Entidades JPA
│   │       ├── repository/         # Repositories Spring Data
│   │       ├── service/            # Serviços de negócio
│   │       ├── exception/          # Exceções e handlers
│   │       └── TaskManagerApplication.java
│   └── resources/
│       ├── templates/              # Templates Thymeleaf
│       └── application.properties  # Configurações
└── test/
    ├── java/
    │   └── com/taskmanager/
    │       ├── controller/         # Testes de API
    │       └── service/            # Testes unitários
    └── resources/
        └── application-test.properties
```

## 🔄 CI/CD

O projeto utiliza GitHub Actions para:
- ✅ Build automatizado
- ✅ Execução de testes
- ✅ Verificação de cobertura de código
- ✅ Análise de código
- ✅ Build de imagem Docker
- ✅ **Deploy automático para GitHub Container Registry**
- ✅ Relatórios de teste

O pipeline é executado automaticamente em:
- Push para `main` ou `develop`
- Pull Requests para `main` ou `develop`

### 🚀 Deploy Automático

Quando você faz push para a branch `main`, o workflow automaticamente:
1. Executa todos os testes
2. Verifica a cobertura de código
3. Constrói a imagem Docker
4. Publica no GitHub Container Registry

Para usar a imagem publicada:
```bash
docker pull ghcr.io/SEU_USUARIO/task-manager:latest
docker run -p 8080:8080 ghcr.io/SEU_USUARIO/task-manager:latest
```

**📖 Veja mais opções de deploy no arquivo [DEPLOY.md](DEPLOY.md)**

## 📊 Cobertura de Testes

O projeto mantém uma cobertura mínima de 80% de código testado, garantida por:
- Testes unitários com JUnit 5 e Mockito
- Testes de integração da API com REST Assured
- Validação automática no pipeline CI/CD

## 🛠️ Tecnologias Utilizadas

- **Backend**: Java 17, Spring Boot 3.2.0
- **Frontend**: Thymeleaf, Bootstrap 5, Bootstrap Icons
- **Banco de Dados**: SQLite (produção), H2 (testes)
- **Testes**: JUnit 5, Mockito, REST Assured
- **Cobertura**: JaCoCo
- **Build**: Maven
- **CI/CD**: GitHub Actions
- **Containerização**: Docker

## 📝 Licença

Este projeto é livre para uso educacional e comercial.

## 👥 Autor

Desenvolvido para a disciplina de Gestão e Qualidade de Software - FAPA

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request
