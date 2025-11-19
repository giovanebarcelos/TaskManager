# 🚀 Guia Rápido: Configurar Deploy Automático

## ⚡ Configuração em 5 Minutos

### 1️⃣ Habilitar GitHub Packages (1 min)

1. Acesse seu repositório no GitHub
2. Vá em **Settings** → **Actions** → **General**
3. Em **Workflow permissions**:
   - ✅ Marque: **Read and write permissions**
   - ✅ Marque: **Allow GitHub Actions to create and approve pull requests**
4. Clique em **Save**

### 2️⃣ Fazer Push para Main (1 min)

```bash
git add .
git commit -m "feat: adicionar deploy automático"
git push origin main
```

### 3️⃣ Verificar o Workflow (2 min)

1. Acesse: `https://github.com/SEU_USUARIO/SEU_REPO/actions`
2. Veja o workflow **CI/CD Pipeline** em execução
3. Aguarde ~4 minutos até completar

### 4️⃣ Tornar a Imagem Pública (Opcional - 1 min)

1. Vá em: `https://github.com/SEU_USUARIO?tab=packages`
2. Clique no package **task-manager**
3. Clique em **Package settings** (canto direito)
4. Em **Danger Zone** → **Change visibility**
5. Selecione **Public** → Confirme

### 5️⃣ Testar a Imagem (1 min)

```bash
# Baixar e executar
docker pull ghcr.io/SEU_USUARIO/SEU_REPO:latest
docker run -d -p 8080:8080 ghcr.io/SEU_USUARIO/SEU_REPO:latest

# Acessar
curl http://localhost:8080
```

## ✅ Pronto! Deploy Automático Configurado

Agora, toda vez que você fizer push para `main`:
1. ✅ Testes executam automaticamente
2. ✅ Cobertura é verificada
3. ✅ Imagem Docker é criada
4. ✅ Deploy é feito automaticamente
5. ✅ Imagem fica disponível em `ghcr.io`

## 🎯 Próximos Passos (Opcional)

### Deploy para Servidor

Adicione ao seu servidor (VPS/Cloud):

```bash
# Criar script de deploy
cat > /opt/task-manager/deploy.sh << 'EOF'
#!/bin/bash
docker pull ghcr.io/SEU_USUARIO/SEU_REPO:latest
docker stop task-manager || true
docker rm task-manager || true
docker run -d \
  --name task-manager \
  -p 8080:8080 \
  -v /opt/task-manager/data:/app/data \
  --restart unless-stopped \
  ghcr.io/SEU_USUARIO/SEU_REPO:latest
EOF

chmod +x /opt/task-manager/deploy.sh
```

### Webhook para Deploy Automático

Configure um webhook no GitHub para deploy automático no servidor:

```bash
# No servidor, instale o webhook
npm install -g github-webhook-handler

# Configure o serviço
cat > /etc/systemd/system/github-webhook.service << 'EOF'
[Unit]
Description=GitHub Webhook Handler
After=network.target

[Service]
Type=simple
User=deploy
WorkingDirectory=/opt/task-manager
ExecStart=/usr/bin/github-webhook-handler --port 9000 --path /webhook --secret YOUR_SECRET
Restart=always

[Install]
WantedBy=multi-user.target
EOF

systemctl enable github-webhook
systemctl start github-webhook
```

### Monitoramento com Health Check

Adicione ao `docker-compose.yml`:

```yaml
version: '3.8'

services:
  task-manager:
    image: ghcr.io/SEU_USUARIO/SEU_REPO:latest
    ports:
      - "8080:8080"
    volumes:
      - ./data:/app/data
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
```

## 📊 Ver Status do Deploy

### Via GitHub

```
https://github.com/SEU_USUARIO/SEU_REPO/actions
```

### Via Docker

```bash
# Ver imagens disponíveis
docker images ghcr.io/SEU_USUARIO/SEU_REPO

# Ver containers rodando
docker ps -a

# Ver logs
docker logs task-manager -f
```

## 🔔 Configurar Notificações

### Slack

Adicione ao workflow (`.github/workflows/ci-cd.yml`):

```yaml
    - name: Notificar Slack
      if: always()
      uses: 8398a7/action-slack@v3
      with:
        status: ${{ job.status }}
        text: 'Deploy finalizado!'
        webhook_url: ${{ secrets.SLACK_WEBHOOK }}
```

### Email

Configure em: **Settings** → **Notifications** → **Actions**

### Discord

```yaml
    - name: Notificar Discord
      if: always()
      uses: sarisia/actions-status-discord@v1
      with:
        webhook: ${{ secrets.DISCORD_WEBHOOK }}
        status: ${{ job.status }}
        title: "Deploy Status"
```

## 🐛 Troubleshooting Rápido

### Erro: "Permission denied"

**Solução**: Verifique se habilitou "Read and write permissions"

### Erro: "Image not found"

**Solução**: 
1. Veja se o workflow completou com sucesso
2. Verifique em Packages se a imagem foi criada

### Erro: "Docker build failed"

**Solução**: 
1. Veja os logs no Actions
2. Teste localmente: `docker build -t test .`

### Deploy não executa

**Solução**: 
1. Certifique-se que está na branch `main`
2. Verifique se fez `push` (não apenas `commit`)

## 📚 Documentação Completa

- 📖 [DEPLOY.md](DEPLOY.md) - Guia completo de deploy
- 📊 [CI-CD-DIAGRAM.md](CI-CD-DIAGRAM.md) - Diagramas do pipeline
- 📝 [README.md](README.md) - Documentação do projeto

## 💡 Dicas Profissionais

1. **Sempre teste localmente primeiro**
   ```bash
   mvn clean test
   docker build -t test .
   docker run -p 8080:8080 test
   ```

2. **Use tags semânticas**
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

3. **Mantenha branches organizadas**
   - `main` → produção
   - `develop` → desenvolvimento
   - `feature/*` → novas funcionalidades

4. **Monitore recursos**
   ```bash
   docker stats task-manager
   ```

5. **Faça backup do banco de dados**
   ```bash
   docker cp task-manager:/app/data/tasks.db ./backup/
   ```

## 🎉 Tudo Configurado!

Seu pipeline de CI/CD está pronto para uso profissional! 🚀

Qualquer dúvida, consulte a [documentação completa](DEPLOY.md).
