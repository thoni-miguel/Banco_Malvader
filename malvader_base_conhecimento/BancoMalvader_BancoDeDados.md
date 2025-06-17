
# Banco Malvader - Parte Banco de Dados (MySQL)

## Requisitos Técnicos do Banco de Dados

- SGBD obrigatório: **MySQL (versão 8.x ou superior)**.
- Estrutura relacional com normalização e integridade referencial.

## Principais Componentes no Banco

### Tabelas Obrigatórias

- **usuario:** Armazena dados básicos e credenciais (senhas criptografadas em MD5 ou superior).
- **funcionario:** Dados funcionais com hierarquia.
- **cliente:** Dados de clientes e score de crédito.
- **endereco:** Endereço de usuários.
- **agencia:** Localização e códigos de agência.
- **conta:** Informações de conta bancária (corrente, poupança, investimento).
- **conta_poupanca, conta_corrente, conta_investimento:** Detalhes específicos por tipo de conta.
- **transacao:** Todas as movimentações financeiras.
- **auditoria:** Log de todas as ações críticas (login, alteração de dados, transações).
- **relatorio:** Registro de relatórios gerados.

### Gatilhos (Triggers)

- **Atualização automática de saldo após cada transação.**
- **Validação de senha forte antes de atualizações de senha.**
- **Limitação de depósitos diários por cliente (R$10.000/dia).**
- **Registro de aberturas de conta e alterações em auditoria.**

### Procedures (Stored Procedures)

- `gerar_otp(id_usuario)`: Gera código OTP de 6 dígitos, válido por 5 minutos.
- `calcular_score_credito(id_cliente)`: Atualiza score de crédito com base no histórico de transações.
- Procedures adicionais para geração de relatórios e encerramento de contas, se necessário.

### Views (Visões)

- `vw_resumo_contas`: Resumo de contas por cliente.
- `vw_movimentacoes_recentes`: Movimentações financeiras dos últimos 90 dias.

### Índices Recomendados

- Índices nos campos: `numero_conta`, `data_hora`, `cpf`, `id_cliente`, `id_funcionario`.

### Regras de Negócio Importantes no Banco

- CPF único por usuário.
- Número de conta gerado automaticamente com dígito verificador (ex.: Algoritmo de Luhn).
- Controle de tentativas de login (bloqueio após 3 tentativas).
- Limitação de funcionários por agência (máximo de 20).
- Regras de saldo mínimo, bloqueio de encerramento com saldo negativo, entre outras.

### Desempenho e Escalabilidade

- Consultas devem retornar resultados em menos de 2 segundos com até 1.000 transações.
- Suporte para no mínimo 100 contas e 10.000 transações.

### Auditoria e Segurança

- Log de todas as alterações críticas no sistema.
- Senhas armazenadas com criptografia MD5 ou superior.
- Controle de OTP com validade temporal.
