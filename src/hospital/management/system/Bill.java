package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill extends JFrame {

    private JTextField patientIdField, patientNameField, treatmentCostField, medicineCostField, roomCostField;
    private JLabel totalLabel;
    private double totalAmount;

    Bill() {
        // Main panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(5, 160, 1525, 670);
        panel.setBackground(new Color(109, 164, 170));
        add(panel);

        // Header panel setup
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(5, 5, 1525, 150);
        panel1.setBackground(new Color(109, 164, 170));
        add(panel1);

        // Hospital logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/dr.png"));
        Image image = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(image);
        JLabel label = new JLabel(i2);
        label.setBounds(1300, 0, 250, 250);
        panel1.add(label);

        // Title
        JLabel title = new JLabel("Patient Billing");
        title.setBounds(30, 15, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel1.add(title);

        // Date
        JLabel dateLabel = new JLabel("Date: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        dateLabel.setBounds(30, 50, 200, 30);
        dateLabel.setForeground(Color.WHITE);
        panel1.add(dateLabel);

        // Patient Information Fields
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setBounds(50, 20, 100, 30);
        panel.add(patientIdLabel);

        patientIdField = new JTextField();
        patientIdField.setBounds(150, 20, 200, 30);
        panel.add(patientIdField);

        JLabel patientNameLabel = new JLabel("Patient Name:");
        patientNameLabel.setBounds(50, 60, 100, 30);
        panel.add(patientNameLabel);

        patientNameField = new JTextField();
        patientNameField.setBounds(150, 60, 200, 30);
        panel.add(patientNameField);

        // Cost Fields
        JLabel treatmentCostLabel = new JLabel("Treatment Cost:");
        treatmentCostLabel.setBounds(50, 100, 100, 30);
        panel.add(treatmentCostLabel);

        treatmentCostField = new JTextField("0.0");
        treatmentCostField.setBounds(150, 100, 200, 30);
        panel.add(treatmentCostField);

        JLabel medicineCostLabel = new JLabel("Medicine Cost:");
        medicineCostLabel.setBounds(50, 140, 100, 30);
        panel.add(medicineCostLabel);

        medicineCostField = new JTextField("0.0");
        medicineCostField.setBounds(150, 140, 200, 30);
        panel.add(medicineCostField);

        JLabel roomCostLabel = new JLabel("Room Cost:");
        roomCostLabel.setBounds(50, 180, 100, 30);
        panel.add(roomCostLabel);

        roomCostField = new JTextField("0.0");
        roomCostField.setBounds(150, 180, 200, 30);
        panel.add(roomCostField);

        // Total Display
        JLabel totalTextLabel = new JLabel("Total Amount:");
        totalTextLabel.setBounds(50, 220, 100, 30);
        panel.add(totalTextLabel);

        totalLabel = new JLabel("0.0");
        totalLabel.setBounds(150, 220, 200, 30);
        totalLabel.setForeground(Color.RED);
        panel.add(totalLabel);

        // Buttons
        JButton calculateBtn = new JButton("Calculate Total");
        calculateBtn.setBounds(50, 260, 150, 30);
        calculateBtn.setBackground(new Color(246, 215, 118));
        panel.add(calculateBtn);
        calculateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotal();
            }
        });

        JButton saveBtn = new JButton("Save Bill");
        saveBtn.setBounds(220, 260, 150, 30);
        saveBtn.setBackground(new Color(246, 215, 118));
        panel.add(saveBtn);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBill();
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(390, 260, 150, 30);
        backBtn.setBackground(new Color(246, 215, 118));
        panel.add(backBtn);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                String fetchedName = "";
                new Reception(fetchedName);
            }
        });

        // Frame setup
        setSize(1950, 1090);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);
        setVisible(true);
    }

    private void calculateTotal() {
        try {
            double treatment = Double.parseDouble(treatmentCostField.getText());
            double medicine = Double.parseDouble(medicineCostField.getText());
            double room = Double.parseDouble(roomCostField.getText());

            totalAmount = treatment + medicine + room;
            totalLabel.setText(String.format("%.2f", totalAmount));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for costs");
        }
    }

    private void saveBill() {
        String patientId = patientIdField.getText();
        String patientName = patientNameField.getText();

        if (patientId.isEmpty() || patientName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in patient details");
            return;
        }

        if (totalAmount == 0) {
            JOptionPane.showMessageDialog(this, "Please calculate total first");
            return;
        }

        try {
            conn c = new conn();  // Using your existing connection class

            String query = "INSERT INTO bills (patient_id, patient_name, treatment_cost, medicine_cost, room_cost, total_amount, bill_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = c.connection.prepareStatement(query);

            pstmt.setString(1, patientId);
            pstmt.setString(2, patientName);
            pstmt.setDouble(3, Double.parseDouble(treatmentCostField.getText()));
            pstmt.setDouble(4, Double.parseDouble(medicineCostField.getText()));
            pstmt.setDouble(5, Double.parseDouble(roomCostField.getText()));
            pstmt.setDouble(6, totalAmount);
            pstmt.setString(7, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                        "Bill Saved Successfully!\n" +
                                "Patient ID: " + patientId + "\n" +
                                "Patient Name: " + patientName + "\n" +
                                "Total Amount: " + String.format("%.2f", totalAmount));

                // Clear fields
                clearFields();

                // Ask if user wants to view all bills
                int response = JOptionPane.showConfirmDialog(this,
                        "Would you like to view all bills now?",
                        "View Bills",
                        JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    new ViewBills();
                }
            }

            pstmt.close();
            // Note: Assuming conn class handles connection closing

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving bill: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cost values");
        }
    }

    private void clearFields() {
        patientIdField.setText("");
        patientNameField.setText("");
        treatmentCostField.setText("0.0");
        medicineCostField.setText("0.0");
        roomCostField.setText("0.0");
        totalLabel.setText("0.0");
        totalAmount = 0.0;
    }

    public static void main(String[] args) {
        new Bill();
    }
}
