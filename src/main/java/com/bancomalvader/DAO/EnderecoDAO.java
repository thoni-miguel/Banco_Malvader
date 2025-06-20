/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas aos
 * endereços no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar informações de endereços no banco
 * de dados.
 *
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoDAO {
  public void inserirEndereco(
      String cep,
      String local,
      int numeroCasa,
      String bairro,
      String cidade,
      String estado,
      int idUsuario) {
    String query =
        "INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, cep);
      ps.setString(2, local);
      ps.setInt(3, numeroCasa);
      ps.setString(4, bairro);
      ps.setString(5, cidade);
      ps.setString(6, estado);
      ps.setInt(7, idUsuario);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir endereço: " + e.getMessage(), e);
    }
  }

  public int obterUltimoIdEndereco() {
    String query = "SELECT MAX(id_endereco) AS id_endereco FROM endereco";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        return rs.getInt("id_endereco");
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao obter o último ID de endereço: " + e.getMessage(), e);
    }
    return -1; // Retorna -1 se não encontrar nenhum ID
  }
}
