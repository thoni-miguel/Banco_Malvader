
# Banco Malvader - Checklist de Implementação

## ✅ Fase 1: Banco de Dados (MySQL)

- [ ] Criar todas as **tabelas** com as constraints necessárias (PK, FK, UNIQUE, etc).
- [ ] Implementar **Gatilhos (Triggers):**
  - [ ] Atualização de saldo após transações.
  - [ ] Validação de senha forte.
  - [ ] Limite de depósito diário.
- [ ] Criar **Stored Procedures:**
  - [ ] Gerar OTP.
  - [ ] Calcular score de crédito.
- [ ] Criar **Views (Visões):**
  - [ ] Resumo de contas por cliente.
  - [ ] Movimentações recentes.
- [ ] Criar **Índices** para otimizar consultas em campos críticos.

## ✅ Fase 2: Backend Java

- [ ] Estruturar o projeto usando **padrão MVC** com os pacotes: `dao`, `model`, `view`, `controller`, `util`.
- [ ] Implementar **conexão JDBC** com pool de conexões.
- [ ] Criar classes DAO para acesso ao banco.
- [ ] Criar modelos (POJOs) refletindo as tabelas.
- [ ] Implementar Controller com lógica de negócio e chamadas às procedures.

## ✅ Fase 3: Interface Gráfica (Java Swing/JavaFX)

- [ ] Criar tela de **login** com campo de senha e OTP.
- [ ] Criar menus separados para **Funcionário** e **Cliente**.
- [ ] Criar formulários para:
  - [ ] Abertura de conta.
  - [ ] Encerramento de conta.
  - [ ] Consultas.
  - [ ] Alterações de dados.
  - [ ] Transações financeiras (depósito, saque, transferência).
  - [ ] Geração de relatórios.

## ✅ Fase 4: Funcionalidades de Negócio

- [ ] Implementar **validações no Java** antes de enviar dados para o banco.
- [ ] Tratar **erros SQL** com try-catch no Java.
- [ ] Chamar procedures como `gerar_otp` e `calcular_score_credito` através do Java.
- [ ] Garantir que as triggers estejam funcionando ao inserir/alterar dados.

## ✅ Fase 5: Exportação de Relatórios

- [ ] Implementar **exportação para Excel e PDF** usando bibliotecas Java.

## ✅ Fase 6: Documentação e Entrega

- [ ] Criar **Diagrama ER** completo.
- [ ] Documentar todas as regras de negócio implementadas no banco.
- [ ] Criar **instruções de instalação e configuração**.
- [ ] Preparar **apresentação prática** com foco nos requisitos exigidos.
