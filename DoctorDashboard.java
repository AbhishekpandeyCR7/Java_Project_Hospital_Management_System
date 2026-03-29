//package hospital.management.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import net.proteanit.sql.DbUtils;
//
//class DoctorDashboard extends JFrame {
//    private int doctorId;
//
//    DoctorDashboard(String doctorName) {
//
//        setTitle("Doctor Dashboard");
//        setSize(800, 400);
//        setLocation(400, 270);
//        getContentPane().setBackground(new Color(109, 164, 170));
//        setLayout(null);
//
//        JLabel welcomeLabel = new JLabel("Welcome Doctor: " + doctorName);
//        welcomeLabel.setBounds(40, 20, 300, 30);
//        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
//        welcomeLabel.setForeground(Color.BLACK);
//        add(welcomeLabel);
//
//        ImageIcon doctorIcon = new ImageIcon(ClassLoader.getSystemResource("icon/dr.png"));
//        Image scaledImage = doctorIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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
//        JButton viewPatientsBtn = new JButton("View Patients");
//        viewPatientsBtn.setBounds(200, 80, 150, 30);
//        viewPatientsBtn.setFont(new Font("serif", Font.BOLD, 15));
//        viewPatientsBtn.setBackground(Color.BLACK);
//        viewPatientsBtn.setForeground(Color.WHITE);
//        viewPatientsBtn.addActionListener(e -> viewPatients());
//        add(viewPatientsBtn);
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
//            String q = "SELECT b.BookingID, pi.Name AS PatientName, b.BookingDate, b.Status " +
//                    "FROM Bookings b JOIN Patient_Info pi ON b.PatientID = pi.PatientID " +
//                    "WHERE b.DoctorID = " + doctorId;
//            ResultSet rs = c.statement.executeQuery(q);
//            JTable table = new JTable(DbUtils.resultSetToTableModel(rs));
//            showTableDialog(table, "My Bookings");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error retrieving bookings: " + ex.getMessage());
//        }
//    }
//
//    private void viewPatients() {
//        try {
//            conn c = new conn();
//            String q = "SELECT pi.Name, pi.Gender, pi.Disease, pi.Room_No, pi.Admit_Time, pi.Deposit " +
//                    "FROM Patient_Info pi JOIN Bookings b ON pi.PatientID = b.PatientID " +
//                    "WHERE b.DoctorID = " + doctorId;
//            ResultSet rs = c.statement.executeQuery(q);
//            JTable table = new JTable(DbUtils.resultSetToTableModel(rs));
//            showTableDialog(table, "My Patients");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error retrieving patients: " + ex.getMessage());
//        }
//    }
//
//    private void showTableDialog(JTable table, String title) {
//        JDialog dialog = new JDialog(this, title, true);
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

public class DoctorDashboard extends JFrame {
    private String doctorName;
    private int doctorId;

    public DoctorDashboard(String doctorName, int doctorId) {
        // Security check: Verify doctorId and name against database
        if (!isValidUser(doctorId, doctorName)) {
            JOptionPane.showMessageDialog(null, "Unauthorized access! Please log in.");
            setVisible(false);
            new Login();
            return;
        }

        this.doctorName = doctorName;
        this.doctorId = doctorId;
        System.out.println("DoctorDashboard initialized - Name: " + doctorName + ", DoctorID: " + doctorId); // Debug

        setTitle("Doctor Dashboard");
        setSize(800, 400);
        setLocation(400, 270);
        getContentPane().setBackground(new Color(109, 164, 170));
        setLayout(null);

        // Welcome Label (Centered at top)
        JLabel welcomeLabel = new JLabel("Welcome Doctor: " + doctorName);
        welcomeLabel.setBounds(300, 20, 300, 30);
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.BLACK);
        add(welcomeLabel);

        // Image (Full-size on the right)
        ImageIcon doctorIcon;
        try {
            doctorIcon = new ImageIcon(ClassLoader.getSystemResource("icon/dr.png"));
            if (doctorIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image not loaded");
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not load dr.png - using default icon");
            doctorIcon = new ImageIcon();
        }
        Image scaledImage = doctorIcon.getImage().getScaledInstance(200, 350, Image.SCALE_SMOOTH); // Increased to 200x350
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(550, 50, 200, 350); // Adjusted to right side, full height
        add(imageLabel);

        // Buttons (Aligned vertically on the left)
        JButton viewBookingsBtn = new JButton("View Bookings");
        viewBookingsBtn.setBounds(40, 80, 150, 30);
        viewBookingsBtn.setFont(new Font("serif", Font.BOLD, 15));
        viewBookingsBtn.setBackground(Color.BLACK);
        viewBookingsBtn.setForeground(Color.WHITE);
        viewBookingsBtn.addActionListener(e -> viewBookings());
        add(viewBookingsBtn);

        JButton viewPatientsBtn = new JButton("View Patients");
        viewPatientsBtn.setBounds(40, 130, 150, 30);
        viewPatientsBtn.setFont(new Font("serif", Font.BOLD, 15));
        viewPatientsBtn.setBackground(Color.BLACK);
        viewPatientsBtn.setForeground(Color.WHITE);
        viewPatientsBtn.addActionListener(e -> viewPatients());
        add(viewPatientsBtn);

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
    private boolean isValidUser(int doctorId, String doctorName) {
        try {
            conn c = new conn();
            if (c.connection == null) return false;
            String q = "SELECT DoctorID, Name FROM doctors WHERE DoctorID = ? AND Name = ?";
            java.sql.PreparedStatement ps = c.connection.prepareStatement(q);
            ps.setInt(1, doctorId);
            ps.setString(2, doctorName);
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
            String q = "SELECT b.BookingID, p.Name AS PatientName, b.BookingDate, b.Status " +
                    "FROM Bookings b JOIN patients p ON b.PatientID = p.PatientID " +
                    "WHERE b.DoctorID = " + doctorId;
            System.out.println("DoctorDashboard Query (Bookings): " + q);
            ResultSet rs = c.statement.executeQuery(q);
            if (!rs.isBeforeFirst()) {
                System.out.println("No bookings found for DoctorID: " + doctorId);
            }
            JTable table = new JTable(DbUtils.resultSetToTableModel(rs));
            showTableDialog(table, "My Bookings");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving bookings: " + ex.getMessage());
        }
    }

    private void viewPatients() {
        try {
            conn c = new conn();
            if (c.connection == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed");
                return;
            }
            String q = "SELECT pi.Name, pi.Gender, pi.Disease, pi.Room_No, pi.Admit_Time, pi.Deposit, b.BookingID, b.BookingDate, b.Status " +
                    "FROM Patient_Info pi " +
                    "JOIN Bookings b ON pi.PatientID = b.PatientID " +
                    "WHERE b.DoctorID = " + doctorId;
            System.out.println("DoctorDashboard Query (Patients): " + q);
            ResultSet rs = c.statement.executeQuery(q);
            if (!rs.isBeforeFirst()) {
                System.out.println("No patients found for DoctorID: " + doctorId);
            }
            JTable table = new JTable(DbUtils.resultSetToTableModel(rs));
            showTableDialog(table, "My Patients");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving patients: " + ex.getMessage());
        }
    }

    private void showTableDialog(JTable table, String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.add(new JScrollPane(table));
        dialog.setSize(800, 400); // Increased width to accommodate more columns
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoctorDashboard("Dr. John", 1)); // For testing only
    }
}