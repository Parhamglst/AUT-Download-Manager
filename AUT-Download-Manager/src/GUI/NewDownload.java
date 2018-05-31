package GUI;

import Utils.Downloads;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * New download panel for adding a new download
 */
public class NewDownload extends JFrame {
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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Downloads.getInstance().newDownload(input.getText());
                dispose();
            }
        });
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    Downloads.getInstance().newDownload(input.getText());
                    dispose();
                }
            }
        });
    }
}
