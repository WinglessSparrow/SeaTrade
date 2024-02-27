package UI;

import javax.swing.*;

public class ControlsPanel {
    private JTextField seatradeIP;
    private JTextField seatradePort;
    private JTextField companyPort;
    private JButton startServerButton;
    private JButton restartServerButton;
    private JButton openWebSiteButton;
    private JPanel mainPanel;
    private JTextField companyName;
    private JButton shutdownButton;
    private JTextPane textPane1;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getSeatradeIP() {
        return seatradeIP;
    }

    public JTextField getSeatradePort() {
        return seatradePort;
    }

    public JTextField getCompanyPort() {
        return companyPort;
    }

    public JButton getStartServerButton() {
        return startServerButton;
    }

    public JButton getRestartServerButton() {
        return restartServerButton;
    }

    public JButton getOpenWebSiteButton() {
        return openWebSiteButton;
    }

    public JTextField getCompanyName() {
        return companyName;
    }

    public JButton getShutdownButton() {
        return shutdownButton;
    }

    public JTextPane getTextPane1() {
        return textPane1;
    }
}
