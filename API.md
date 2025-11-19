# API REST - Documentação Completa

## Base URL
```
http://localhost:8080/api/tasks
```

## Endpoints

### 1. Listar Todas as Tarefas

**GET** `/api/tasks`

Retorna todas as tarefas ordenadas por data de criação (mais recentes primeiro).

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "title": "Minha Primeira Tarefa",
    "description": "Descrição detalhada",
    "status": "PENDING",
    "priority": "HIGH",
    "createdAt": "2025-11-13T10:30:00",
    "completedAt": null
  }
]
```

---

### 2. Buscar Tarefa por ID

**GET** `/api/tasks/{id}`

Retorna uma tarefa específica pelo ID.

**Parameters:**
- `id` (path) - ID da tarefa

**Response 200 OK:**
```json
{
  "id": 1,
  "title": "Minha Primeira Tarefa",
  "description": "Descrição detalhada",
  "status": "PENDING",
  "priority": "HIGH",
  "createdAt": "2025-11-13T10:30:00",
  "completedAt": null
}
```

**Response 404 Not Found:**
```json
{
  "status": 404,
  "message": "Tarefa não encontrada com ID: 999",
  "timestamp": "2025-11-13T10:30:00"
}
```

---

### 3. Criar Nova Tarefa

**POST** `/api/tasks`

Cria uma nova tarefa.

**Request Body:**
```json
{
  "title": "Nova Tarefa",
  "description": "Descrição opcional",
  "priority": "HIGH"
}
```

**Validações:**
- `title`: obrigatório, entre 3 e 100 caracteres
- `description`: opcional, máximo 500 caracteres
- `priority`: opcional, valores: `LOW`, `MEDIUM`, `HIGH`, `URGENT` (padrão: `MEDIUM`)

**Response 201 Created:**
```json
{
  "id": 2,
  "title": "Nova Tarefa",
  "description": "Descrição opcional",
  "status": "PENDING",
  "priority": "HIGH",
  "createdAt": "2025-11-13T10:35:00",
  "completedAt": null
}
```

**Response 400 Bad Request:**
```json
{
  "status": 400,
  "errors": {
    "title": "O título deve ter entre 3 e 100 caracteres"
  },
  "timestamp": "2025-11-13T10:35:00"
}
```

---

### 4. Atualizar Tarefa

**PUT** `/api/tasks/{id}`

Atualiza uma tarefa existente.

**Parameters:**
- `id` (path) - ID da tarefa

**Request Body:**
```json
{
  "title": "Título Atualizado",
  "description": "Nova descrição",
  "status": "IN_PROGRESS",
  "priority": "URGENT"
}
```

**Status disponíveis:**
- `PENDING` - Pendente
- `IN_PROGRESS` - Em Progresso
- `COMPLETED` - Concluída
- `CANCELLED` - Cancelada

**Response 200 OK:**
```json
{
  "id": 2,
  "title": "Título Atualizado",
  "description": "Nova descrição",
  "status": "IN_PROGRESS",
  "priority": "URGENT",
  "createdAt": "2025-11-13T10:35:00",
  "completedAt": null
}
```

**Response 404 Not Found:**
```json
{
  "status": 404,
  "message": "Tarefa não encontrada com ID: 999",
  "timestamp": "2025-11-13T10:40:00"
}
```

---

### 5. Completar Tarefa

**PATCH** `/api/tasks/{id}/complete`

Marca uma tarefa como concluída e registra a data/hora de conclusão.

**Parameters:**
- `id` (path) - ID da tarefa

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "status": 404,
  "message": "Tarefa não encontrada com ID: 999",
  "timestamp": "2025-11-13T10:45:00"
}
```

---

### 6. Cancelar Tarefa

**PATCH** `/api/tasks/{id}/cancel`

Marca uma tarefa como cancelada.

**Parameters:**
- `id` (path) - ID da tarefa

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "status": 404,
  "message": "Tarefa não encontrada com ID: 999",
  "timestamp": "2025-11-13T10:50:00"
}
```

---

### 7. Deletar Tarefa

**DELETE** `/api/tasks/{id}`

Remove permanentemente uma tarefa.

**Parameters:**
- `id` (path) - ID da tarefa

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "status": 404,
  "message": "Tarefa não encontrada com ID: 999",
  "timestamp": "2025-11-13T10:55:00"
}
```

---

### 8. Listar Tarefas por Status

