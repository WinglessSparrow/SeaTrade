package UI;

import javax.swing.*;
import java.awt.*;

public class LabeledInput extends JPanel {

    private final JTextField input;

    public LabeledInput(String name, String stdVal, Dimension dim, Dimension inputDim) {
        setLayout(new FlowLayout());

        if (dim != null) {
            setSize(dim);
        }

        var label = new JLabel(name);

        input = new JTextField();
        input.setText(stdVal);
        input.setPreferredSize(inputDim);

        add(label);
        add(input);
    }

    public String getVal() {
        return input.getText();
    }
}
