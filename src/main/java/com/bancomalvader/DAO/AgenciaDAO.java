package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgenciaDAO {
    
    public Integer buscarIdAgenciaPorCodigo(String codigoAgencia) {
        String query = "SELECT id_agencia FROM agencia WHERE codigo_agencia = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setString(1, codigoAgencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_agencia");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ID da agência: " + e.getMessage(), e);
        }
        return null;
    }

    public Integer inserirAgencia(String codigoAgencia, String nome, int enderecoId) {
        String query = "INSERT INTO agencia (codigo_agencia, nome, endereco_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, codigoAgencia);
            ps.setString(2, nome);
            ps.setInt(3, enderecoId);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao inserir agência: nenhuma linha afetada.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID da agência inserida.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir agência: " + e.getMessage(), e);
        }
    }
} 