import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.ArrayList;

public class JobPortal {
    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, String> roles = new HashMap<>();
    private static ArrayList<String> jobs = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JobPortal::showLoginPage);
    }

    // Show Login Page
    private static void showLoginPage() {
        JFrame frame = new JFrame("Job Portal - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
    
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        JButton exitButton = new JButton("Exit"); // New Exit Button
    
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(signupButton);
        panel.add(new JLabel()); // Spacer
        panel.add(exitButton);   // Add Exit Button to the grid
    
        frame.add(panel);
        frame.setVisible(true);
    
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
    
            if (users.containsKey(username) && users.get(username).equals(password)) {
                String role = roles.get(username);
                frame.dispose();
                if ("Employer".equals(role)) {
                    showEmployerPage(username);
                } else {
                    showJobSeekerPage(username);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignupPage();
        });
    
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        });
    }
    

    // Show Signup Page
    private static void showSignupPage() {
        JFrame frame = new JFrame("Job Portal - Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Employer", "JobSeeker"});
        JButton signupButton = new JButton("Signup");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(new JLabel());
        panel.add(signupButton);

        frame.add(panel);
        frame.setVisible(true);

        signupButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (!users.containsKey(username)) {
                users.put(username, password);
                roles.put(username, role);
                JOptionPane.showMessageDialog(frame, "Signup successful!");
                frame.dispose();
                showLoginPage();
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Show Employer Page
    private static void showEmployerPage(String username) {
        JFrame frame = new JFrame("Job Portal - Employer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> jobListModel = new DefaultListModel<>();
        JList<String> jobList = new JList<>(jobListModel);
        JTextField jobField = new JTextField();
        JButton postButton = new JButton("Post Job");
        JButton logoutButton = new JButton("Logout");

        // Display posted jobs
        for (String job : jobs) {
            jobListModel.addElement(job);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Post a Job:"), BorderLayout.WEST);
        topPanel.add(jobField, BorderLayout.CENTER);
        topPanel.add(postButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(jobList), BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        postButton.addActionListener(e -> {
            String job = jobField.getText().trim();
            if (!job.isEmpty()) {
                jobs.add(job);
                jobListModel.addElement(job);
                JOptionPane.showMessageDialog(frame, "Job posted successfully!");
                jobField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Job description cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        logoutButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
    }

    // Show Job Seeker Page
    private static void showJobSeekerPage(String username) {
        JFrame frame = new JFrame("Job Portal - Job Seeker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> jobListModel = new DefaultListModel<>();
        JList<String> jobList = new JList<>(jobListModel);
        JButton applyButton = new JButton("Apply");
        JButton logoutButton = new JButton("Logout");

        // Display jobs
        for (String job : jobs) {
            jobListModel.addElement(job);
        }

        panel.add(new JScrollPane(jobList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(applyButton);
        bottomPanel.add(logoutButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        applyButton.addActionListener(e -> {
            String selectedJob = jobList.getSelectedValue();
            if (selectedJob != null) {
                JOptionPane.showMessageDialog(frame, "Applied for job: " + selectedJob);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a job to apply!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        logoutButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
    }
}