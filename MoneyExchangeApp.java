package CodeSoft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.DecimalFormat;

public class MoneyExchangeApp extends JFrame implements ActionListener {

    private JLabel titleLabel;
    private JLabel amountLabel;
    private JLabel fromLabel;
    private JLabel toLabel;

    private JTextField amountField;

    private JComboBox<String> fromBox;
    private JComboBox<String> toBox;

    private JButton convertButton;
    private JButton clearButton;

    private JTextArea outputArea;

    private final String[] currencies = {
            "USD", "INR", "EUR", "GBP",
            "JPY", "AUD", "CAD", "CHF", "CNY"
    };

    public MoneyExchangeApp() {

        setTitle("Currency Converter");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        titleLabel = new JLabel(
                "LIVE CURRENCY CONVERTER",
                SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22));

        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        amountLabel = new JLabel("Amount");
        amountField = new JTextField();

        fromLabel = new JLabel("From Currency");
        fromBox = new JComboBox<>(currencies);

        toLabel = new JLabel("To Currency");
        toBox = new JComboBox<>(currencies);

        toBox.setSelectedItem("INR");

        convertButton = new JButton("Convert");
        clearButton = new JButton("Clear");

        convertButton.addActionListener(this);
        clearButton.addActionListener(this);

        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        inputPanel.add(fromLabel);
        inputPanel.add(fromBox);

        inputPanel.add(toLabel);
        inputPanel.add(toBox);

        inputPanel.add(convertButton);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14));

        add(new JScrollPane(outputArea),
                BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == convertButton) {
            convertCurrency();
        }

        if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void convertCurrency() {

        try {

            double amount =
                    Double.parseDouble(amountField.getText());

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than zero");

                return;
            }

            String from =
                    fromBox.getSelectedItem().toString();

            String to =
                    toBox.getSelectedItem().toString();

            if (from.equals(to)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select different currencies");

                return;
            }

            convertButton.setEnabled(false);
            outputArea.setText("Fetching live exchange rate...");

            new SwingWorker<Void, Void>() {

                double rate;
                double converted;

                @Override
                protected Void doInBackground()
                        throws Exception {

                    rate = getExchangeRate(from, to);
                    converted = amount * rate;

                    return null;
                }

                @Override
                protected void done() {

                    try {

                        get();

                        DecimalFormat df =
                                new DecimalFormat("#,##0.00");

                        String result =
                                "====================================\n" +
                                "       CURRENCY CONVERSION\n" +
                                "====================================\n\n" +
                                "From Currency : " + from + "\n" +
                                "To Currency   : " + to + "\n" +
                                "Exchange Rate : " + df.format(rate) + "\n\n" +
                                "Entered Amount   : " + df.format(amount) + "\n" +
                                "Converted Amount : " + df.format(converted);

                        outputArea.setText(result);
                    }

                    catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                MoneyExchangeApp.this,
                                "Unable to fetch exchange rates");
                    }

                    convertButton.setEnabled(true);
                }
            }.execute();
        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid numeric amount");
        }
    }

    private double getExchangeRate(String from,
                                   String to)
            throws Exception {

        String urlString =
                "https://open.er-api.com/v6/latest/" + from;

        URL url = new URL(urlString);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != 200) {
            throw new IOException();
        }

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        String json = response.toString();

        String key = "\"" + to + "\":";

        int start = json.indexOf(key);

        if (start == -1) {
            throw new Exception("Currency not found");
        }

        start += key.length();

        int end = json.indexOf(",", start);

        return Double.parseDouble(
                json.substring(start, end));
    }

    private void clearFields() {

        amountField.setText("");
        outputArea.setText("");

        fromBox.setSelectedIndex(0);
        toBox.setSelectedItem("INR");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                MoneyExchangeApp::new);
    }
}
