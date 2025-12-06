//package hospital.management.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import net.proteanit.sql.DbUtils;
//
//class PatientDashboard extends JFrame {
//    private int patientId;
//
//    PatientDashboard(String patientName) {
//
//        setTitle("Patient Dashboard");
//        setSize(800, 400);
//        setLocation(400, 270);
//        getContentPane().setBackground(new Color(109, 164, 170));
//        setLayout(null);
//
//        JLabel welcomeLabel = new JLabel("Welcome Patient: " + patientName);
//        welcomeLabel.setBounds(40, 20, 300, 30);
//        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
//        welcomeLabel.setForeground(Color.BLACK);
//        add(welcomeLabel);
//
//        ImageIcon patientIcon = new ImageIcon(ClassLoader.getSystemResource("icon/patient.png"));
//        Image scaledImage = patientIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
//        imageLabel.setBounds(650, 20, 100, 100);
//        add(imageLabel);
//
//        JButton viewBookingsBtn = new JButton("View Bookings");
//        viewBookingsBtn.setBounds(40, 80, 150, 30);
//        viewBookingsBtn.setFont(new Font("serif", Font.BOLD, 15));
//        viewBookingsBtn.setBackground(Color.BLACK);
//        viewBookingsBtn.setForeground(Color.WHITE);
//        viewBookingsBtn.addActionListener(e -> viewBookings());
//        add(viewBookingsBtn);
//
//        JButton viewDetailsBtn = new JButton("View My Details");
//        viewDetailsBtn.setBounds(200, 80, 150, 30);
//        viewDetailsBtn.setFont(new Font("serif", Font.BOLD, 15));
//        viewDetailsBtn.setBackground(Color.BLACK);
//        viewDetailsBtn.setForeground(Color.WHITE);
//        viewDetailsBtn.addActionListener(e -> viewDetails());
//        add(viewDetailsBtn);
//
//        JButton logoutBtn = new JButton("Logout");
//        logoutBtn.setBounds(360, 80, 100, 30);
//        logoutBtn.setFont(new Font("serif", Font.BOLD, 15));
//        logoutBtn.setBackground(Color.BLACK);
//        logoutBtn.setForeground(Color.WHITE);
//        logoutBtn.addActionListener(e -> {
//            setVisible(false);
//            new Login();
//        });
//        add(logoutBtn);
//
//        setVisible(true);
//    }
//
//    private void viewBookings() {
//        try {
//            conn c = new conn();
//            String q = "SELECT b.BookingID, d.Name AS DoctorName, b.BookingDate, b.Status " +
//                    "FROM Bookings b JOIN doctors d ON b.DoctorID = d.DoctorID " +
//                    "WHERE b.PatientID = " + patientId;
//            ResultSet rs = c.statement.executeQuery(q);
//            JTable table = new JTable(DbUtils.resultSetToTableModel(rs));
//            showTableDialog(table);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error retrieving bookings: " + ex.getMessage());
//        }
//    }
//
//    private void viewDetails() {
//        try {
//            conn c = new conn();
//            String q = "SELECT Name, Gender, Disease, Room_No, Admit_Time, Deposit " +
//                    "FROM Patient_Info WHERE PatientID = " + patientId;
//            ResultSet rs = c.statement.executeQuery(q);
//            if (rs.next()) {
//                String details = "Name: " + rs.getString("Name") + "\n" +
//                        "Gender: " + rs.getString("Gender") + "\n" +
//                        "Disease: " + rs.getString("Disease") + "\n" +
//                        "Room: " + rs.getString("Room_No") + "\n" +
//                        "Admit Time: " + rs.getString("Admit_Time") + "\n" +
//                        "Deposit: " + rs.getString("Deposit");
//                JOptionPane.showMessageDialog(null, details, "My Details", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "No details found");
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error retrieving details: " + ex.getMessage());
//        }
//    }
//
//    private void showTableDialog(JTable table) {
//        JDialog dialog = new JDialog(this, "My Bookings", true);
//        dialog.add(new JScrollPane(table));
//        dialog.setSize(600, 400);
//        dialog.setLocationRelativeTo(this);
//        dialog.setVisible(true);
//    }
//}
package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;

public class PatientDashboard extends JFrame {
    private String patientName;
    private int patientId;

    public PatientDashboard(String patientName, int patientId) {
        // Security check: Verify patientId and name against database
        if (!isValidUser(patientId, patientName)) {
            JOptionPane.showMessageDialog(null, "Unauthorized access! Please log in.");
            setVisible(false);
            new Login();
            return;
        }

        this.patientName = patientName;
        this.patientId = patientId;
        System.out.println("PatientDashboard initialized - Name: " + patientName + ", PatientID: " + patientId); // Debug

        setTitle("Patient Dashboard");
        setSize(800, 400);
        setLocation(400, 270);
        getContentPane().setBackground(new Color(109, 164, 170));
        setLayout(null);

        // Welcome Label (Centered at top)
        JLabel welcomeLabel = new JLabel("Welcome Patient: " + patientName);
        welcomeLabel.setBounds(300, 20, 300, 30);
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.BLACK);
        add(welcomeLabel);

