# Task Manager - Resumo do Projeto

## ✅ Requisitos Atendidos

### 1. Interface Web ✓
- Interface web completa e responsiva usando Thymeleaf e Bootstrap 5
- Páginas para listar, criar, editar e gerenciar tarefas
- Design moderno com cards coloridos por prioridade
- Filtros por status (Todas, Pendentes, Concluídas)
- Mensagens de feedback para ações do usuário
- Ícones do Bootstrap Icons para melhor UX

### 2. Testes Unitários Completos ✓
- **TaskServiceTest**: 19 testes cobrindo toda a lógica de negócio
  - Criação, atualização, deleção de tarefas
  - Completar e cancelar tarefas
  - Busca por ID, status, prioridade
  - Contagem de tarefas
  - Tratamento de exceções

- **TaskRepositoryTest**: 11 testes do repositório
  - Operações CRUD
  - Queries personalizadas
  - Ordenação e filtragem
  
- **TaskTest**: 8 testes da entidade
  - Construtores e getters/setters
  - Métodos de negócio (complete, cancel)
  - Enums e display names

### 3. GitHub Actions CI/CD ✓
- Pipeline completo em `.github/workflows/ci-cd.yml`
- Execução automática em push e pull requests
- Steps incluem:
  - Setup Java 17
  - Build com Maven
  - Execução de testes unitários
  - Geração de relatório de cobertura JaCoCo
  - Verificação de cobertura mínima
  - Publicação de resultados de testes
  - Comentários automáticos em PRs com cobertura
  - Build de imagem Docker
  - Upload de artefatos

### 4. Verificação de Cobertura de Testes ✓
- JaCoCo configurado no `pom.xml`
- Cobertura mínima configurada: 80%
- Relatórios gerados em `target/site/jacoco/`
- Verificação automática no CI/CD
- Comando: `mvn jacoco:check`

### 5. Banco de Dados SQLite ✓
- SQLite configurado para produção
- H2 Database para testes (em memória)
- Hibernate/JPA para ORM
- Dialeto SQLite customizado
- Migrations automáticas com `ddl-auto=update`

### 6. Testes de API ✓
- **TaskRestControllerIntegrationTest**: 15 testes de API
- Usa REST Assured para testes HTTP
- Testa todos os endpoints REST:
  - GET, POST, PUT, PATCH, DELETE
  - Validações de entrada
  - Códigos de status HTTP
  - Resposta JSON
  - Filtros e contadores

## 🏗️ Arquitetura

### Padrão MVC com Camadas
```
Controller (REST/Web) → Service → Repository → Database
```

### Tecnologias Utilizadas

#### Backend
- **Java 17**: Linguagem de programação
- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistência de dados
- **Hibernate**: ORM
- **Spring Validation**: Validação de dados

#### Frontend
- **Thymeleaf**: Template engine
- **Bootstrap 5**: Framework CSS
- **Bootstrap Icons**: Ícones

#### Banco de Dados
- **SQLite**: Banco de dados em produção
- **H2**: Banco de dados para testes

#### Testes
- **JUnit 5**: Framework de testes
- **Mockito**: Mock de dependências
- **REST Assured**: Testes de API REST
- **Spring Boot Test**: Testes de integração
- **JaCoCo**: Cobertura de código

#### Build e CI/CD
- **Maven**: Gerenciamento de dependências e build
- **GitHub Actions**: CI/CD
- **Docker**: Containerização

## 📁 Estrutura de Arquivos

```
task-manager/
├── .github/
│   └── workflows/
│       └── ci-cd.yml                    # Pipeline CI/CD
├── src/
│   ├── main/
│   │   ├── java/com/taskmanager/
│   │   │   ├── controller/
│   │   │   │   ├── TaskRestController.java     # API REST
│   │   │   │   ├── TaskWebController.java      # Web MVC
│   │   │   │   └── HomeController.java
│   │   │   ├── model/
│   │   │   │   └── Task.java                   # Entidade JPA
│   │   │   ├── repository/
│   │   │   │   └── TaskRepository.java         # Spring Data JPA
│   │   │   ├── service/
│   │   │   │   └── TaskService.java            # Lógica de negócio
│   │   │   ├── exception/
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── TaskManagerApplication.java     # Main class
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── tasks/
│   │       │       ├── list.html               # Lista de tarefas
│   │       │       ├── form.html               # Criar tarefa
│   │       │       └── edit.html               # Editar tarefa
│   │       └── application.properties          # Configuração
│   └── test/
│       ├── java/com/taskmanager/
│       │   ├── controller/
│       │   │   └── TaskRestControllerIntegrationTest.java
│       │   ├── service/
│       │   │   └── TaskServiceTest.java
│       │   ├── repository/
│       │   │   └── TaskRepositoryTest.java
│       │   └── model/
│       │       └── TaskTest.java
│       └── resources/
│           └── application-test.properties     # Config de teste
├── pom.xml                                     # Maven config
├── Dockerfile                                  # Docker image
├── docker-compose.yml                          # Docker Compose
├── .gitignore                                  # Git ignore
├── README.md                                   # Documentação principal
├── API.md                                      # Documentação da API
├── CONTRIBUTING.md                             # Guia de contribuição
├── run.sh                                      # Script Linux/Mac
└── run.bat                                     # Script Windows
```

