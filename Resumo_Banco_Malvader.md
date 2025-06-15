
# Resumo – Trabalho Banco Malvader (Programação Laboratorial de Banco de Dados)

## ✅ Objetivo do Projeto:
Criar um sistema bancário completo com interface gráfica e persistência em banco MySQL, aplicando conceitos de **POO**, **SQL avançado**, **Triggers**, **Procedures**, **Views** e **auditoria de ações**.

---

## ✅ Funcionalidades Principais:

- **Autenticação de usuários (Funcionário e Cliente)** com senha + OTP (One-Time Password).
- **Gerenciamento de Contas:** Poupança, Corrente, Investimento.
- **Operações Bancárias:** Depósito, Saque, Transferência, Consulta de saldo, Extrato.
- **Gestão de Funcionários:** Cadastro, Hierarquia, Controle de permissões.
- **Relatórios Financeiros:** Exportáveis em Excel e PDF.
- **Auditoria:** Log de todas as ações críticas no sistema.

---

## ✅ Tecnologias e Arquitetura:

- **Banco de Dados:** MySQL
- **Frontend:** Livre (Java Swing/JavaFX/Python Tkinter/HTML+JS etc.)
- **Backend:** JDBC ou equivalente
- **Arquitetura:** MVC com pacotes: `dao`, `model`, `view`, `controller`, `util`

---

## ✅ Banco de Dados:

- **Tabelas:** Usuario, Funcionário, Cliente, Endereço, Agência, Conta, Conta_Poupança, Conta_Corrente, Conta_Investimento, Transação, Auditoria, Relatório.
- **Gatilhos (Triggers):** Ex: Atualização automática de saldo, Validação de senha, Limite de depósito diário.
- **Stored Procedures:** Ex: Gerar OTP, Calcular score de crédito.
- **Views:** Ex: Resumo de contas, Movimentações recentes.

---

## ✅ Regras de Negócio Exigidas:

- Limite de funcionários por agência.
- Limite de depósitos diários.
- Bloqueio de conta com saldo negativo.
- Controle de tentativas de login (bloqueio temporário).
- Histórico de encerramento de conta.
- Score de crédito calculado com base nas transações.

---

## ✅ Requisitos Não Funcionais:

- Consultas rápidas (resposta < 2 segundos com 1.000 transações).
- Senhas criptografadas (MD5 ou superior).
- Suporte a no mínimo 100 contas e 10.000 transações.

---

## ✅ Entrega Obrigatória:

- Código-fonte organizado e comentado.
- Banco de Dados funcionando com todas as constraints, procedures e triggers.
- Documentação com diagrama ER + descrição das regras de negócio.
- Apresentação prática com demonstração (5 a 10 minutos).
