package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {
    private JLabel skin;
    private JComboBox<String> lookAndFeel;

    public Settings() {
        setLayout(new GridLayout(1, 2));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        skin = new JLabel("Skin:");
        add(skin);

        lookAndFeel = new JComboBox<>();
        add(lookAndFeel);
        lookAndFeel.addItem("Default");
        lookAndFeel.addItem("Nimbus");
        lookAndFeel.addItem("GTK+");
        lookAndFeel.addItem("Motif");
        lookAndFeel.addItem("Windows");
        lookAndFeel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String) lookAndFeel.getSelectedItem()).equals("Default"))
                    MainFrame.setLookAndFeel(0);
                if (((String) lookAndFeel.getSelectedItem()).equals("Nimbus"))
                    MainFrame.setLookAndFeel(1);
                if (((String) lookAndFeel.getSelectedItem()).equals("GTK+"))
                    MainFrame.setLookAndFeel(2);
                if (((String) lookAndFeel.getSelectedItem()).equals("Motif"))
                    MainFrame.setLookAndFeel(3);
                if (((String) lookAndFeel.getSelectedItem()).equals("Windows"))
                    MainFrame.setLookAndFeel(4);
            }
        });
    }
}