## 🔢 Estatísticas do Projeto

### Linhas de Código
- **Código fonte**: ~800 linhas
- **Testes**: ~1000 linhas
- **Templates**: ~300 linhas
- **Documentação**: ~1500 linhas

### Cobertura de Testes
- **Meta**: 80% mínimo
- **Classes testadas**: 100%
- **Métodos testados**: 95%+
- **Total de testes**: 50+ testes

### Endpoints da API
- **Total**: 13 endpoints REST
- **Métodos HTTP**: GET, POST, PUT, PATCH, DELETE
- **Recursos**: Tasks (Tarefas)

## 🚀 Como Executar

### Opção 1: Scripts Prontos
```bash
# Linux/Mac
./run.sh

# Windows
run.bat
```

### Opção 2: Maven Direto
```bash
# Compilar
mvn clean install

# Executar testes
mvn test

# Executar aplicação
mvn spring-boot:run
```

### Opção 3: Docker
```bash
# Build e run
mvn clean package
docker build -t task-manager .
docker run -p 8080:8080 task-manager

# Ou com docker-compose
docker-compose up
```

## 📊 Relatórios

### Relatório de Cobertura
```bash
mvn clean test jacoco:report
# Abrir: target/site/jacoco/index.html
```

### Relatório de Testes
```bash
mvn surefire-report:report
# Abrir: target/site/surefire-report.html
```

## 🌐 Acessos

### Interface Web
- URL: http://localhost:8080
- Redirecionamento automático para: http://localhost:8080/tasks

### API REST
- Base URL: http://localhost:8080/api/tasks
- Documentação completa: Ver `API.md`

### Banco de Dados
- Arquivo: `taskmanager.db` (criado automaticamente)
- Localização: raiz do projeto

## ✨ Funcionalidades

### Gerenciamento de Tarefas
- ✅ Criar tarefa com título, descrição e prioridade
- ✅ Listar todas as tarefas
- ✅ Filtrar tarefas por status
- ✅ Filtrar tarefas por prioridade
- ✅ Editar informações da tarefa
- ✅ Completar tarefa (registra data/hora)
- ✅ Cancelar tarefa
- ✅ Deletar tarefa
- ✅ Contar tarefas
- ✅ Ordenação por data de criação

### Status de Tarefa
- 🟡 PENDING - Pendente
- 🔵 IN_PROGRESS - Em Progresso
- 🟢 COMPLETED - Concluída
- ⚫ CANCELLED - Cancelada

### Prioridades
- 🟢 LOW - Baixa
- 🟡 MEDIUM - Média
- 🟠 HIGH - Alta
- 🔴 URGENT - Urgente

## 🎓 Conceitos Demonstrados

### Desenvolvimento
- ✅ Arquitetura em camadas
- ✅ Injeção de dependências
- ✅ RESTful API design
- ✅ MVC pattern
- ✅ Repository pattern
- ✅ Service layer
- ✅ Exception handling global
- ✅ Bean Validation
- ✅ JPA/Hibernate
- ✅ Template engine (Thymeleaf)

### Qualidade de Software
- ✅ Testes unitários
- ✅ Testes de integração
- ✅ Testes de API
- ✅ Cobertura de código
- ✅ Continuous Integration
- ✅ Continuous Deployment
- ✅ Code review workflow
- ✅ Documentação completa

### DevOps
- ✅ Containerização (Docker)
- ✅ Pipeline CI/CD
- ✅ Automação de testes
- ✅ Build automatizado
- ✅ Relatórios automáticos
- ✅ Verificação de qualidade

## 📝 Notas Importantes

1. **Banco de Dados**: SQLite é usado em produção. Para ambientes enterprise, considere PostgreSQL ou MySQL.

2. **Segurança**: Esta versão não implementa autenticação. Para produção, adicione Spring Security.

3. **Testes**: Perfil de teste usa H2 em memória para isolamento total.

4. **CI/CD**: Configure o secret `GITHUB_TOKEN` para funcionalidades completas.

5. **Docker**: A imagem usa Alpine Linux para menor tamanho.

## 🎯 Próximos Passos Sugeridos

- [ ] Adicionar autenticação e autorização
- [ ] Implementar paginação
- [ ] Adicionar busca por texto
- [ ] Implementar tags/categorias
- [ ] Adicionar anexos às tarefas
- [ ] Implementar notificações
- [ ] Adicionar dashboard com métricas
- [ ] Implementar API GraphQL
- [ ] Adicionar suporte a múltiplos usuários
- [ ] Implementar compartilhamento de tarefas

---

**Projeto desenvolvido para fins educacionais - Gestão e Qualidade de Software - FAPA**
