/**
 * Classe responsável pela interface gráfica do menu principal do sistema Banco Malvader.
 *
 * <p>Permite a navegação para as diferentes funcionalidades do sistema, como gerenciamento de
 * clientes, contas e funcionários.
 *
 */
package com.bancomalvader.View;

import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import javax.swing.*;

public final class MainMenuView extends JFrame {

  public MainMenuView() {
    // Configuração da janela principal
    setTitle("Home");
    setSize(800, 481); // Tamanho exato conforme especificado
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centraliza a janela na tela
    setLayout(new BorderLayout());
    getContentPane().setBackground(new Color(30, 30, 30)); // Fundo cinza escuro da janela

    // Cabeçalho com fundo preto e título "MVDR"
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
    headerLabel.setForeground(new Color(255, 69, 0)); // Vermelho para "MVDR"
    headerPanel.setLayout(new BorderLayout());
    headerPanel.add(headerLabel, BorderLayout.CENTER);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central com título e botões
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(new Color(30, 30, 30)); // Mesmo fundo da janela
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

    // Fundo cinza claro para o título e os botões
    JPanel containerPanel = new JPanel();
    containerPanel.setBackground(new Color(45, 45, 45)); // Fundo cinza do container
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

    JLabel loginLabel = new JLabel("Escolha método de login", SwingConstants.CENTER);
    loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
    loginLabel.setForeground(Color.WHITE);
    loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    loginLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    containerPanel.add(loginLabel);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço entre título e botões

    // Configurar tamanho dos botões
    Dimension buttonSize = new Dimension(286, 45);
    Dimension exitButtonSize = new Dimension(94, 45);

    // Botões de login
    JButton clienteButton =
        new RoundedButton("Cliente", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    clienteButton.setMaximumSize(buttonSize);
    clienteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton funcionarioButton =
        new RoundedButton("Funcionário", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    funcionarioButton.setMaximumSize(buttonSize);
    funcionarioButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Adicionar ações aos botões
    clienteButton.addActionListener(
        e -> {
          // Redireciona para a LoginView específica do Cliente
          LoginView loginView = new LoginView("Cliente");
          loginView.setVisible(true);
          dispose(); // Fecha a tela atual
        });

    funcionarioButton.addActionListener(
        e -> {
          // Redireciona para a LoginView específica do Funcionário
          LoginView loginView = new LoginView("Funcionário");
          loginView.setVisible(true);
          dispose(); // Fecha a tela atual
        });

    containerPanel.add(clienteButton);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre os botões
    containerPanel.add(funcionarioButton);

    centerPanel.add(containerPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Botão "Sair" fora do containerPanel
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
    footerPanel.setBackground(new Color(30, 30, 30));

    // Adicionar botão "Sair" logo abaixo do containerPanel
    RoundedButton exitButton =
        new RoundedButton("Sair", new Color(109, 6, 6, 25), new Color(161, 61, 61));
    exitButton.setForeground(new Color(161, 61, 61));
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitButton.setMaximumSize(exitButtonSize);

    exitButton.addActionListener(
        e -> {
          dispose();
        });

    centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    centerPanel.add(exitButton);

    add(centerPanel, BorderLayout.CENTER);
  }
}
