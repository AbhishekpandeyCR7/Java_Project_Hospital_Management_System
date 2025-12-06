//package hospital.management.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Appointment extends JFrame {
//    private JComboBox<String> doctorComboBox;
//    private JTextField patientNameField, dateField;
//    private JComboBox<String> timeComboBox;
//    private Map<String, String> doctorSpecialties;
//
//    public Appointment() {
//        doctorSpecialties = new HashMap<>();
//        initializeDoctorSpecialties();
//
//        JPanel panel = new JPanel();
//        panel.setBounds(5, 5, 490, 390);
//        panel.setBackground(new Color(109, 164, 170));
//        panel.setLayout(null);
//        add(panel);
//
//        JLabel doctorLabel = new JLabel("Select Doctor:");
//        doctorLabel.setBounds(50, 30, 100, 20);
//        doctorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(doctorLabel);
//
//        doctorComboBox = new JComboBox<>();
//        doctorComboBox.setBounds(160, 30, 250, 25);
//        panel.add(doctorComboBox);
//
//        JLabel patientLabel = new JLabel("Patient Name:");
//        patientLabel.setBounds(50, 80, 100, 20);
//        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(patientLabel);
//
//        patientNameField = new JTextField();
//        patientNameField.setBounds(160, 80, 200, 25);
//        panel.add(patientNameField);
//
//        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
//        dateLabel.setBounds(50, 130, 150, 20);
//        dateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(dateLabel);
//
//        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        dateField.setBounds(200, 130, 160, 25);
//        panel.add(dateField);
//
//        JLabel timeLabel = new JLabel("Time:");
//        timeLabel.setBounds(50, 180, 100, 20);
//        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(timeLabel);
//
//        String[] times = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
//        timeComboBox = new JComboBox<>(times);
//        timeComboBox.setBounds(160, 180, 200, 25);
//        panel.add(timeComboBox);
//
//        JButton bookButton = new JButton("Book Appointment");
//        bookButton.setBounds(100, 250, 150, 30);
//        bookButton.setBackground(Color.BLACK);
//        bookButton.setForeground(Color.WHITE);
//        panel.add(bookButton);
//
//        JButton backButton = new JButton("Back");
//        backButton.setBounds(270, 250, 100, 30);
//        backButton.setBackground(Color.BLACK);
//        backButton.setForeground(Color.WHITE);
//        panel.add(backButton);
//
//        loadDoctors();
//
//        bookButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                bookAppointment();
//            }
//        });
//
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//            }
//        });
//
//        setUndecorated(true);
//        setSize(500, 400);
//        setLocation(450, 300);
//        setLayout(null);
//        setVisible(true);
//    }
//
//    private void initializeDoctorSpecialties() {
//        doctorSpecialties.put("Dr. John Smith", "Cardiology");
//        doctorSpecialties.put("Dr. Sarah Johnson", "Neurology");
//        doctorSpecialties.put("Dr. Michael Brown", "Orthopedics");
//        doctorSpecialties.put("Dr. Emily Davis", "Pediatrics");
//        doctorSpecialties.put("Dr. Robert Wilson", "Oncology");
//    }
//
//    private void loadDoctors() {
//        try {
//            conn c = new conn();
//            String q = "SELECT Name FROM EMP_INFO WHERE Salary > 50000";
//            ResultSet rs = c.statement.executeQuery(q);
//
//            doctorComboBox.removeAllItems();
//
//            while (rs.next()) {
//                String doctorName = rs.getString("Name");
//                if (doctorSpecialties.containsKey(doctorName)) {
//                    String displayText = doctorName + " - " + doctorSpecialties.get(doctorName);
//                    doctorComboBox.addItem(displayText);
//                }
//            }
//
//            if (doctorComboBox.getItemCount() == 0) {
//                for (Map.Entry<String, String> entry : doctorSpecialties.entrySet()) {
//                    doctorComboBox.addItem(entry.getKey() + " - " + entry.getValue());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            for (Map.Entry<String, String> entry : doctorSpecialties.entrySet()) {
//                doctorComboBox.addItem(entry.getKey() + " - " + entry.getValue());
//            }
//        }
//    }
//
//    private void bookAppointment() {
//        try {
//            conn c = new conn();
//            String query = "INSERT INTO Appointments (doctor_name, patient_name, appointment_date, appointment_time, specialty) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = c.connection.prepareStatement(query);
//
//            String selectedDoctor = (String) doctorComboBox.getSelectedItem();
//            String doctorName = selectedDoctor.split(" - ")[0];
//            String specialty = doctorSpecialties.get(doctorName);
//
//            ps.setString(1, doctorName);
//            ps.setString(2, patientNameField.getText());
//            ps.setString(3, dateField.getText());
//            ps.setString(4, (String) timeComboBox.getSelectedItem());
//            ps.setString(5, specialty);
//
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Appointment booked successfully with " + selectedDoctor + "!");
//            setVisible(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error booking appointment: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        new Appointment();
//    }
//}
//package hospital.management.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Appointment extends JFrame {
//    private JComboBox<String> doctorComboBox;
//    private JTextField patientNameField, dateField;
//    private JComboBox<String> timeComboBox;
//
//    public Appointment() {
//        JPanel panel = new JPanel();
//        panel.setBounds(5, 5, 490, 390);
//        panel.setBackground(new Color(109, 164, 170));
//        panel.setLayout(null);
//        add(panel);
//
//        JLabel doctorLabel = new JLabel("Select Doctor:");
//        doctorLabel.setBounds(50, 30, 100, 20);
//        doctorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(doctorLabel);
//
//        doctorComboBox = new JComboBox<>();
//        doctorComboBox.setBounds(160, 30, 250, 25);
//        panel.add(doctorComboBox);
//
//        JLabel patientLabel = new JLabel("Patient Name:");
//        patientLabel.setBounds(50, 80, 100, 20);
//        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(patientLabel);
//
//        patientNameField = new JTextField();
//        patientNameField.setBounds(160, 80, 200, 25);
//        panel.add(patientNameField);
//
//        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
//        dateLabel.setBounds(50, 130, 150, 20);
//        dateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(dateLabel);
//
//        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        dateField.setBounds(200, 130, 160, 25);
//        panel.add(dateField);
//
//        JLabel timeLabel = new JLabel("Time:");
//        timeLabel.setBounds(50, 180, 100, 20);
//        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(timeLabel);
//
//        String[] times = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
//        timeComboBox = new JComboBox<>(times);
//        timeComboBox.setBounds(160, 180, 200, 25);
//        panel.add(timeComboBox);
//
//        JButton bookButton = new JButton("Book Appointment");
//        bookButton.setBounds(100, 250, 150, 30);
//        bookButton.setBackground(Color.BLACK);
//        bookButton.setForeground(Color.WHITE);
//        bookButton.addActionListener(e -> bookAppointment());
//        panel.add(bookButton);
//
//        JButton backButton = new JButton("Back");
//        backButton.setBounds(270, 250, 100, 30);
//        backButton.setBackground(Color.BLACK);
//        backButton.setForeground(Color.WHITE);
//        backButton.addActionListener(e -> setVisible(false));
//        panel.add(backButton);
//
//        loadDoctors();
//
//        setUndecorated(true);
//        setSize(500, 400);
//        setLocation(450, 300);
//        setLayout(null);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    }
//
//    private void loadDoctors() {
//        try {
//            conn c = new conn();
//            String q = "SELECT DoctorID, Name FROM doctors";
//            ResultSet rs = c.statement.executeQuery(q);
//
//            doctorComboBox.removeAllItems();
//            while (rs.next()) {
//                String doctorName = rs.getString("Name");
//                int doctorId = rs.getInt("DoctorID");
//                doctorComboBox.addItem(doctorId + " - " + doctorName); // Store ID with name
//            }
//            rs.close();
//
//            if (doctorComboBox.getItemCount() == 0) {
//                JOptionPane.showMessageDialog(null, "No doctors available");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error loading doctors: " + e.getMessage());
//        }
//    }
//
//    private void bookAppointment() {
//        try {
//            conn c = new conn();
//
//            // Get DoctorID from selected item
//            String selectedDoctor = (String) doctorComboBox.getSelectedItem();
//            if (selectedDoctor == null) {
//                JOptionPane.showMessageDialog(null, "Please select a doctor");
//                return;
//            }
//            int doctorId = Integer.parseInt(selectedDoctor.split(" - ")[0]);
//            String doctorName = selectedDoctor.split(" - ")[1];
//
//            // Get PatientID from patient name
//            String patientName = patientNameField.getText().trim();
//            if (patientName.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Please enter patient name");
//                return;
//            }
//            String patientQuery = "SELECT PatientID FROM patients WHERE Name = ?";
//            PreparedStatement psPatient = c.connection.prepareStatement(patientQuery);
//            psPatient.setString(1, patientName);
//            ResultSet rs = psPatient.executeQuery();
//            int patientId;
//            if (rs.next()) {
//                patientId = rs.getInt("PatientID");
//            } else {
//                JOptionPane.showMessageDialog(null, "Patient not found. Please register the patient first.");
//                return;
//            }
//            rs.close();
//
//            // Validate date and time
//            String dateStr = dateField.getText().trim();
//            String timeStr = (String) timeComboBox.getSelectedItem();
//            String bookingDateTime = dateStr + " " + timeStr + ":00";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.setLenient(false);
//            try {
//                sdf.parse(bookingDateTime);
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.");
//                return;
//            }
//
//            // Generate unique BookingID
//            String bookingId = "B" + System.currentTimeMillis();
//
//            // Insert into Bookings
//            String query = "INSERT INTO Bookings (BookingID, PatientID, DoctorID, BookingDate, Status) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = c.connection.prepareStatement(query);
//            ps.setString(1, bookingId);
//            ps.setInt(2, patientId);
//            ps.setInt(3, doctorId);
//            ps.setString(4, bookingDateTime);
//            ps.setString(5, "Pending");
//
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Appointment booked successfully with " + doctorName + "!");
//            setVisible(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error booking appointment: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Appointment::new);
//    }
//}
//package hospital.management.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Appointment extends JFrame {
//    private JComboBox<String> doctorComboBox;
//    private JTextField patientNameField, dateField;
//    private JComboBox<String> timeComboBox;
//
//    public Appointment() {
//        JPanel panel = new JPanel();
//        panel.setBounds(5, 5, 490, 390);
//        panel.setBackground(new Color(109, 164, 170));
//        panel.setLayout(null);
//        add(panel);
//
//        JLabel doctorLabel = new JLabel("Select Doctor:");
//        doctorLabel.setBounds(50, 30, 100, 20);
//        doctorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(doctorLabel);
//
//        doctorComboBox = new JComboBox<>();
//        doctorComboBox.setBounds(160, 30, 250, 25);
//        panel.add(doctorComboBox);
//
//        JLabel patientLabel = new JLabel("Patient Name:");
//        patientLabel.setBounds(50, 80, 100, 20);
//        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(patientLabel);
//
//        patientNameField = new JTextField();
//        patientNameField.setBounds(160, 80, 200, 25);
//        panel.add(patientNameField);
//
//        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
//        dateLabel.setBounds(50, 130, 150, 20);
//        dateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(dateLabel);
//
//        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        dateField.setBounds(200, 130, 160, 25);
//        panel.add(dateField);
//
//        JLabel timeLabel = new JLabel("Time:");
//        timeLabel.setBounds(50, 180, 100, 20);
//        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        panel.add(timeLabel);
//
//        String[] times = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
//        timeComboBox = new JComboBox<>(times);
//        timeComboBox.setBounds(160, 180, 200, 25);
//        panel.add(timeComboBox);
//
//        JButton bookButton = new JButton("Book Appointment");
//        bookButton.setBounds(100, 250, 150, 30);
//        bookButton.setBackground(Color.BLACK);
//        bookButton.setForeground(Color.WHITE);
//        bookButton.addActionListener(e -> bookAppointment());
//        panel.add(bookButton);
//
//        JButton backButton = new JButton("Back");
//        backButton.setBounds(270, 250, 100, 30);
//        backButton.setBackground(Color.BLACK);
//        backButton.setForeground(Color.WHITE);
//        backButton.addActionListener(e -> setVisible(false));
//        panel.add(backButton);
//
//        loadDoctors();
//
//        setUndecorated(true);
//        setSize(500, 400);
//        setLocation(450, 300);
//        setLayout(null);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    }
//
//    private void loadDoctors() {
//        try {
//            conn c = new conn();
//            String q = "SELECT DoctorID, Name FROM doctors";
//            ResultSet rs = c.statement.executeQuery(q);
//
//            doctorComboBox.removeAllItems();
//            while (rs.next()) {
//                String doctorName = rs.getString("Name");
//                int doctorId = rs.getInt("DoctorID");
//                doctorComboBox.addItem(doctorId + " - " + doctorName);
//            }
//            rs.close();
//
//            if (doctorComboBox.getItemCount() == 0) {
//                JOptionPane.showMessageDialog(null, "No doctors available");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error loading doctors: " + e.getMessage());
//        }
//    }
//
//    private void bookAppointment() {
//        try {
//            conn c = new conn();
//
//            String selectedDoctor = (String) doctorComboBox.getSelectedItem();
//            if (selectedDoctor == null) {
//                JOptionPane.showMessageDialog(null, "Please select a doctor");
//                return;
//            }
//            int doctorId = Integer.parseInt(selectedDoctor.split(" - ")[0]);
//            String doctorName = selectedDoctor.split(" - ")[1];
//
//            String patientName = patientNameField.getText().trim();
//            if (patientName.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Please enter patient name");
//                return;
//            }
//            String patientQuery = "SELECT PatientID FROM patients WHERE Name = ?";
//            PreparedStatement psPatient = c.connection.prepareStatement(patientQuery);
//            psPatient.setString(1, patientName);
//            ResultSet rs = psPatient.executeQuery();
//            int patientId;
//            if (rs.next()) {
//                patientId = rs.getInt("PatientID");
//            } else {
//                JOptionPane.showMessageDialog(null, "Patient not found. Please register the patient first.");
//                return;
//            }
//            rs.close();
//
//            String dateStr = dateField.getText().trim();
//            String timeStr = (String) timeComboBox.getSelectedItem();
//            String bookingDateTime = dateStr + " " + timeStr + ":00";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.setLenient(false);
//            try {
//                sdf.parse(bookingDateTime);
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.");
//                return;
//            }
//
//            String bookingId = "B" + System.currentTimeMillis();
//            String query = "INSERT INTO Bookings (BookingID, PatientID, DoctorID, BookingDate, Status) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = c.connection.prepareStatement(query);
//            ps.setString(1, bookingId);
//            ps.setInt(2, patientId);
//            ps.setInt(3, doctorId);
//            ps.setString(4, bookingDateTime);
//            ps.setString(5, "Pending");
//
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Appointment booked successfully with " + doctorName + "!");
//            setVisible(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error booking appointment: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Appointment::new);
//    }
//}
package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment extends JFrame {
    private JComboBox<String> doctorComboBox;
    private JTextField patientNameField, dateField;
    private JComboBox<String> timeComboBox;

    public Appointment() {
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 490, 390);
        panel.setBackground(new Color(109, 164, 170));
        panel.setLayout(null);
        add(panel);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setBounds(50, 30, 100, 20);
        doctorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(doctorLabel);

        doctorComboBox = new JComboBox<>();
        doctorComboBox.setBounds(160, 30, 250, 25);
        panel.add(doctorComboBox);

        JLabel patientLabel = new JLabel("Patient Name:");
        patientLabel.setBounds(50, 80, 100, 20);
        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(patientLabel);

        patientNameField = new JTextField();
        patientNameField.setBounds(160, 80, 200, 25);
        panel.add(patientNameField);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setBounds(50, 130, 150, 20);
        dateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(dateLabel);

        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dateField.setBounds(200, 130, 160, 25);
        panel.add(dateField);

        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(50, 180, 100, 20);
        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(timeLabel);

        String[] times = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
        timeComboBox = new JComboBox<>(times);
        timeComboBox.setBounds(160, 180, 200, 25);
        panel.add(timeComboBox);

        JButton bookButton = new JButton("Book Appointment");
        bookButton.setBounds(100, 250, 150, 30);
        bookButton.setBackground(Color.BLACK);
        bookButton.setForeground(Color.WHITE);
        bookButton.addActionListener(e -> bookAppointment());
        panel.add(bookButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(270, 250, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> setVisible(false));
        panel.add(backButton);

        loadDoctors();

        setUndecorated(true);
        setSize(500, 400);
        setLocation(450, 300);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadDoctors() {
        try {
            conn c = new conn();
            String q = "SELECT DoctorID, Name FROM doctors";
            ResultSet rs = c.statement.executeQuery(q);

            doctorComboBox.removeAllItems();
            while (rs.next()) {
                String doctorName = rs.getString("Name");
                int doctorId = rs.getInt("DoctorID");
                doctorComboBox.addItem(doctorId + " - " + doctorName);
            }
            rs.close();

            if (doctorComboBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "No doctors available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading doctors: " + e.getMessage());
        }
    }

    private void bookAppointment() {
        try {
            conn c = new conn();

            String selectedDoctor = (String) doctorComboBox.getSelectedItem();
            if (selectedDoctor == null) {
                JOptionPane.showMessageDialog(null, "Please select a doctor");
                return;
            }
            int doctorId = Integer.parseInt(selectedDoctor.split(" - ")[0]);
            String doctorName = selectedDoctor.split(" - ")[1];

            String patientName = patientNameField.getText().trim();
            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter patient name");
                return;
            }
            String patientQuery = "SELECT PatientID FROM patients WHERE Name = ?";
            PreparedStatement psPatient = c.connection.prepareStatement(patientQuery);
            psPatient.setString(1, patientName);
            ResultSet rs = psPatient.executeQuery();
            int patientId;
            if (rs.next()) {
                patientId = rs.getInt("PatientID");
            } else {
                JOptionPane.showMessageDialog(null, "Patient not found. Please register the patient first.");
                return;
            }
            rs.close();

            String dateStr = dateField.getText().trim();
            String timeStr = (String) timeComboBox.getSelectedItem();
            String bookingDateTime = dateStr + " " + timeStr + ":00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setLenient(false);
            try {
                sdf.parse(bookingDateTime);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.");
                return;
            }

            String bookingId = "B" + System.currentTimeMillis();
            String query = "INSERT INTO Bookings (BookingID, PatientID, DoctorID, BookingDate, Status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = c.connection.prepareStatement(query);
            ps.setString(1, bookingId);
            ps.setInt(2, patientId);
            ps.setInt(3, doctorId);
            ps.setString(4, bookingDateTime);
            ps.setString(5, "Pending");

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Appointment booked successfully with " + doctorName + "!");
            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error booking appointment: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Appointment::new);
    }
}