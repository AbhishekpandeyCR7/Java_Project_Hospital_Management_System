package hospital.management.system;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagement extends JFrame {
    private final JTable inventoryTable;
    private final DefaultTableModel tableModel;

    InventoryManagement() {
        // Frame title
        setTitle("Inventory Management");

        // Frame settings
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Table to display inventory
        String[] columns = {"Item Name", "Quantity", "Minimum Required"};
        tableModel = new DefaultTableModel(columns, 0);
        inventoryTable = new JTable(tableModel);

        // Add some initial items
        addDefaultItems();

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(40, 80, 120), 2));
        add(scrollPane, BorderLayout.CENTER);

        // Add Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton addItemButton = new JButton("Add Item");
        JButton alertButton = new JButton("Low Inventory Alert");
        buttonPanel.add(addItemButton);
        buttonPanel.add(alertButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add item functionality
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = JOptionPane.showInputDialog("Enter Item Name:");
                String quantity = JOptionPane.showInputDialog("Enter Quantity:");
                String minRequired = JOptionPane.showInputDialog("Enter Minimum Required Quantity:");

                if (itemName != null && quantity != null && minRequired != null) {
                    tableModel.addRow(new Object[]{itemName, Integer.parseInt(quantity), Integer.parseInt(minRequired)});
                }
            }
        });

        // Low inventory alert functionality
        alertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder alertMessage = new StringBuilder("Low Inventory Items:\n");
                boolean lowInventoryFound = false;

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                    int minRequired = Integer.parseInt(tableModel.getValueAt(i, 2).toString());

                    if (quantity < minRequired) {
                        lowInventoryFound = true;
                        alertMessage.append(tableModel.getValueAt(i, 0)).append(" (Quantity: ").append(quantity).append(")\n");
                    }
                }

                if (lowInventoryFound) {
                    JOptionPane.showMessageDialog(null, alertMessage.toString(), "Low Inventory Alert", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "All inventory levels are sufficient.", "Inventory Check", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    // Method to add default items to the inventory
    private void addDefaultItems() {
        Object[][] defaultItems = {
                {"Surgical Masks", 100, 50},
                {"Hand Sanitizers", 60, 30},
                {"Gloves", 200, 100},
                {"Thermometers", 15, 10},
                {"Syringes", 500, 300},
                {"IV Fluids", 40, 20},
                {"Bandages", 150, 50},
                {"Oxygen Cylinders", 10, 5}
        };

        for (Object[] item : defaultItems) {
            tableModel.addRow(item);
        }
    }

    public static void main(String[] args) {
        new InventoryManagement();
    }
}
