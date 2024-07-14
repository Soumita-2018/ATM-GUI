package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField pinField;
    private JTextField depositField;
    private JTextField withdrawField;
    private JLabel balanceLabel;

    private ATMService atmService;

    public ATMGUI() {
        atmService = new ATMService();

        setTitle("ATM");
        setSize(866, 586);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createPinPanel(), "PinPanel");
        mainPanel.add(createTransactionPanel(), "TransactionPanel");
        mainPanel.add(createDepositPanel(), "DepositPanel");
        mainPanel.add(createWithdrawPanel(), "WithdrawPanel");

        getContentPane().add(mainPanel);

        cardLayout.show(mainPanel, "PinPanel");

        setVisible(true);
    }

    private JPanel createPinPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(45, 89, 89));

        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setForeground(new Color(255, 255, 255));
        pinLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        pinLabel.setBounds(26, 199, 161, 68);
        panel.add(pinLabel);

        pinField = new JTextField(20);
        pinField.setFont(new Font("Times New Roman", Font.BOLD, 24));
        pinField.setBounds(182, 199, 611, 68);
        panel.add(pinField);

        JButton enterButton = new JButton("Submit");
        enterButton.setFont(new Font("Times New Roman", Font.BOLD, 23));
        enterButton.setBackground(UIManager.getColor("List.selectionBackground"));
        enterButton.setBounds(288, 373, 269, 84);
        panel.add(enterButton);
        
        JLabel lblNewLabel = new JLabel("Welcome in ATM System");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBackground(new Color(240, 240, 240));
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 29));
        lblNewLabel.setBounds(266, 48, 496, 47);
        panel.add(lblNewLabel);

        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pin = pinField.getText();
                if (atmService.validatePIN(pin)) {
                    cardLayout.show(mainPanel, "TransactionPanel");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid PIN");
                }
            }
        });

        return panel;
    }

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(null);

        JLabel optionsLabel = new JLabel("Select an option:");
        optionsLabel.setBounds(10, 20, 150, 25);
        panel.add(optionsLabel);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(10, 60, 100, 25);
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(10, 100, 100, 25);
        panel.add(withdrawButton);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setBounds(10, 140, 150, 25);
        panel.add(balanceButton);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "DepositPanel");
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "WithdrawPanel");
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double balance = atmService.checkBalance();
                JOptionPane.showMessageDialog(null, "Current balance: $" + balance);
            }
        });

        return panel;
    }

    private JPanel createDepositPanel() {
        JPanel panel = new JPanel(null);

        JLabel depositLabel = new JLabel("Enter amount to deposit:");
        depositLabel.setBounds(10, 20, 200, 25);
        panel.add(depositLabel);

        depositField = new JTextField(20);
        depositField.setBounds(10, 60, 165, 25);
        panel.add(depositField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(10, 100, 100, 25);
        panel.add(depositButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 100, 100, 25);
        panel.add(backButton);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(depositField.getText());
                atmService.deposit(amount);
                JOptionPane.showMessageDialog(null, "Deposited: $" + amount);
                cardLayout.show(mainPanel, "TransactionPanel");
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "TransactionPanel");
            }
        });

        return panel;
    }

    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(null);

        JLabel withdrawLabel = new JLabel("Enter amount to withdraw:");
        withdrawLabel.setBounds(10, 20, 200, 25);
        panel.add(withdrawLabel);

        withdrawField = new JTextField(20);
        withdrawField.setBounds(10, 60, 165, 25);
        panel.add(withdrawField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(10, 100, 100, 25);
        panel.add(withdrawButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 100, 100, 25);
        panel.add(backButton);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(withdrawField.getText());
                if (atmService.withdraw(amount)) {
                    JOptionPane.showMessageDialog(null, "Withdrew: $" + amount);
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance");
                }
                cardLayout.show(mainPanel, "TransactionPanel");
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "TransactionPanel");
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        new ATMGUI();
    }
}

