/**
 * Controlador responsável por gerenciar as operações relacionadas às contas no sistema Banco
 * Malvader.
 *
 * <p>Este controlador fornece métodos para criar, atualizar, consultar e excluir contas bancárias,
 * além de realizar operações como depósitos, saques e transferências.
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Controller;

import com.bancomalvader.DAO.*;
import com.bancomalvader.Model.Cliente;
import com.bancomalvader.Model.Conta;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ContaController {

  public boolean cadastrarConta(
      int idUsuario,
      String numeroConta,
      String codigoAgencia,
      String tipoConta,
      String limite,
      Date dataVencimento) {
    try {
      ClienteDAO clienteDAO = new ClienteDAO();
      ContaDAO contaDAO = new ContaDAO();
      ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO();
      ContaPoupancaDAO contaPoupancaDAO = new ContaPoupancaDAO();
      AgenciaDAO agenciaDAO = new AgenciaDAO();

      // Verifica se a agência existe, se não, cria uma nova
      Integer idAgencia = agenciaDAO.buscarIdAgenciaPorCodigo(codigoAgencia);
      if (idAgencia == null) {
        // Cria uma nova agência com nome padrão e endereço padrão
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        // Cria um endereço padrão para a agência
        enderecoDAO.inserirEndereco(
            "00000000", // CEP padrão
            "Endereço da Agência", // Local padrão
            1, // Número padrão
            "Centro", // Bairro padrão
            "Cidade", // Cidade padrão
            "UF", // Estado padrão
            idUsuario // Usa o ID do usuário atual como referência
        );
        
        // Obtém o ID do endereço recém-criado
        int enderecoId = enderecoDAO.obterUltimoIdEndereco();
        
        // Cria a agência
        idAgencia = agenciaDAO.inserirAgencia(
            codigoAgencia,
            "Agência " + codigoAgencia,
            enderecoId
        );
      }

      Integer idCliente = clienteDAO.buscarIdClientePorUsuario(idUsuario);
      if (idCliente == null) {
        throw new IllegalArgumentException("Cliente não encontrado.");
      }

      Conta conta = contaDAO.inserirConta(numeroConta, codigoAgencia, tipoConta, idCliente);
      if (conta == null || idCliente <= 0) {
        System.err.println("Erro: ID da conta não foi gerado ou é inválido.");
        throw new IllegalArgumentException("Erro ao cadastrar conta: ID não gerado.");
      } else {
        System.out.println("Conta cadastrada com sucesso. ID: " + idCliente);
      }

      if (tipoConta.equalsIgnoreCase("CORRENTE")) {
        if (limite == null || limite.isEmpty() || dataVencimento == null) {
          throw new IllegalArgumentException("Limite e data de vencimento são obrigatórios.");
        }
        contaCorrenteDAO.inserirContaCorrente(new BigDecimal(limite), dataVencimento, conta.getIdConta());
      } else if (tipoConta.equalsIgnoreCase("POUPANCA")) {
        contaPoupancaDAO.inserirContaPoupanca(conta.getIdConta());
      } else {
        throw new IllegalArgumentException("Tipo de conta inválido: " + tipoConta);
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Conta buscarContaPorNumero(String numeroConta) {
    try {
      ContaDAO contaDAO = new ContaDAO();
      return contaDAO.buscarContaPorNumero(numeroConta);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao buscar conta: " + e.getMessage(), e);
    }
  }

  public boolean encerrarConta(String numeroConta) {
    try {
      ContaDAO contaDAO = new ContaDAO();
      Conta conta = contaDAO.buscarContaPorNumero(numeroConta);
      if (conta == null) {
        throw new IllegalArgumentException("Conta não encontrada.");
      }
      if (conta.getStatus() == null || !conta.getStatus().name().equals("ATIVA")) {
        throw new IllegalArgumentException("A conta não está ativa e não pode ser encerrada.");
      }
      if (conta.getSaldo().compareTo(java.math.BigDecimal.ZERO) > 0) {
        throw new IllegalArgumentException("A conta possui saldo. Zere o saldo antes de encerrar.");
      }
      // Atualiza status para ENCERRADA
      boolean atualizado = contaDAO.atualizarStatusConta(numeroConta, "ENCERRADA");
      if (!atualizado) {
        throw new RuntimeException("Falha ao atualizar status da conta.");
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public List<Conta> buscarContasPorIdUsuario(int idUsuario) {
    ContaDAO contaDAO = new ContaDAO();
    return contaDAO.buscarContasPorIdUsuario(idUsuario);
  }

  public void realizarDeposito(int idConta, double valor, String descricao) {
    ContaDAO contaDAO = new ContaDAO();
    contaDAO.realizarDeposito(idConta, valor, descricao);
  }

  public void realizarSaque(int idConta, double valor, String descricao) {
    ContaDAO contaDAO = new ContaDAO();
    contaDAO.realizarSaque(idConta, valor, descricao);
  }

  public List<String> buscarExtratoPorIdConta(int idConta) {
    ContaDAO contaDAO = new ContaDAO();
    return contaDAO.buscarTransacoesPorIdConta(idConta);
  }
}
