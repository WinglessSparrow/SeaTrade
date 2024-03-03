package UI;

import Logger.Log;
import Central.CompanyController;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

public class UI extends JFrame {

    public CompanyController ctr = null;

    public UI() {
        setSize(new Dimension(600, 450));
        setLayout(new FlowLayout());
        setResizable(false);

        var ctrHolder = new ControlsPanel();

        ctrHolder.getStartServerButton().addActionListener(a -> {
            String cmpName = ctrHolder.getCompanyName().getText();
            int cmpPort = Integer.parseInt(ctrHolder.getCompanyPort().getText());

            String ip = ctrHolder.getSeatradeIP().getText();
            int port = Integer.parseInt(ctrHolder.getSeatradePort().getText());

            ctr = new CompanyController(new InetSocketAddress(ip, port), cmpPort, cmpName);

            ctr.launch();

            ctrHolder.getShutdownButton().setEnabled(true);
            ctrHolder.getRestartServerButton().setEnabled(true);
            ctrHolder.getOpenWebSiteButton().setEnabled(true);

            ctrHolder.getStartServerButton().setEnabled(false);
        });

        ctrHolder.getOpenWebSiteButton().addActionListener(a -> {
            try {
                var uri = new URI("http://localhost:9000");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        ctrHolder.getShutdownButton().addActionListener(a -> {
            ctrHolder.getShutdownButton().setEnabled(false);
            ctrHolder.getRestartServerButton().setEnabled(false);
            ctrHolder.getOpenWebSiteButton().setEnabled(false);

            ctrHolder.getStartServerButton().setEnabled(true);

            ctr.shutdown();
        });

        ctrHolder.getRestartServerButton().addActionListener(a -> ctr.restart());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (ctr != null) {
                    ctr.shutdown();
                }
            }
        });

        add(ctrHolder.getMainPanel());

        ctrHolder.getTextPane().setCaretColor(Color.WHITE);

        Log.setOut(msg -> {
            try {
                var sDoc = ctrHolder.getTextPane().getStyledDocument();
                sDoc.insertString(sDoc.getLength(), "| LOG | " + msg + "\n", null);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        });

        Log.setGreenOut(msg -> {
            try {
                var keyWord = new SimpleAttributeSet();
                StyleConstants.setForeground(keyWord, new Color(48, 182, 65));
                var sDoc = ctrHolder.getTextPane().getStyledDocument();
                sDoc.insertString(sDoc.getLength(), "| LOG | " + msg + "\n", keyWord);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        });

        Log.setErrOut(msg -> {
            try {
                var keyWord = new SimpleAttributeSet();
                StyleConstants.setForeground(keyWord, new Color(199, 28, 28));
                var sDoc = ctrHolder.getTextPane().getStyledDocument();
                sDoc.insertString(sDoc.getLength(), "| ERROR | " + msg + "\n", keyWord);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
