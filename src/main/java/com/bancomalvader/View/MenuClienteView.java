/**
 * Classe responsável pela interface gráfica do menu de operações para clientes no sistema Banco
 * Malvader.
 *
 * <p>Permite acesso a funcionalidades específicas voltadas para clientes, como consulta de contas,
 * realização de operações bancárias e atualização de dados pessoais.
 *
 */
package com.bancomalvader.View;

import com.bancomalvader.Model.Usuario;
import com.bancomalvader.Util.RoundedButton;
import com.bancomalvader.Controller.ContaController;
import com.bancomalvader.Model.Conta;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class MenuClienteView extends JFrame {

  public MenuClienteView(Usuario usuario) {
    setTitle("Bem-vindo, " + usuario.getNome());
    setSize(800, 927);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
    headerLabel.setForeground(new Color(255, 69, 0));
    headerPanel.add(headerLabel);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(new Color(45, 45, 45));
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    JLabel welcomeLabel = new JLabel("Bem-vindo, " + usuario.getNome() + "!");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
    welcomeLabel.setForeground(Color.WHITE);
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    centerPanel.add(welcomeLabel);

    centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Botões para funcionalidades de cliente
    String[] actions = {"Saldo", "Depósito", "Saque", "Extrato", "Consultar Limite", "Sair"};

    for (String action : actions) {
      boolean isExitButton = action.equalsIgnoreCase("Sair");
      RoundedButton button =
          new RoundedButton(
              action, isExitButton ? new Color(109, 6, 6) : new Color(240, 66, 11), Color.WHITE);
      button.setPreferredSize(isExitButton ? new Dimension(94, 45) : new Dimension(262, 45));
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setPreferredSize(new Dimension(170, 45));

      // Adicione ação ao botão
      button.addActionListener(e -> handleButtonClick(action, usuario));
      centerPanel.add(button);
      centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    add(centerPanel, BorderLayout.CENTER);
  }

  private void handleButtonClick(String action, Usuario usuario) {
    switch (action) {
      case "Saldo":
        ContaController contaController = new ContaController();
        List<Conta> contas = contaController.buscarContasPorIdUsuario(usuario.getId());
        if (contas.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Nenhuma conta encontrada para este usuário.");
        } else if (contas.size() == 1) {
          Conta conta = contas.get(0);
          JOptionPane.showMessageDialog(this, "Saldo da conta " + conta.getNumeroConta() + ": R$ " + String.format("%.2f", conta.getSaldo()));
        } else {
          // Mais de uma conta: pedir para escolher
          String[] opcoes = contas.stream().map(Conta::getNumeroConta).toArray(String[]::new);
          String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o número da conta:",
            "Escolher Conta",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
          );
          if (escolha != null) {
            Conta contaEscolhida = contas.stream().filter(c -> c.getNumeroConta().equals(escolha)).findFirst().orElse(null);
            if (contaEscolhida != null) {
              JOptionPane.showMessageDialog(this, "Saldo da conta " + contaEscolhida.getNumeroConta() + ": R$ " + String.format("%.2f", contaEscolhida.getSaldo()));
            }
          }
        }
        break;
      case "Depósito":
        ContaController contaControllerDeposito = new ContaController();
        List<Conta> contasDeposito = contaControllerDeposito.buscarContasPorIdUsuario(usuario.getId());
        if (contasDeposito.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Nenhuma conta encontrada para este usuário.");
        } else {
          Conta conta = contasDeposito.get(0);
          String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do depósito:", "Depósito", JOptionPane.PLAIN_MESSAGE);
          if (valorStr != null) {
            try {
              double valor = Double.parseDouble(valorStr.replace(",", "."));
              if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser positivo.");
                return;
              }
              contaControllerDeposito.realizarDeposito(conta.getIdConta(), valor, "Depósito realizado pelo cliente");
              JOptionPane.showMessageDialog(this, "Depósito de R$ " + String.format("%.2f", valor) + " realizado com sucesso!");
            } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(this, "Valor inválido.");
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(this, "Erro ao realizar depósito: " + ex.getMessage());
            }
          }
        }
        break;
      case "Saque":
        ContaController contaControllerSaque = new ContaController();
        List<Conta> contasSaque = contaControllerSaque.buscarContasPorIdUsuario(usuario.getId());
        if (contasSaque.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Nenhuma conta encontrada para este usuário.");
        } else {
          Conta conta = contasSaque.get(0);
          String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do saque:", "Saque", JOptionPane.PLAIN_MESSAGE);
          if (valorStr != null) {
            try {
              double valor = Double.parseDouble(valorStr.replace(",", "."));
              double saldo = conta.getSaldo().doubleValue();
              if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser positivo.");
                return;
              }
              if (valor > saldo) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente.");
                return;
              }
              contaControllerSaque.realizarSaque(conta.getIdConta(), valor, "Saque realizado pelo cliente");
              JOptionPane.showMessageDialog(this, "Saque de R$ " + String.format("%.2f", valor) + " realizado com sucesso!");
            } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(this, "Valor inválido.");
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(this, "Erro ao realizar saque: " + ex.getMessage());
            }
          }
        }
        break;
      case "Extrato":
        ContaController contaControllerExtrato = new ContaController();
        List<Conta> contasExtrato = contaControllerExtrato.buscarContasPorIdUsuario(usuario.getId());
        if (contasExtrato.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Nenhuma conta encontrada para este usuário.");
        } else {
          Conta conta = contasExtrato.get(0);
          List<String> extrato = contaControllerExtrato.buscarExtratoPorIdConta(conta.getIdConta());
          if (extrato.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma transação encontrada para esta conta.");
          } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Data/Hora | Tipo | Valor | Descrição\n");
            sb.append("---------------------------------------------\n");
            for (String linha : extrato) {
              sb.append(linha).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Extrato da Conta", JOptionPane.INFORMATION_MESSAGE);
          }
        }
        break;
      case "Consultar Limite":
        ContaController contaControllerLimite = new ContaController();
        List<Conta> contasLimite = contaControllerLimite.buscarContasPorIdUsuario(usuario.getId());
        if (contasLimite.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Nenhuma conta encontrada para este usuário.");
        } else {
          Conta conta = contasLimite.get(0);
          if ("CORRENTE".equalsIgnoreCase(conta.getTipoConta())) {
            JOptionPane.showMessageDialog(this, 
              "Limite disponível: R$ " + String.format("%.2f", conta.getLimite()) + "\n" +
              "Data de vencimento: " + conta.getDataVencimento());
          } else {
            JOptionPane.showMessageDialog(this, "Apenas contas correntes possuem limite disponível.");
          }
        }
        break;
      case "Sair":
        JOptionPane.showMessageDialog(this, "Saindo...");
        dispose();
        new LoginView("Cliente").setVisible(true);
        break;
      default:
        JOptionPane.showMessageDialog(this, "Ação desconhecida: " + action);
    }
  }
}
