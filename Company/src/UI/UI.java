package UI;

import Main.CompanyController;

import javax.swing.*;
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
        });

        ctrHolder.getOpenWebSiteButton().addActionListener(a -> {

            try {
                var uri = new URI("http://localhost:9000");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        ctrHolder.getShutdownButton().addActionListener(a -> ctr.shutdown());

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
    }
}
