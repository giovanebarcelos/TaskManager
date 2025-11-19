# Guia de Contribuição

Obrigado por considerar contribuir com o Task Manager! Este documento fornece diretrizes para contribuir com o projeto.

## 🚀 Como Começar

1. **Fork o repositório**
   ```bash
   # Clique no botão "Fork" no GitHub
   ```

2. **Clone seu fork**
   ```bash
   git clone https://github.com/seu-usuario/task-manager.git
   cd task-manager
   ```

3. **Configure o upstream**
   ```bash
   git remote add upstream https://github.com/original-usuario/task-manager.git
   ```

4. **Crie uma branch para sua feature**
   ```bash
   git checkout -b feature/minha-nova-feature
   ```

## 💻 Desenvolvimento

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Git

### Configuração do Ambiente
```bash
# Compile o projeto
mvn clean install

# Execute os testes
mvn test

# Execute a aplicação
mvn spring-boot:run
```

### Padrões de Código

1. **Java Code Style**
   - Siga as convenções Java padrão
   - Use 4 espaços para indentação
   - Máximo 120 caracteres por linha
   - Use nomes descritivos para variáveis e métodos

2. **Commits**
   - Use mensagens de commit claras e descritivas
   - Formato: `tipo: descrição breve`
   - Tipos: `feat`, `fix`, `docs`, `test`, `refactor`, `style`, `chore`
   
   Exemplos:
   ```
   feat: adiciona endpoint para buscar tarefas por usuário
   fix: corrige bug na validação de data
   docs: atualiza documentação da API
   test: adiciona testes para TaskService
   ```

3. **Nomenclatura de Branches**
   - `feature/nome-da-feature` - Para novas funcionalidades
   - `bugfix/descricao-do-bug` - Para correções de bugs
   - `hotfix/descricao-urgente` - Para correções urgentes
   - `docs/descricao` - Para documentação
   - `test/descricao` - Para testes

## ✅ Testes

### Cobertura de Código
- Mantenha cobertura mínima de 80%
- Escreva testes para todas as novas funcionalidades
- Execute `mvn jacoco:report` para verificar cobertura

### Tipos de Testes
1. **Testes Unitários** (`src/test/java`)
   - Use JUnit 5 e Mockito
   - Teste cada método de forma isolada
   - Mock dependências externas

2. **Testes de Integração**
   - Use `@SpringBootTest`
   - Teste endpoints da API
   - Use perfil de teste (`@ActiveProfiles("test")`)

3. **Testes de API**
   - Use REST Assured
   - Teste todos os endpoints
   - Verifique respostas e códigos de status

### Executar Testes
```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=TaskServiceTest

# Com relatório de cobertura
mvn clean test jacoco:report
```

## 📝 Pull Requests

### Antes de Submeter
1. Atualize sua branch com o upstream
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

2. Execute todos os testes
   ```bash
   mvn clean test
   ```

3. Verifique a cobertura de código
   ```bash
   mvn jacoco:check
   ```

4. Execute o build completo
   ```bash
   mvn clean install
   ```

### Criando o Pull Request
1. Push para seu fork
   ```bash
   git push origin feature/minha-nova-feature
   ```

2. Abra um Pull Request no GitHub

3. Preencha o template do PR:
   ```markdown
   ## Descrição
   Breve descrição das mudanças

   ## Tipo de Mudança
   - [ ] Bug fix
   - [ ] Nova feature
   - [ ] Breaking change
   - [ ] Documentação

   ## Como Testar
   Passos para testar as mudanças

   ## Checklist
   - [ ] Código segue os padrões do projeto
   - [ ] Testes foram adicionados/atualizados
   - [ ] Todos os testes passam
   - [ ] Cobertura de código mantida acima de 80%
   - [ ] Documentação foi atualizada
   ```

### Revisão de Código
- Responda aos comentários dos revisores
- Faça as alterações solicitadas
- Mantenha a discussão respeitosa e construtiva

## 🐛 Reportando Bugs

Use o template de issue para reportar bugs:

```markdown
## Descrição do Bug
Descrição clara do problema

## Como Reproduzir
Passos para reproduzir o comportamento:
1. Vá para '...'
2. Clique em '....'
3. Role até '....'
4. Veja o erro

## Comportamento Esperado
O que deveria acontecer

## Comportamento Atual
O que está acontecendo

## Screenshots
Se aplicável, adicione screenshots

## Ambiente
- OS: [e.g., Ubuntu 22.04]
- Java Version: [e.g., 17]
- Browser: [e.g., Chrome 119]

## Informações Adicionais
Qualquer informação adicional sobre o problema
```

## 💡 Sugerindo Melhorias

Use o template de issue para sugerir melhorias:

```markdown
## Descrição da Melhoria
Descrição clara da melhoria sugerida

## Motivação
Por que essa melhoria seria útil?

## Solução Proposta
Como você imagina que isso deveria funcionar?

## Alternativas Consideradas
Quais outras abordagens você considerou?

## Contexto Adicional
Qualquer outra informação relevante
```

## 📚 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/taskmanager/
│   │   ├── controller/      # REST e Web Controllers
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositories
│   │   ├── service/         # Lógica de negócio
│   │   ├── exception/       # Exceções personalizadas
│   │   └── TaskManagerApplication.java
│   └── resources/
│       ├── templates/       # Views Thymeleaf
│       └── application.properties
└── test/
    ├── java/com/taskmanager/
    │   ├── controller/      # Testes de API
    │   ├── service/         # Testes unitários
    │   ├── repository/      # Testes de repositório
    │   └── model/           # Testes de modelo
    └── resources/
        └── application-test.properties
```

## 🔍 Code Review Checklist

Para revisores:

- [ ] Código está limpo e legível
- [ ] Testes adequados foram adicionados
- [ ] Cobertura de código mantida/melhorada
- [ ] Documentação atualizada
- [ ] Sem código comentado desnecessário
- [ ] Segue padrões do projeto
- [ ] Performance considerada
- [ ] Segurança considerada
- [ ] Compatibilidade com versões anteriores

## 📮 Comunicação

- Use Issues para discussões sobre features e bugs
- Seja respeitoso e profissional
- Forneça contexto suficiente nas discussões
- Seja paciente aguardando respostas

## 🎯 Prioridades

1. **Crítico**: Bugs de segurança, perda de dados
2. **Alta**: Bugs que impedem funcionalidades principais
3. **Média**: Melhorias de UX, novas features
4. **Baixa**: Refatorações, otimizações

## 📜 Licença

Ao contribuir, você concorda que suas contribuições serão licenciadas sob a mesma licença do projeto.

## ❓ Dúvidas?

Se tiver dúvidas sobre como contribuir:
1. Verifique a documentação existente
2. Procure em issues fechadas
3. Abra uma nova issue com sua dúvida
4. Entre em contato com os mantenedores

---

**Obrigado por contribuir! 🎉**
