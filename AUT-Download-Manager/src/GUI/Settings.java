package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Settings Panel for changing the preferences
 */
public class Settings extends JFrame {
    private static Settings singleton;

    private JTabbedPane tabs;
    private SkinTab skinTab;

    private Settings(){
        setVisible(true);

        tabs= new JTabbedPane();
        add(tabs);
        tabs.addTab("Skin", new SkinTab());
    }

    /**
     * The method for constructing the singleton because we don't need multiple running settings panel
     * @return
     */
    public static Settings getInstance(){
        if(singleton == null){
            singleton = new Settings();
        }
        return singleton;
    }

    private class GeneralTab extends JPanel{

    }

    private class SkinTab extends JPanel{
        private JLabel skin;
        private JComboBox<String> lookAndFeel;

        private SkinTab() {
            setLayout(new GridLayout(1, 2));
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

                    SwingUtilities.updateComponentTreeUI(Settings.getInstance());
                    SwingUtilities.updateComponentTreeUI(MainFrame.getInstance(null,null,null));
                }
            });
        }
    }
}
