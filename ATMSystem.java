package CodeSoft;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMSystem extends JFrame implements ActionListener {

    private JTextField amountField;
    private JTextArea historyArea;
    private JLabel balanceLabel;

    private JButton depositBtn;
    private JButton withdrawBtn;
    private JButton balanceBtn;
    private JButton exitBtn;

    private UserBank bank;

    public ATMSystem() {

        bank = new UserBank();

        setTitle("ATM System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Enter Amount:"));

        amountField = new JTextField(15);
        topPanel.add(amountField);

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        balanceBtn = new JButton("Check Balance");
        exitBtn = new JButton("Exit");

        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        balanceBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        balanceLabel = new JLabel(
                "Current Balance : Rs. " + bank.getBalance(),
                SwingConstants.CENTER);

        bottomPanel.add(balanceLabel, BorderLayout.NORTH);

        historyArea = new JTextArea();
        historyArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(historyArea);

        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private double getAmount() {

        if (amountField.getText().trim().isEmpty()) {
            throw new NumberFormatException();
        }

        double amount = Double.parseDouble(amountField.getText());

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than 0");
        }

        return amount;
    }

    private void updateBalance() {
        balanceLabel.setText(
                "Current Balance : Rs. " + bank.getBalance());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            if (e.getSource() == depositBtn) {

                double amount = getAmount();

                bank.addMoney(amount);

                updateBalance();

                historyArea.append(
                        "Deposited : Rs. " + amount + "\n");

                amountField.setText("");
            }

            else if (e.getSource() == withdrawBtn) {

                double amount = getAmount();

                if (bank.takeMoney(amount)) {

                    updateBalance();

                    historyArea.append(
                            "Withdrawn : Rs. " + amount + "\n");
                }

                else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Insufficient Balance");
                }

                amountField.setText("");
            }

            else if (e.getSource() == balanceBtn) {

                historyArea.append(
                        "Balance : Rs. " +
                                bank.getBalance() + "\n");
            }

            else if (e.getSource() == exitBtn) {

                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to exit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number");
        }

        catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMSystem::new);
    }
}