
# Parte Java – Trabalho Banco Malvader (Programação Laboratorial de Banco de Dados)

## Linguagem e Interface Gráfica:

- O front-end pode ser desenvolvido na linguagem de preferência do grupo:
  - Exemplos citados: Java com Swing, JavaFX, Python com Tkinter ou frameworks web como React.
  - Requisito: Precisa integrar com o banco de dados MySQL via JDBC ou equivalente.

---

## Arquitetura Recomendada:

- **MVC (Model-View-Controller)** ou equivalente.

- **Pacotes sugeridos:**

  - `dao`: Acesso ao banco de dados.
  - `model`: Representação das tabelas.
  - `view`: Interface gráfica (Swing, JavaFX, etc.).
  - `controller`: Lógica de negócios.
  - `util`: Conexão com o MySQL e utilitários (como manipulação de JSON/CSV para relatórios).

---

## Integração com Banco de Dados:

- **Conexão:** Uso de driver JDBC para conexão com o MySQL.
- **Recomenda-se:** Implementação de **pool de conexões** para desempenho.

---

## Implementações Esperadas na Parte Java:

- **Autenticação com OTP:** A Java GUI deverá permitir o login via senha e código OTP.
- **Menus separados:** Interface para Funcionários e para Clientes.
- **Formulários de entrada:** Para cadastro, alteração e exclusão de contas e clientes.
- **Validação de entradas:** Antes de enviar dados ao banco (ex.: senhas, campos obrigatórios).
- **Exportação de relatórios:** Front-end deve exportar relatórios para **Excel** e **PDF**.
- **Chamada de Procedures e execução de Triggers:** O código Java deve realizar chamadas às procedures existentes no banco e deixar os triggers fazerem o trabalho no lado SQL.
- **Execução de transações SQL:** Para operações críticas como transferências.

---

## Requisitos de Usabilidade para a Interface Java:

- **Interface intuitiva:** Menus, botões e mensagens claras de erro/sucesso.
- **Feedback de ações:** Exemplo: Exibir mensagens quando uma operação for realizada com sucesso ou em caso de falha de validação.

---

## Requisitos de Código:

- **Organização por pacotes**
- **Comentado:** Código Java com comentários explicando a lógica e as interações com o banco (gatilhos, procedures, views).
- **Exemplos sugeridos de métodos na camada DAO:**
  - `findByScoreRange`
  - `getTransactionSummary`
  - `getClientAccounts`
  - `authenticateUserWithOTP`
- **Validação e tratamento de erros:** Uso de blocos try-catch para exceções SQL e validação de entrada.

---

## Requisitos de Entrega (Parte Java):

- Código-fonte organizado e comentado.
- Deve incluir:
  - Interface gráfica funcional (Swing, JavaFX ou outra escolhida).
  - Integração JDBC com o MySQL.
  - Implementação de todas as funcionalidades exigidas (RF2 e RF3).
  - Exportação de relatórios (Excel/PDF).

