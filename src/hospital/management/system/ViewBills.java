package hospital.management.system;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ViewBills extends JFrame {

    private JTable billTable;
    private DefaultTableModel tableModel;

    ViewBills() {
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

        // Ambulance image
        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icon/amb.png"));
        Image image1 = i11.getImage().getScaledInstance(300, 100, Image.SCALE_DEFAULT);
        ImageIcon i22 = new ImageIcon(image1);
        JLabel label1 = new JLabel(i22);
        label1.setBounds(1000, 50, 300, 100);
        panel1.add(label1);

        // Title
        JLabel title = new JLabel("Bill Records");
        title.setBounds(30, 15, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel1.add(title);

        // Table setup
        String[] columnNames = {"Bill ID", "Patient ID", "Patient Name", "Treatment Cost",
                "Medicine Cost", "Room Cost", "Total Amount", "Bill Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(billTable);
        scrollPane.setBounds(50, 20, 1425, 600);
        panel.add(scrollPane);

        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(30, 58, 200, 30);
        backBtn.setBackground(new Color(246, 215, 118));
        panel1.add(backBtn);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                String fetchedName = "";
                new Reception(fetchedName);
            }
        });

        // Load bill data
        loadBillData();

        // Frame setup
        setSize(1950, 1090);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);
        setVisible(true);
    }

    private void loadBillData() {
        try {
            conn c = new conn();  // Using your existing connection class
            ResultSet rs = c.statement.executeQuery("SELECT * FROM bills");

            // Clear existing data
            tableModel.setRowCount(0);

            // Add rows to table model
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("bill_id"),
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getDouble("treatment_cost"),
                        rs.getDouble("medicine_cost"),
                        rs.getDouble("room_cost"),
                        rs.getDouble("total_amount"),
                        rs.getString("bill_date")
                };
                tableModel.addRow(row);
            }

            rs.close();
            // Note: Assuming conn class handles connection closing

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading bills: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewBills();
    }
}
