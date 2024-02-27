package UI;

import Main.Company;
import Main.CompanyController;

import javax.swing.*;
import java.awt.*;
import java.net.InetSocketAddress;

public class UI extends JFrame {

    public CompanyController ctr = null;

    public UI() {
        setSize(new Dimension(500, 500));
        setLayout(new FlowLayout());
        setResizable(false);

        var ctr = new ControlsPanel().getMainPanel();

        add(ctr);
//        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
//
//        var inAddrSeatrade = new LabeledInput("SeaTrade Address", "localhost", null, new Dimension(150, 20));
//        var inPortSeatrade = new LabeledInput("SeaTrade Port", "8081", null, new Dimension(150, 20));
//        var inPort = new LabeledInput("Company Port", "8080", null, new Dimension(150, 20));
//        var inCompName = new LabeledInput("Company Name", "East India Trading Company", null, new Dimension(150, 20));
//
//        var btnStart = new JButton("start");
//
//        btnStart.addActionListener(a -> {
//            ctr = new CompanyController(new InetSocketAddress(inAddrSeatrade.getVal(), Integer.parseInt(inPortSeatrade.getVal())), Integer.parseInt(inPort.getVal()), inCompName.getVal());
//            ctr.launch();
//        });
//
//        var controlCon = new JPanel();
//
//        inAddrSeatrade.setAlignmentY(Component.TOP_ALIGNMENT);
//        inPort.setAlignmentY(Component.TOP_ALIGNMENT);
//
//        controlCon.add(inAddrSeatrade);
//        controlCon.add(inPortSeatrade);
//        controlCon.add(inPort);
//        controlCon.add(btnStart);
//        var layout = new BoxLayout(controlCon, BoxLayout.Y_AXIS);
//        controlCon.setLayout(layout);
//
//        controlCon.setSize(getWidth() / 3, getHeight());
//
//        var logCon = new JPanel();
//        logCon.setBackground(Color.CYAN);
//
//        add(controlCon);
//        add(logCon);

    }
}
