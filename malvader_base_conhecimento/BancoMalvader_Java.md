
# Banco Malvader - Parte Java (Frontend e Lógica de Aplicação)

## Linguagem e Interface Gráfica

- Linguagem recomendada: **Java**
- Interface gráfica: **Swing**, **JavaFX** ou qualquer outra tecnologia que permita integração com MySQL via JDBC.
- Deve implementar arquitetura **MVC (Model-View-Controller)** ou equivalente.

## Estrutura de Pacotes Java

- `dao`: Classes para acesso ao banco de dados (ex.: métodos como `findByScoreRange`, `getTransactionSummary`).
- `model`: Classes que representam as tabelas do banco (POJOs com validações internas).
- `view`: Interface gráfica (telas de login, menus, formulários, relatórios).
- `controller`: Contém a lógica de negócios. Controla chamadas a procedures e validações.
- `util`: Responsável por conexão com MySQL, manipulação de JSON/CSV para relatórios e outras funções utilitárias.

## Funcionalidades Java Obrigatórias

- **Autenticação com OTP:** Login com senha e código de uso único (OTP) gerado pelo banco.
- **Menus com Separação de Perfis:** Diferenciar interfaces para Funcionário e Cliente.
- **Formulários para:**
  - Cadastro de usuários e funcionários.
  - Criação, alteração e encerramento de contas.
  - Realização de transações financeiras (depósitos, saques, transferências).
  - Geração e exportação de relatórios (Excel/PDF).

## Regras de Usabilidade

- Mensagens claras de sucesso e erro.
- Validação de entrada de dados antes de acessar o banco.
- Layout intuitivo, mesmo que simples.

## Requisitos Técnicos Java

- Conexão via JDBC.
- Implementação de transações SQL para operações críticas.
- Exportação de relatórios em pelo menos dois formatos (exemplo: Excel e PDF).
- Código comentado, com explicações sobre a lógica de interação com o banco.

## Exemplo de Funcionalidades no Java:

- `authenticateUserWithOTP()`
- `getClientAccounts()`
- `generateReportAsPDF()`
- `performTransactionWithSQLTransaction()`
