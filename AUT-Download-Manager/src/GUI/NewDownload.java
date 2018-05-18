package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class NewDownload extends JFrame {
    private JPanel main;
    private JLabel url;
    private JTextField input;
    private JButton add;

    public NewDownload(){
        super("New Download");
        setLayout(new FlowLayout());
        setVisible(true);
        setBackground(Color.WHITE);
        setSize(new Dimension(850,20));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        url = new JLabel("URL:");
        add(url);

        input = new JTextField();
        input.setPreferredSize(new Dimension(600,20));
        add(input);
        try {
            String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            if (clipboard.startsWith("http") || !clipboard.contains(" "))
                input.setText(clipboard);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        add = new JButton("Add");
        add(add);
    }
}
