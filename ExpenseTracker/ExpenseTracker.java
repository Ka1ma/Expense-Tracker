import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ExpenseTracker extends JFrame {
    private List<Expense> expenses;
    private DefaultTableModel tableModel;
    private JTextField categoryField;
    private JTextField descriptionField;
    private JTextField amountField;
    private JLabel totalLabel;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Expense Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Modern UI Color Scheme
        Color bgColor = new Color(245, 245, 245);
        Color fgColor = new Color(51, 51, 51);
        Color accentColor = new Color(0, 120, 215);

        // Panel for inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Category Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        categoryField = new JTextField(15);
        inputPanel.add(categoryField, gbc);

        // Description Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        descriptionField = new JTextField(15);
        inputPanel.add(descriptionField, gbc);

        // Amount Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        amountField = new JTextField(15);
        inputPanel.add(amountField, gbc);

        // Add Expense Button
        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(accentColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addButton.addActionListener(new AddExpenseActionListener());

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Table for displaying expenses
        tableModel = new DefaultTableModel(new String[]{"Category", "Description", "Amount"}, 0);
        JTable expenseTable = new JTable(tableModel);
        expenseTable.setFillsViewportHeight(true);
        expenseTable.setShowGrid(false);
        expenseTable.setIntercellSpacing(new Dimension(0, 0));
        expenseTable.setRowHeight(30);
        expenseTable.getTableHeader().setReorderingAllowed(false);
        expenseTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);

        // Panel for total expenses
        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(bgColor);
        totalLabel = new JLabel("Total Expenses: $0.00");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalPanel.add(totalLabel);

        add(totalPanel, BorderLayout.SOUTH);
    }

    private class AddExpenseActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = categoryField.getText();
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());

            Expense expense = new Expense(category, description, amount);
            expenses.add(expense);
            tableModel.addRow(new Object[]{category, description, amount});
            updateTotal();

            categoryField.setText("");
            descriptionField.setText("");
            amountField.setText("");
        }
    }

    private void updateTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        totalLabel.setText("Total Expenses: $" + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTracker tracker = new ExpenseTracker();
            tracker.setVisible(true);
        });
    }
}