        // Image (Full-size on the right)
        ImageIcon patientIcon;
        try {
            patientIcon = new ImageIcon(ClassLoader.getSystemResource("icon/patient.png"));
            if (patientIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image not loaded");
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not load patient.png - using default icon");
            patientIcon = new ImageIcon();
        }
        Image scaledImage = patientIcon.getImage().getScaledInstance(200, 350, Image.SCALE_SMOOTH); // Full-size: 200x350
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(550, 50, 200, 350); // Right side, full height
        add(imageLabel);

        // Buttons (Aligned vertically on the left)
        JButton viewBookingsBtn = new JButton("View Bookings");
        viewBookingsBtn.setBounds(40, 80, 150, 30);
        viewBookingsBtn.setFont(new Font("serif", Font.BOLD, 15));
        viewBookingsBtn.setBackground(Color.BLACK);
        viewBookingsBtn.setForeground(Color.WHITE);
        viewBookingsBtn.addActionListener(e -> viewBookings());
        add(viewBookingsBtn);

        JButton viewDetailsBtn = new JButton("View My Details");
        viewDetailsBtn.setBounds(40, 130, 150, 30);
        viewDetailsBtn.setFont(new Font("serif", Font.BOLD, 15));
        viewDetailsBtn.setBackground(Color.BLACK);
        viewDetailsBtn.setForeground(Color.WHITE);
        viewDetailsBtn.addActionListener(e -> viewDetails());
        add(viewDetailsBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(40, 180, 150, 30);
        logoutBtn.setFont(new Font("serif", Font.BOLD, 15));
        logoutBtn.setBackground(Color.BLACK);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(e -> {
            setVisible(false);
            new Login();
        });
        add(logoutBtn);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Security method to validate user
    private boolean isValidUser(int patientId, String patientName) {
        try {
            conn c = new conn();
            if (c.connection == null) return false;
            String q = "SELECT PatientID, Name FROM patients WHERE PatientID = ? AND Name = ?";
            java.sql.PreparedStatement ps = c.connection.prepareStatement(q);
            ps.setInt(1, patientId);
            ps.setString(2, patientName);
            ResultSet rs = ps.executeQuery();
            boolean valid = rs.next(); // True if a matching record exists
            rs.close();
            ps.close();
            return valid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void viewBookings() {
        try {
            conn c = new conn();
            if (c.connection == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed");
                return;
            }
            String q = "SELECT b.BookingID, d.Name AS DoctorName, b.BookingDate, b.Status " +
                    "FROM Bookings b JOIN doctors d ON b.DoctorID = d.DoctorID " +
                    "WHERE b.PatientID = " + patientId;
            System.out.println("PatientDashboard Query: " + q);
            ResultSet rs = c.statement.executeQuery(q);
            if (!rs.isBeforeFirst()) {
                System.out.println("No bookings found for PatientID: " + patientId);
            }
            JTable table = new JTable(DbUtils.resultSetToTableModel(rs));
            showTableDialog(table, "My Bookings");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving bookings: " + ex.getMessage());
        }
    }

    private void viewDetails() {
        try {
            conn c = new conn();
            if (c.connection == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed");
                return;
            }
            String q = "SELECT Name, Gender, Disease, Room_No, Admit_Time, Deposit " +
                    "FROM Patient_Info WHERE PatientID = " + patientId;
            System.out.println("PatientDashboard Query: " + q);
            ResultSet rs = c.statement.executeQuery(q);
            if (rs.next()) {
                String details = "Name: " + rs.getString("Name") + "\n" +
                        "Gender: " + (rs.getString("Gender") != null ? rs.getString("Gender") : "N/A") + "\n" +
                        "Disease: " + (rs.getString("Disease") != null ? rs.getString("Disease") : "N/A") + "\n" +
                        "Room: " + (rs.getString("Room_No") != null ? rs.getString("Room_No") : "N/A") + "\n" +
                        "Admit Time: " + (rs.getString("Admit_Time") != null ? rs.getString("Admit_Time") : "N/A") + "\n" +
                        "Deposit: " + (rs.getString("Deposit") != null ? rs.getString("Deposit") : "N/A");
                JOptionPane.showMessageDialog(null, details, "My Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No additional details found in Patient_Info");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving details: " + ex.getMessage());
        }
    }

    private void showTableDialog(JTable table, String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.add(new JScrollPane(table));
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientDashboard("Jane Doe", 1)); // For testing only
    }
}