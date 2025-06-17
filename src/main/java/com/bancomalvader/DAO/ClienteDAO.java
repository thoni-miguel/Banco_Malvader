/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas aos
 * clientes no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar dados de clientes no banco de
 * dados.
 *
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import com.bancomalvader.Model.*;
import java.sql.*;

public class ClienteDAO extends UsuarioDAO {

  public Cliente inserirCliente(int idUsuario) {
    Connection conn = null;
    try {
      conn = DatabaseConnection.getConnection();
      String sql = "INSERT INTO cliente (id_usuario) VALUES (?)";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, idUsuario);
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        int idCliente = rs.getInt(1);
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        return cliente;
      }

      throw new RuntimeException("Erro ao inserir cliente: ID não retornado.");
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao inserir cliente: " + e.getMessage());
    } finally {
      DatabaseConnection.closeConnection(conn);
    }
  }

  public void excluirCliente(int idUsuario) {
    String query = "DELETE FROM cliente WHERE id_usuario = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idUsuario);
      ps.executeUpdate();
      System.out.println("Cliente excluído com sucesso. ID do usuário: " + idUsuario);

    } catch (SQLException e) {
      throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage(), e);
    }
  }

  public Integer buscarIdClientePorUsuario(int idUsuario) {
    String query = "SELECT id_cliente FROM cliente WHERE id_usuario = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, idUsuario);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("id_cliente");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar ID do cliente: " + e.getMessage(), e);
    }
    return null;
  }

  public Cliente buscarClientePorId(int idCliente) {
    String query = "SELECT * FROM cliente WHERE id_cliente = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, idCliente);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        return cliente;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
    }
    return null;
  }

  public Usuario validarLogin(String nome, String senha) {
    String query =
        "SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.tipo_usuario, u.senha_hash, u.otp_ativo, u.otp_expiracao "
            + "FROM usuario u "
            + "INNER JOIN cliente c ON u.id_usuario = c.id_usuario "
            + "WHERE u.nome = ? AND u.senha_hash = ?";

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, nome);
      ps.setString(2, senha);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Usuario(
              rs.getInt("id_usuario"),
              rs.getString("nome"),
              rs.getString("cpf"),
              rs.getDate("data_nascimento"),
              rs.getString("telefone"),
              rs.getString("tipo_usuario"),
              rs.getString("senha_hash"),
              rs.getString("otp_ativo"),
              rs.getTimestamp("otp_expiracao"));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao validar login do cliente: " + e.getMessage(), e);
    }

    return null;
  }

  public Cliente buscarClientePorCPF(String cpf) {
    String query =
        """
            SELECT c.id_cliente, c.id_usuario, c.score_credito,
                   u.nome, u.cpf, u.data_nascimento, u.telefone,
                   e.local, e.numero_casa, e.cep, e.bairro, e.cidade, e.estado
            FROM cliente c
            INNER JOIN usuario u ON c.id_usuario = u.id_usuario
            INNER JOIN endereco e ON u.id_usuario = e.id_usuario
            WHERE u.cpf = ?
            """;

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, cpf);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Endereco endereco =
            new Endereco(
                rs.getString("cep"),
                rs.getString("local"),
                rs.getInt("numero_casa"),
                rs.getString("bairro"),
                rs.getString("cidade"),
                rs.getString("estado"));

        Usuario usuario =
            new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getDate("data_nascimento"),
                rs.getString("telefone"),
                "CLIENTE",
                null,
                null,
                null);

        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id_cliente"));
        cliente.setUsuario(usuario);
        cliente.setEndereco(endereco);
        cliente.setScoreCredito(rs.getBigDecimal("score_credito"));

        return cliente;
      }

    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar cliente por CPF: " + e.getMessage(), e);
    }

    return null;
  }
}
