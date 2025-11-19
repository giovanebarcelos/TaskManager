# 🚀 Guia de Deploy

Este documento descreve como fazer deploy da aplicação Task Manager usando diferentes métodos.

## 📦 Deploy Automático via GitHub Actions

O projeto está configurado com **deploy automático** para o GitHub Container Registry (ghcr.io).

### Como Funciona

1. **Trigger**: Quando você faz push para a branch `main`
2. **Build & Test**: Executa todos os testes automaticamente
3. **Docker Build**: Cria uma imagem Docker da aplicação
4. **Deploy**: Publica a imagem no GitHub Container Registry

### Configuração Inicial

#### 1. Habilitar GitHub Packages

No seu repositório GitHub:
1. Vá em **Settings** → **Actions** → **General**
2. Em **Workflow permissions**, selecione:
   - ✅ **Read and write permissions**
3. Salve as configurações

#### 2. Fazer a Imagem Pública (Opcional)

Para permitir que qualquer pessoa baixe sua imagem:
1. Acesse: `https://github.com/users/SEU_USUARIO/packages/container/SEU_REPOSITORIO/settings`
2. Em **Danger Zone** → **Change visibility**
3. Selecione **Public**

### Usar a Imagem Publicada

Após o deploy automático, você pode executar a aplicação em qualquer lugar:

```bash
# Baixar a imagem
docker pull ghcr.io/SEU_USUARIO/SEU_REPOSITORIO:latest

# Executar a aplicação
docker run -d \
  -p 8080:8080 \
  --name task-manager \
  -v $(pwd)/data:/app/data \
  ghcr.io/SEU_USUARIO/SEU_REPOSITORIO:latest

# Acessar
curl http://localhost:8080
```

### Tags Disponíveis

O workflow cria múltiplas tags automaticamente:
- `latest` - Última versão da branch main
- `main` - Branch main
- `main-{sha}` - Commit SHA específico

## 🌐 Outras Opções de Deploy

### 1. Deploy para Heroku

Adicione ao seu workflow:

```yaml
  deploy-heroku:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - uses: actions/checkout@v4
    - uses: akhileshns/heroku-deploy@v3.12.14
      with:
        heroku_api_key: ${{secrets.HEROKU_API_KEY}}
        heroku_app_name: "seu-app-task-manager"
        heroku_email: "seu-email@example.com"
```

