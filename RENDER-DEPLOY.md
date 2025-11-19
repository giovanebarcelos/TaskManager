# 🚀 Deploy Automático no Render.com

## ✅ Configuração Completa em 5 Minutos

### 📋 Pré-requisitos
- ✅ Conta no GitHub (você já tem)
- ✅ Repositório público ou privado (você já tem)
- ⏳ Conta no Render.com (vamos criar agora)

---

## 🎯 Passo a Passo

### 1️⃣ **Criar Conta no Render** (1 minuto)

1. Acesse: **https://render.com**
2. Clique em **"Get Started for Free"**
3. Escolha **"Sign Up with GitHub"**
4. Autorize o Render a acessar seu GitHub
5. ✅ Pronto! Conta criada

### 2️⃣ **Criar Novo Web Service** (2 minutos)

1. No dashboard do Render, clique em **"New +"** (canto superior direito)
2. Selecione **"Web Service"**
3. Clique em **"Connect a repository"**
4. Encontre e selecione: **`giovanebarcelos/Gerenciamento-de-Tarefas`**
   - Se não aparecer, clique em **"Configure account"** para dar permissão
5. Clique em **"Connect"**

### 3️⃣ **Configurar o Serviço** (2 minutos)

Na tela de configuração, preencha:

```
Name: task-manager
(ou qualquer nome que preferir)

Region: Oregon (US West)
(escolha o mais próximo)

Branch: main

Runtime: Docker
(Render detecta automaticamente o Dockerfile)

Instance Type: Free
(750 horas/mês grátis)
```

#### **Variáveis de Ambiente** (opcional)

Clique em **"Advanced"** e adicione:

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `SERVER_PORT` | `8080` |

#### **Health Check Path** (recomendado)

```
/actuator/health
```

### 4️⃣ **Deploy!** (1 minuto)

1. Role até o final da página
2. Clique em **"Create Web Service"**
3. ⏳ Aguarde 3-5 minutos (primeira vez é mais demorado)
4. 🎉 Deploy concluído!

---

## 🌐 **Acessar Sua Aplicação**

Após o deploy, sua aplicação estará disponível em:

```
https://task-manager-XXXXX.onrender.com
```

O Render gera uma URL única. Você verá ela no dashboard.

### **URLs que funcionarão:**

- **Interface Web**: `https://task-manager-XXXXX.onrender.com`
- **Listar tarefas**: `https://task-manager-XXXXX.onrender.com/tasks`
- **API REST**: `https://task-manager-XXXXX.onrender.com/api/tasks`
- **Health Check**: `https://task-manager-XXXXX.onrender.com/actuator/health`

---

## 🔄 **Deploy Automático Configurado!**

Agora, toda vez que você fizer push para `main`:

```bash
git add .
git commit -m "nova funcionalidade"
git push origin main
```

O Render automaticamente:
1. ✅ Detecta o push
2. ✅ Baixa o código
3. ✅ Constrói a imagem Docker
4. ✅ Faz deploy
5. ✅ Aplicação atualizada online! 🚀

**Tempo total**: ~3-5 minutos por deploy

---

## 📊 **Monitoramento**

### Ver Logs em Tempo Real

1. No dashboard do Render
2. Clique no seu serviço "task-manager"
3. Vá na aba **"Logs"**
4. Veja os logs em tempo real! 📝

### Ver Métricas

1. Aba **"Metrics"**
2. Veja:
   - CPU usage
   - Memory usage
   - Request count
   - Response time

### Ver Deploys Anteriores

1. Aba **"Events"**
2. Histórico completo de deploys
3. Pode fazer rollback se necessário

---

## 🎨 **Customizar Domínio** (Opcional)

### Usar Domínio Próprio

1. Vá em **Settings** do serviço
2. Clique em **"Custom Domains"**
3. Adicione seu domínio: `tasks.seusite.com`
4. Configure DNS (CNAME ou A record)
5. ✅ Pronto!

### Subdomínio Render Personalizado

1. Clique no nome do serviço
2. Edite para algo memorável:
   ```
   task-manager-giovane
   ```
3. Nova URL: `https://task-manager-giovane.onrender.com`

---

## 🔒 **Segurança e Boas Práticas**

### 1. Variáveis de Ambiente Sensíveis