**GET** `/api/tasks/status/{status}`

Retorna tarefas filtradas por status.

**Parameters:**
- `status` (path) - Status da tarefa: `PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "title": "Tarefa Pendente",
    "description": "Descrição",
    "status": "PENDING",
    "priority": "MEDIUM",
    "createdAt": "2025-11-13T10:30:00",
    "completedAt": null
  }
]
```

---

### 9. Listar Tarefas por Prioridade

**GET** `/api/tasks/priority/{priority}`

Retorna tarefas filtradas por prioridade.

**Parameters:**
- `priority` (path) - Prioridade: `LOW`, `MEDIUM`, `HIGH`, `URGENT`

**Response 200 OK:**
```json
[
  {
    "id": 2,
    "title": "Tarefa Urgente",
    "description": "Descrição",
    "status": "PENDING",
    "priority": "URGENT",
    "createdAt": "2025-11-13T11:00:00",
    "completedAt": null
  }
]
```

---

### 10. Listar Tarefas Pendentes

**GET** `/api/tasks/pending`

Atalho para listar apenas tarefas com status PENDING.

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "title": "Tarefa Pendente",
    "description": "Descrição",
    "status": "PENDING",
    "priority": "MEDIUM",
    "createdAt": "2025-11-13T10:30:00",
    "completedAt": null
  }
]
```

---

### 11. Listar Tarefas Concluídas

**GET** `/api/tasks/completed`

Atalho para listar apenas tarefas com status COMPLETED.

**Response 200 OK:**
```json
[
  {
    "id": 3,
    "title": "Tarefa Concluída",
    "description": "Descrição",
    "status": "COMPLETED",
    "priority": "HIGH",
    "createdAt": "2025-11-13T09:00:00",
    "completedAt": "2025-11-13T11:30:00"
  }
]
```

---

### 12. Contar Total de Tarefas

**GET** `/api/tasks/count`

Retorna o número total de tarefas.

**Response 200 OK:**
```
5
```

---

### 13. Contar Tarefas por Status

**GET** `/api/tasks/count/status/{status}`

Retorna o número de tarefas de um status específico.

**Parameters:**
- `status` (path) - Status: `PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

**Response 200 OK:**
```
3
```

---

## Exemplos com cURL

### Criar uma tarefa
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Minha Nova Tarefa",
    "description": "Descrição detalhada",
    "priority": "HIGH"
  }'
```

### Listar todas as tarefas
```bash
curl http://localhost:8080/api/tasks
```

### Buscar tarefa por ID
```bash
curl http://localhost:8080/api/tasks/1
```

### Atualizar tarefa
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Título Atualizado",
    "status": "IN_PROGRESS",
    "priority": "URGENT"
  }'
```

### Completar tarefa
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/complete
```

### Deletar tarefa
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

### Listar tarefas pendentes
```bash
curl http://localhost:8080/api/tasks/pending
```

### Contar tarefas por status
```bash
curl http://localhost:8080/api/tasks/count/status/PENDING
```

---

## Códigos de Status HTTP

- `200 OK` - Requisição bem-sucedida
- `201 Created` - Recurso criado com sucesso
- `204 No Content` - Operação bem-sucedida sem conteúdo de retorno
- `400 Bad Request` - Dados inválidos na requisição
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro interno do servidor

---

## Modelo de Dados

### Task
```typescript
{
  id: number,                    // ID único da tarefa
  title: string,                 // Título (3-100 caracteres)
  description: string | null,    // Descrição (máx 500 caracteres)
  status: TaskStatus,            // Status da tarefa
  priority: TaskPriority,        // Prioridade da tarefa
  createdAt: datetime,           // Data/hora de criação
  completedAt: datetime | null   // Data/hora de conclusão
}
```

### TaskStatus (Enum)
- `PENDING` - Pendente
- `IN_PROGRESS` - Em Progresso
- `COMPLETED` - Concluída
- `CANCELLED` - Cancelada

### TaskPriority (Enum)
- `LOW` - Baixa
- `MEDIUM` - Média
- `HIGH` - Alta
- `URGENT` - Urgente

---

## CORS

A API está configurada para aceitar requisições de qualquer origem (`*`).
Em produção, configure origens específicas no `TaskRestController`.

---

## Autenticação

Esta versão não implementa autenticação. Para produção, considere adicionar:
- Spring Security
- JWT Tokens
- OAuth2
