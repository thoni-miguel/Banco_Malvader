/**
 * Controlador responsável por gerenciar as operações relacionadas aos usuários do sistema Banco
 * Malvader.
 *
 * <p>Este controlador fornece métodos para criar, atualizar, autenticar e excluir usuários, além de
 * gerenciar perfis e tipos de usuários no sistema.
 *
 */
package com.bancomalvader.Controller;

import com.bancomalvader.DAO.ClienteDAO;
import com.bancomalvader.DAO.EnderecoDAO;
import com.bancomalvader.DAO.FuncionarioDAO;
import com.bancomalvader.DAO.UsuarioDAO;
import com.bancomalvader.Model.*;
import java.util.Date;

public class UsuarioController {
  private ClienteDAO clienteDAO = new ClienteDAO();
  private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

  /**
   * Cadastra um cliente ou funcionário baseado no tipo de usuário.
   *
   * @param nome Nome do usuário.
   * @param cpf CPF do usuário.
   * @param dataNascimento Data de nascimento.
   * @param telefone Telefone do usuário.
   * @param tipoUsuario Tipo do usuário (CLIENTE ou FUNCIONARIO).
   * @param senha Senha do usuário.
   * @param codigoFuncionario Código do funcionário (somente para FUNCIONARIO).
   * @param cargo Cargo do funcionário (somente para FUNCIONARIO).
   * @param cep CEP do endereço do usuário.
   * @param local Local do endereço do usuário.
   * @param numeroCasa Número da casa do endereço do usuário.
   * @param bairro Bairro do endereço do usuário.
   * @param cidade Cidade do endereço do usuário.
   * @param estado Estado do endereço do usuário.
   * @return true se o cadastro foi bem-sucedido, false caso contrário.
   */
  public boolean cadastrarUsuario(
      String nome,
      String cpf,
      Date dataNascimento,
      String telefone,
      String tipoUsuario,
      String senha,
      String codigoFuncionario,
      String cargo,
      String cep,
      String local,
      int numeroCasa,
      String bairro,
      String cidade,
      String estado) {
    try {
      TipoUsuario tipo = TipoUsuario.valueOf(tipoUsuario.toUpperCase());
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      EnderecoDAO enderecoDAO = new EnderecoDAO();

      if (usuarioDAO.verificarCpfExiste(cpf)) {
        throw new IllegalArgumentException("O CPF já está cadastrado no sistema.");
      }

      int idUsuario =
          usuarioDAO.inserirUsuario(
              nome, cpf, new java.sql.Date(dataNascimento.getTime()), telefone, tipoUsuario, senha);

      enderecoDAO.inserirEndereco(cep, local, numeroCasa, bairro, cidade, estado, idUsuario);

      if (tipo == TipoUsuario.CLIENTE) {
        clienteDAO.inserirCliente(idUsuario);
      } else if (tipo == TipoUsuario.FUNCIONARIO) {
        if (codigoFuncionario == null || cargo == null) {
          throw new IllegalArgumentException("Código e cargo são obrigatórios para Funcionários.");
        }

        Funcionario funcionario =
            new Funcionario(
                idUsuario,
                nome,
                cpf,
                new java.sql.Date(dataNascimento.getTime()),
                telefone,
                tipo,
                senha,
                codigoFuncionario,
                CargoFuncionario.valueOf(cargo.toUpperCase()));

        funcionarioDAO.inserirFuncionario(funcionario, idUsuario);
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean excluirCliente(int idUsuario) {
    try {
      ClienteDAO clienteDAO = new ClienteDAO();
      clienteDAO.excluirCliente(idUsuario);

      UsuarioDAO usuarioDAO = new UsuarioDAO();
      usuarioDAO.excluirUsuario(idUsuario);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
