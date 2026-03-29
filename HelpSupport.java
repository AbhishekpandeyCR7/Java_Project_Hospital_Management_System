package hospital.management.system;

import javax.swing.*;
import java.awt.*;

public class HelpSupport extends JFrame {
    HelpSupport() {
        // Frame title
        setTitle("Help and Support");

        // Frame settings
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Add image banner at the top
        ImageIcon bannerIcon = new ImageIcon(ClassLoader.getSystemResource("icon/Help.jpg")); // Use your custom image
        Image bannerImage = bannerIcon.getImage().getScaledInstance(600, 150, Image.SCALE_SMOOTH);
        JLabel bannerLabel = new JLabel(new ImageIcon(bannerImage), JLabel.CENTER);
        add(bannerLabel, BorderLayout.NORTH);

        // Text area for user guide content
        JTextArea guideContent = new JTextArea();
        guideContent.setFont(new Font("Arial", Font.PLAIN, 14));
        guideContent.setLineWrap(true);
        guideContent.setWrapStyleWord(true);
        guideContent.setEditable(false);
        guideContent.setText("Welcome to the Hospital Management System!\n\n"
                + "Here's how you can use the system:\n\n"
                + "1. Patient Management:\n   - Add, update, and discharge patients from the Reception menu.\n\n"
                + "2. Facility Management:\n   - View and manage rooms, departments, and ambulances.\n\n"
                + "3. Staff Management:\n   - View employee information.\n\n"
                + "4. Appointments:\n   - Book and view appointments from the Reception menu.\n\n"
                + "5. Billing:\n   - Generate and view bills easily.\n\n"
                + "6. Inventory Management:\n   - Track and manage medical supplies efficiently.\n\n"
                + "For further assistance, contact the system administrator.\n");

        JScrollPane scrollPane = new JScrollPane(guideContent);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(40, 80, 120), 2));
        add(scrollPane, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(255, 180, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(255, 180, 0), 1));
        closeButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new HelpSupport();
    }
}
