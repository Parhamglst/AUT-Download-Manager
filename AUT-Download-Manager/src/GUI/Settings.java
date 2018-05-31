package GUI;

import Utils.Downloads;
import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Flow;

/**
 * The Settings Panel for changing the preferences
 */
public class Settings extends JFrame {
    private static Settings singleton;
    private static final javax.swing.border.Border GRAY_LINE_BORDER = BorderFactory.createLineBorder(Color.gray);

    private JTabbedPane tabs;
    private ArrayList<String> preferences;

    private SkinTab skin;
    private Restrictions restrictions;
    private GeneralTab general;

    private Settings() {
        Dimension resolution = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setPreferredSize(new Dimension((int) (resolution.getWidth() * 2 / 5), (int) (resolution.getHeight() * 2 / 3)));
        setVisible(true);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        tabs = new JTabbedPane();
        add(tabs);
        tabs.setFocusable(false);

        general = new GeneralTab();
        tabs.addTab("General", general);
        restrictions = new Restrictions();
        tabs.addTab("Restrictions", restrictions);
        skin = new SkinTab();
        tabs.addTab("Skin", skin);

        preferences = new ArrayList<>();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    preferences.add(general.dir.getText());
                    restrictions.renderRestricted();
                    preferences.add(restrictions.restricted.getText());
                    preferences.add(restrictions.allowedTypes.getText());

                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./Preferences.jdm"));
                    out.writeObject(preferences);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("./Preferences.jdm"));
                    preferences = (ArrayList<String>) in.readObject();
                } catch (IOException | ClassNotFoundException e1) {

                }
            }
        });
    }

    /**
     * The method for constructing the singleton because we don't need multiple running settings panel
     *
     * @return
     */
    public static Settings getInstance() {
        if (singleton == null) {
            singleton = new Settings();
        }
        return singleton;
    }

    private class GeneralTab extends JPanel {
        private JLabel saveTo;
        private JTextField dir;
        private JButton browse;

        private JComboBox fileTypes;
        private JTextField ftDir;
        private JButton ftBrowse;
        private HashMap<String,String> filetypesMap;

        public GeneralTab() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridwidth = 1;
            gbc.weightx = 0.05;
            setBorder(GRAY_LINE_BORDER);

            gbc.gridy = 0;
            gbc.gridx = 0;
            saveTo = new JLabel("Default Storage Directory: ");
            add(saveTo, gbc);

            gbc.gridy = 0;
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 10;
            dir = new JTextField();
            try {
                if (!preferences.get(0).isEmpty()) {
                    dir.setText(preferences.get(0));
                }
            }
            catch (NullPointerException e){

            }
            add(dir, gbc);

            gbc.gridy = 0;
            gbc.gridx = 4;
            gbc.gridwidth = 1;
            gbc.fill = 0;
            gbc.weightx = 1;
            gbc.anchor = GridBagConstraints.EAST;
            browse = new JButton("Browse");
            browse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save To");
                    fileChooser.setApproveButtonText("Set");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    int choice = fileChooser.showOpenDialog(singleton);
                    if (choice != JFileChooser.APPROVE_OPTION) return;
                    dir.setText(fileChooser.getCurrentDirectory().getPath());
                }
            });
            add(browse, gbc);

            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;
            fileTypes = new JComboBox();

            if(!loadFileTypes()) {
                filetypesMap = new HashMap();
                filetypesMap.put("Programs","msi zip");
                filetypesMap.put("Compressed", "zip rar");
                filetypesMap.put("Videos","mp4 mkv");
                filetypesMap.put("Audio","mp3 flac");
                filetypesMap.put("Documents","epub pdf xls docx");
                for (HashMap.Entry<String,String> entry:
                     filetypesMap.entrySet()) {
                    this.fileTypes.addItem(entry);
                }
                this.fileTypes.addItem("Specify a file type");
            }
            fileTypes.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getItem() instanceof HashMap.Entry){
                        ftDir.setText((String)(((Map.Entry) e.getItem()).getValue()));
                    }
                    else{
                        JFrame newCategory = new JFrame("New Category");

                    }
                }
            });
            add(fileTypes, gbc);

            gbc.gridy = 1;
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 10;
            ftDir = new JTextField();
            add(ftDir, gbc);

            gbc.gridy = 1;
            gbc.gridx = 4;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            ftBrowse = new JButton("Browse");
            ftBrowse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save To");
                    fileChooser.setApproveButtonText("Set");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    int choice = fileChooser.showOpenDialog(singleton);
                    if (choice != JFileChooser.APPROVE_OPTION) return;
                    ftDir.setText(fileChooser.getCurrentDirectory().getPath());
                }
            });
            add(ftBrowse, gbc);
        }

        private boolean loadFileTypes(){
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("./filetypes.jdm"))){
                fileTypes.setModel((ComboBoxModel) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }
            return true;
        }
    }

    private class Restrictions extends JPanel {
        private JLabel websites;
        private JTextArea restricted;
        private JLabel fileTypes;
        private JTextArea allowedTypes;

        public Restrictions() {
            setLayout(new GridBagLayout());
            setBorder(GRAY_LINE_BORDER);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridwidth = 1;

            gbc.gridx = 0;
            gbc.gridy = 0;
            websites = new JLabel("Downloads would't start automatically from the following websites: ");
            add(websites, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            restricted = new JTextArea();
            restricted.setLineWrap(true);
            restricted.setWrapStyleWord(true);
            JScrollPane sp = new JScrollPane(restricted);
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            restricted.setBorder(GRAY_LINE_BORDER);
            restricted.setRows(5);
            restricted.setColumns(10);
//            restricted.setText(preferences.get(1));
            add(sp, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            JLabel restrictedWebsitesDescription = new JLabel("ex. youtube.com, lynda.com/photoshop");
            restrictedWebsitesDescription.setForeground(Color.gray);
            add(restrictedWebsitesDescription, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel(" "), gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            fileTypes = new JLabel("Download will automatically start for the following file types: ");
            add(fileTypes, gbc);

            gbc.gridx = 0;
            gbc.gridy = 6;
            allowedTypes = new JTextArea();
            allowedTypes.setLineWrap(true);
            allowedTypes.setWrapStyleWord(true);
            JScrollPane tsp = new JScrollPane(allowedTypes);
            tsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            allowedTypes.setBorder(GRAY_LINE_BORDER);
            allowedTypes.setRows(5);
            allowedTypes.setColumns(10);
//            allowedTypes.setText(preferences.get(2));
            add(tsp, gbc);

            gbc.gridx = 0;
            gbc.gridy = 8;
            JLabel fileTypesDescription = new JLabel("ex. ZIP rar .mkv PSD");
            fileTypesDescription.setForeground(Color.gray);
            add(fileTypesDescription, gbc);
        }

        void renderRestricted (){
            String[] restricted = this.restricted.getText().split(" ");
            for (int i = 0; restricted[i] != null; i++) {
                restricted[i] = ".*\\."+restricted[i]+".*";
            }
        }
    }

    private class SkinTab extends JPanel {
        private JLabel skin;
        private JComboBox<String> lookAndFeel;

        private SkinTab() {
            FlowLayout fl = new FlowLayout();
            fl.setAlignment(FlowLayout.LEFT);
            setLayout(fl);
            setBorder(GRAY_LINE_BORDER);

            skin = new JLabel("Skin:                                                                        ");
            add(skin);

            lookAndFeel = new JComboBox<>();
            add(lookAndFeel);
            final String aDefault = "Default";
            lookAndFeel.addItem(aDefault);
            final String aNimbus = "Nimbus";
            lookAndFeel.addItem(aNimbus);
            final String aMotif = "Motif";
            lookAndFeel.addItem(aMotif);
            final String aWindows = "Windows";
            lookAndFeel.addItem(aWindows);
//            lookAndFeel.setSelectedItem(preferences.get(3));
            lookAndFeel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (((String) lookAndFeel.getSelectedItem()).equals(aDefault))
                        MainFrame.setLookAndFeel(0);
                    if (((String) lookAndFeel.getSelectedItem()).equals(aNimbus))
                        MainFrame.setLookAndFeel(1);
                    if (((String) lookAndFeel.getSelectedItem()).equals(aMotif))
                        MainFrame.setLookAndFeel(3);
                    if (((String) lookAndFeel.getSelectedItem()).equals(aWindows))
                        MainFrame.setLookAndFeel(4);

                    SwingUtilities.updateComponentTreeUI(Settings.getInstance());
                    SwingUtilities.updateComponentTreeUI(MainFrame.getInstance("Another Download Manager"));
                }
            });
        }
    }
}