**Configuração:**
1. Crie uma conta em [heroku.com](https://heroku.com)
2. Crie um novo app
3. Obtenha sua API Key em: Account Settings → API Key
4. Adicione como secret no GitHub: `HEROKU_API_KEY`

### 2. Deploy para AWS (EC2)

```yaml
  deploy-aws:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - name: Deploy para EC2 via SSH
      uses: appleboy/ssh-action@master
      with:
        host: ${{ secrets.EC2_HOST }}
        username: ${{ secrets.EC2_USERNAME }}
        key: ${{ secrets.EC2_SSH_KEY }}
        script: |
          cd /home/ubuntu/task-manager
          docker pull ghcr.io/${{ github.repository }}:latest
          docker-compose down
          docker-compose up -d
```

**Configuração:**
1. Configure uma instância EC2
2. Instale Docker no servidor
3. Adicione secrets no GitHub:
   - `EC2_HOST`: IP público da instância
   - `EC2_USERNAME`: usuário SSH (geralmente `ubuntu`)
   - `EC2_SSH_KEY`: chave privada SSH

### 3. Deploy para Azure Container Apps

```yaml
  deploy-azure:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - name: Login no Azure
      uses: azure/login@v1
      with:
        creds: ${{ secrets.AZURE_CREDENTIALS }}
        
    - name: Deploy para Container Apps
      uses: azure/container-apps-deploy-action@v1
      with:
        containerAppName: task-manager
        resourceGroup: meu-resource-group
        imageToDeploy: ghcr.io/${{ github.repository }}:latest
```

### 4. Deploy para Google Cloud Run

```yaml
  deploy-gcp:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - name: Setup Cloud SDK
      uses: google-github-actions/setup-gcloud@v1
      with:
        service_account_key: ${{ secrets.GCP_SA_KEY }}
        project_id: ${{ secrets.GCP_PROJECT_ID }}
        
    - name: Deploy para Cloud Run
      run: |
        gcloud run deploy task-manager \
          --image ghcr.io/${{ github.repository }}:latest \
          --platform managed \
          --region us-central1 \
          --allow-unauthenticated
```

### 5. Deploy para VPS via SSH (Manual)

```yaml
  deploy-vps:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - name: Deploy via SSH
      uses: appleboy/ssh-action@master
      with:
        host: ${{ secrets.VPS_HOST }}
        username: ${{ secrets.VPS_USERNAME }}
        password: ${{ secrets.VPS_PASSWORD }}
        script: |
          cd /opt/task-manager
          docker-compose pull
          docker-compose up -d --force-recreate
```

## 📊 Monitoramento de Deploy

### Ver Status do Deploy

Acesse o repositório no GitHub e vá em:
- **Actions** → Veja os workflows em execução
- **Packages** → Veja as imagens Docker publicadas

### Notificações de Deploy

Adicione ao workflow para receber notificações:

```yaml
    - name: Notificar sucesso no Slack
      if: success()
      uses: 8398a7/action-slack@v3
      with:
        status: custom
        custom_payload: |
          {
            text: "✅ Deploy realizado com sucesso!",
            attachments: [{
              color: 'good',
              text: `Commit: ${{ github.sha }}\nAutor: ${{ github.actor }}`
            }]
          }
      env:
        SLACK_WEBHOOK_URL: ${{ secrets.SLACK_WEBHOOK }}
```

## 🔧 Rollback

Se algo der errado, você pode fazer rollback para uma versão anterior:

```bash
# Listar tags disponíveis
docker images ghcr.io/SEU_USUARIO/SEU_REPOSITORIO

# Executar versão específica
docker run -p 8080:8080 ghcr.io/SEU_USUARIO/SEU_REPOSITORIO:main-abc123
```

## 📝 Variáveis de Ambiente

Para configurar a aplicação em produção, use variáveis de ambiente:

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_PATH=/app/data/tasks.db \
  -e SERVER_PORT=8080 \
  ghcr.io/SEU_USUARIO/SEU_REPOSITORIO:latest
```

## 🔒 Secrets Necessários

Configure os seguintes secrets no GitHub (Settings → Secrets and variables → Actions):

| Secret | Descrição | Necessário para |
|--------|-----------|-----------------|
| `GITHUB_TOKEN` | Token automático do GitHub | Deploy Docker (já incluído) |
| `HEROKU_API_KEY` | API Key do Heroku | Deploy Heroku |
| `AZURE_CREDENTIALS` | Credenciais do Azure | Deploy Azure |
| `GCP_SA_KEY` | Service Account Key GCP | Deploy Google Cloud |
| `EC2_HOST` | IP do servidor EC2 | Deploy AWS |
| `EC2_USERNAME` | Usuário SSH | Deploy AWS |
| `EC2_SSH_KEY` | Chave privada SSH | Deploy AWS |

## ✅ Checklist de Deploy

Antes de fazer deploy em produção:

- [ ] Todos os testes passando
- [ ] Cobertura de testes >= 80%
- [ ] Variáveis de ambiente configuradas
- [ ] Secrets configurados no GitHub
- [ ] Backup do banco de dados
- [ ] Monitoramento configurado
- [ ] Logs configurados
- [ ] Health checks funcionando
- [ ] Rollback testado

## 🆘 Troubleshooting

### Erro: "Permission denied" no GitHub Packages

**Solução**: Verifique as permissões do workflow em Settings → Actions → General

### Erro: "Image not found"

**Solução**: Verifique se a imagem foi publicada corretamente em Packages

### Deploy falhou mas testes passaram

**Solução**: Verifique os logs do workflow na aba Actions

## 📚 Referências

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [GitHub Packages](https://docs.github.com/en/packages)
- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Deployment](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)
