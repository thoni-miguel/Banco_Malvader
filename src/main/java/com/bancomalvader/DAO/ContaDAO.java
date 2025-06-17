/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas às contas
 * bancárias no sistema Banco Malvader.
 *
 * <p>Fornece métodos genéricos para manipular dados de contas, como inserção, atualização, exclusão
 * e consultas no banco de dados. Esta classe serve como base para tipos específicos de contas, como
 * correntes e poupanças.
 *
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import com.bancomalvader.Model.Conta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

  // Método para inserir uma nova conta
  public Conta inserirConta(String numeroConta, String codigoAgencia, String tipoConta, int idCliente) {
    String sql =
        "INSERT INTO conta (numero_conta, id_agencia, tipo_conta, id_cliente) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      // Busca o ID da agência pelo código
      AgenciaDAO agenciaDAO = new AgenciaDAO();
      Integer idAgencia = agenciaDAO.buscarIdAgenciaPorCodigo(codigoAgencia);
      if (idAgencia == null) {
        throw new SQLException("Agência não encontrada com o código: " + codigoAgencia);
      }

      // Configura os parâmetros
      stmt.setString(1, numeroConta);
      stmt.setInt(2, idAgencia);
      stmt.setString(3, tipoConta);
      stmt.setInt(4, idCliente);

      // Executa a inserção
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Falha ao inserir conta: nenhuma linha foi afetada.");
      }

      // Tenta obter o ID gerado
      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          int idConta = rs.getInt(1);
          System.out.println("Conta criada com ID: " + idConta); // Log para debug
          return new Conta(idConta, numeroConta, codigoAgencia, 0.00, tipoConta, idCliente);
        } else {
          throw new SQLException("Falha ao obter o ID da conta inserida.");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir conta: " + e.getMessage(), e);
    }
  }

  // Método para verificar se um cliente possui outras contas
  public boolean verificarOutrasContas(int idCliente) {
    String query = "SELECT COUNT(*) AS total FROM conta WHERE id_cliente = ?";

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idCliente);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("total") > 0; // Retorna true se o cliente tiver outras contas
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao verificar outras contas: " + e.getMessage(), e);
    }

    return false; // Retorna false se não encontrar outras contas
  }

  // Método para buscar uma conta pelo nome do cliente e número da conta
  public Conta buscarContaPorNomeENumero(String nomeCliente, String numeroConta) {
    String query =
        """
                SELECT c.id_conta, c.numero_conta, a.codigo_agencia, c.saldo, c.tipo_conta, c.id_cliente,
                       u.nome AS nome_cliente, u.cpf AS cpf_cliente
                FROM conta c
                INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente
                INNER JOIN usuario u ON cl.id_usuario = u.id_usuario
                INNER JOIN agencia a ON c.id_agencia = a.id_agencia
                WHERE u.nome = ? AND c.numero_conta = ?
            """;

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, nomeCliente);
      ps.setString(2, numeroConta);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          Conta conta =
              new Conta(
                  rs.getInt("id_conta"),
                  rs.getString("numero_conta"),
                  rs.getString("codigo_agencia"),
                  rs.getDouble("saldo"),
                  rs.getString("tipo_conta"),
                  rs.getInt("id_cliente"));

          // Adiciona informações do cliente
          conta.setNomeCliente(rs.getString("nome_cliente"));
          conta.setCpfCliente(rs.getString("cpf_cliente"));

          return conta;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar conta por nome e número: " + e.getMessage(), e);
    }

    return null; // Retorna null se a conta não for encontrada
  }

  // Método para buscar uma conta pelo número
  public Conta buscarContaPorNumero(String numeroConta) {
    String query =
        """
        SELECT c.id_conta, c.numero_conta, a.codigo_agencia, c.saldo, c.tipo_conta, c.id_cliente, c.status,
               u.nome AS nome_cliente, u.cpf AS cpf_cliente,
               cc.limite, cc.data_vencimento
        FROM conta c
        INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente
        INNER JOIN usuario u ON cl.id_usuario = u.id_usuario
        INNER JOIN agencia a ON c.id_agencia = a.id_agencia
        LEFT JOIN conta_corrente cc ON c.id_conta = cc.id_conta
        WHERE c.numero_conta = ? AND c.status = 'ATIVA'
    """;

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, numeroConta);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Conta conta =
            new Conta(
                rs.getInt("id_conta"),
                rs.getString("numero_conta"),
                rs.getString("codigo_agencia"),
                rs.getDouble("saldo"),
                rs.getString("tipo_conta"),
                rs.getInt("id_cliente"));

        // Dados adicionais
        conta.setNomeCliente(rs.getString("nome_cliente"));
        conta.setCpfCliente(rs.getString("cpf_cliente"));

        // Setar status corretamente
        String statusStr = rs.getString("status");
        if (statusStr != null) {
          conta.setStatus(com.bancomalvader.Model.StatusConta.valueOf(statusStr));
        }

        // Dados da conta corrente
        if ("CORRENTE".equalsIgnoreCase(rs.getString("tipo_conta"))) {
          conta.setLimite(rs.getBigDecimal("limite"));
          conta.setDataVencimento(String.valueOf(rs.getDate("data_vencimento")));
        }

        return conta;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar conta por número: " + e.getMessage(), e);
    }

    return null;
  }

  // Método para excluir uma conta
  public void excluirConta(int idConta) {
    String query = "DELETE FROM conta WHERE id_conta = ?";

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idConta);
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("Erro ao excluir conta: " + e.getMessage(), e);
    }
  }

  // Buscar todas as contas de um usuário (cliente) pelo id_usuario
  public List<Conta> buscarContasPorIdUsuario(int idUsuario) {
    List<Conta> contas = new ArrayList<>();
    String query =
        "SELECT c.id_conta, c.numero_conta, a.codigo_agencia, c.saldo, c.tipo_conta, c.id_cliente, u.nome AS nome_cliente, u.cpf AS cpf_cliente, " +
        "cc.limite, cc.data_vencimento " +
        "FROM conta c " +
        "INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente " +
        "INNER JOIN usuario u ON cl.id_usuario = u.id_usuario " +
        "INNER JOIN agencia a ON c.id_agencia = a.id_agencia " +
        "LEFT JOIN conta_corrente cc ON c.id_conta = cc.id_conta " +
        "WHERE u.id_usuario = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, idUsuario);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Conta conta = new Conta(
            rs.getInt("id_conta"),
            rs.getString("numero_conta"),
            rs.getString("codigo_agencia"),
            rs.getDouble("saldo"),
            rs.getString("tipo_conta"),
            rs.getInt("id_cliente"));
        conta.setNomeCliente(rs.getString("nome_cliente"));
        conta.setCpfCliente(rs.getString("cpf_cliente"));
        if ("CORRENTE".equalsIgnoreCase(rs.getString("tipo_conta"))) {
          conta.setLimite(rs.getBigDecimal("limite"));
          conta.setDataVencimento(rs.getDate("data_vencimento") != null ? rs.getDate("data_vencimento").toString() : "");
        }
        contas.add(conta);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar contas do usuário: " + e.getMessage(), e);
    }
    return contas;
  }

  // Realizar depósito
  public void realizarDeposito(int idConta, double valor, String descricao) {
    String sql = "INSERT INTO transacao (id_conta_origem, tipo_transacao, valor, descricao) VALUES (?, 'DEPOSITO', ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, idConta);
      stmt.setDouble(2, valor);
      stmt.setString(3, descricao);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao realizar depósito: " + e.getMessage(), e);
    }
  }

  // Realizar saque
  public void realizarSaque(int idConta, double valor, String descricao) {
    String sql = "INSERT INTO transacao (id_conta_origem, tipo_transacao, valor, descricao) VALUES (?, 'SAQUE', ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, idConta);
      stmt.setDouble(2, valor);
      stmt.setString(3, descricao);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao realizar saque: " + e.getMessage(), e);
    }
  }

  // Buscar transações (extrato) de uma conta
  public List<String> buscarTransacoesPorIdConta(int idConta) {
    List<String> extrato = new ArrayList<>();
    String query = "SELECT data_hora, tipo_transacao, valor, descricao FROM transacao WHERE id_conta_origem = ? ORDER BY data_hora DESC";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, idConta);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Timestamp dataHora = rs.getTimestamp("data_hora");
        String tipo = rs.getString("tipo_transacao");
        double valor = rs.getDouble("valor");
        String descricao = rs.getString("descricao");
        extrato.add(String.format("%s | %s | R$ %.2f | %s", dataHora, tipo, valor, descricao != null ? descricao : ""));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar extrato: " + e.getMessage(), e);
    }
    return extrato;
  }

  // Atualiza o status da conta pelo número da conta
  public boolean atualizarStatusConta(String numeroConta, String novoStatus) {
    String sql = "UPDATE conta SET status = ? WHERE numero_conta = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, novoStatus);
      stmt.setString(2, numeroConta);
      int rows = stmt.executeUpdate();
      return rows > 0;
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao atualizar status da conta: " + e.getMessage(), e);
    }
  }
}