Se tiver senhas/chaves, adicione como **Secret Files**:

1. Settings → Environment
2. Add Secret File
3. Nome: `application-prod.properties`
4. Conteúdo com senhas

### 2. Auto-Deploy

Por padrão está ativado. Para desativar:

1. Settings → Build & Deploy
2. Desmarque **"Auto-Deploy"**

### 3. Health Checks

Configurado para `/actuator/health`:
- Render reinicia automaticamente se app cair
- Garante alta disponibilidade

---

## 💰 **Plano Gratuito - Limites**

| Recurso | Limite Free Tier |
|---------|------------------|
| Horas/mês | 750 horas (sempre ativo) |
| CPU | 0.5 CPU compartilhado |
| RAM | 512 MB |
| Bandwidth | 100 GB/mês |
| Build time | 500 minutos/mês |
| Custom domains | Sim, ilimitado |
| SSL | Sim, automático |

**Nota**: App hiberna após 15 min de inatividade (demora ~30s no primeiro acesso)

### **Evitar Hibernação** (Opcional)

Use um serviço de ping gratuito:

1. **UptimeRobot**: https://uptimerobot.com
2. Adicione sua URL
3. Ping a cada 5 minutos
4. App sempre ativo! ✅

---

## 🐛 **Troubleshooting**

### Deploy Falhou

**Erro**: "Port already in use"
**Solução**: Certifique-se que `SERVER_PORT=8080` no Dockerfile

**Erro**: "Out of memory"
**Solução**: Otimize a aplicação ou upgrade para plano pago

### App Lenta no Primeiro Acesso

**Causa**: Hibernação após 15 min de inatividade
**Solução**: 
- Use UptimeRobot para manter ativo
- Ou upgrade para plano pago ($7/mês)

### Banco de Dados SQLite

**Atenção**: O Render usa sistema de arquivos efêmero!

Para persistir dados, use:
1. **Render PostgreSQL** (grátis até 1GB)
2. **MongoDB Atlas** (grátis até 512MB)

Ou para desenvolvimento, SQLite funciona mas dados resetam no redeploy.

---

## 📈 **Upgrade para Plano Pago** (Opcional)

Se precisar de mais recursos:

| Plano | Preço | Recursos |
|-------|-------|----------|
| **Starter** | $7/mês | 1 CPU, 1GB RAM, sem hibernação |
| **Standard** | $25/mês | 2 CPU, 2GB RAM, auto-scaling |
| **Pro** | $85/mês | 4 CPU, 4GB RAM, suporte prioritário |

---

## 🔗 **Links Úteis**

- **Dashboard Render**: https://dashboard.render.com
- **Documentação**: https://render.com/docs
- **Status Page**: https://status.render.com
- **Suporte**: https://render.com/support

---

## ✅ **Checklist Final**

- [ ] Conta Render criada
- [ ] Repositório GitHub conectado
- [ ] Web Service criado
- [ ] Deploy concluído com sucesso
- [ ] URL funcionando
- [ ] Testado interface web
- [ ] Testado API REST
- [ ] Logs verificados
- [ ] Auto-deploy funcionando

---

## 🎉 **Pronto!**

Sua aplicação está online e acessível publicamente! 🚀

**URL**: `https://task-manager-XXXXX.onrender.com`

Toda vez que você fizer `git push origin main`, o Render fará deploy automático em ~3-5 minutos!

---

## 📱 **Compartilhar com o Mundo**

Agora você pode compartilhar sua aplicação:

```
🔗 Task Manager App
https://task-manager-XXXXX.onrender.com

✨ Features:
• Interface web completa
• API REST
• Testes 100% passando
• Deploy automático
• Código no GitHub
```

---

## 🚀 **Próximos Passos** (Opcional)

1. **Adicionar banco de dados persistente**
   - Render PostgreSQL (grátis)
   - MongoDB Atlas (grátis)

2. **Configurar domínio personalizado**
   - `tasks.seusite.com`

3. **Adicionar autenticação**
   - Spring Security
   - OAuth2 / JWT

4. **Monitoramento avançado**
   - New Relic (grátis)
   - Sentry para erros

5. **CI/CD avançado**
   - Testes automáticos
   - Preview deployments para PRs

---

**Dúvidas?** Consulte: https://render.com/docs/deploy-spring-boot
