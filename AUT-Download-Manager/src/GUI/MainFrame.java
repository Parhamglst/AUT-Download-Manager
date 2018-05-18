package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private MenuBar menuBar;
    private Toolbar toolbar;
    private Tabs tabs;
    private JPanel mainPanel;
    private JList lists;
    private DownloadList downloadList;
    private Completed completed;

    public MainFrame(String title, DefaultListModel listModel) {
        super(title);
        setLayout(new BorderLayout());
        setSize(new Dimension(1080,600));
        setBackground(Color.WHITE);

        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        toolbar = new Toolbar();
        add(toolbar, BorderLayout.NORTH);
        toolbar.setVisible(true);

        completed = new Completed(listModel);

        mainPanel = new JPanel();
        mainPanel.setVisible(true);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        downloadList = new DownloadList(listModel);
        mainPanel.add(downloadList, BorderLayout.CENTER);
        downloadList.setVisible(true);
        downloadList.addMouseListener(new MainFrame.CustomMouseAdapter());
        lists = downloadList;
        add(lists, BorderLayout.CENTER);

        DefaultListModel<JList> tabList = new DefaultListModel<>();
        tabList.addElement(downloadList);
        tabList.addElement(completed);

        tabs = new Tabs(tabList);
        add(tabs, BorderLayout.WEST);
        tabs.getTabs().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == 1 && e.getClickCount() == 1){
                    if((tabs.getTabs().getSelectedValue()).toString().equals("Completed Downloads"))
                        lists = completed;
                    if((tabs.getTabs().getSelectedValue()).toString().equals("Downloads"))
                        lists = downloadList;
                }
            }
        });
        tabs.setVisible(true);

        setVisible(true);
    }

    public static void setLookAndFeel(int choice){
        switch (choice){
            case 0:
                return;
            case 1:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public class CustomMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON2) {
                mainPanel.add(new DownloadInfo("dsa","dsa","dsa","dsa","dsa","dsa"),BorderLayout.EAST);
            }
        }
    }

    private class DownloadInfo extends JPanel implements ActionListener {
        FileName fileName;
        JTextField downloadDir;
        SaveTo saveTo;
        JLabel fileType;
        JLabel size;
        JLabel downloadStatues;
        JButton ok;

        DownloadInfo(String fileName, String fileType, String downloadDir, String size, String downloadStatues, String saveDir) {
            setLayout(new GridLayout(7, 2));

            this.fileName = new FileName(fileName, fileType);
            add(this.fileName);

            add(new JLabel("File type:"),3);
            this.fileType.setText(fileType);
            add(this.fileType, 4);

            add(new JLabel("File size:"),5);
            this.size.setText(size);
            add(this.size,6);

            add(new JLabel("Download address:"), 7);
            this.downloadDir.setText(downloadDir);
            this.downloadDir.setEditable(false);
            add(this.downloadDir,8);

            add(new JLabel("Download statues:"),9);
            this.downloadStatues.setText(downloadStatues);//TODO:Improvement(show the completion in percentage)
            add(this.downloadStatues,17);

            saveTo = new SaveTo(saveDir);
            add(new JLabel("Save to:"),11);
            add(saveTo,12);

            add(ok,14);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                setOpaque(false);
            }
        }

        private class SaveTo extends JPanel implements ActionListener {
            JTextField saveDir;
            JButton changeSaveDir;

            SaveTo(String saveDir) {
                setLayout(new FlowLayout());

                this.saveDir.setText(saveDir);
                this.saveDir.setEditable(false);
                add(this.saveDir);

                this.changeSaveDir.addActionListener(this);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == changeSaveDir) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save To");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    int choice = fileChooser.showOpenDialog(this);
                    if (choice != JFileChooser.APPROVE_OPTION) return;
                    saveDir.setText(fileChooser.getCurrentDirectory().getPath());
                }
            }
        }

        private class FileName extends JPanel {
            JLabel icon;
            JLabel fileName;

            FileName(String fileName, String fileType) {
                icon = new JLabel(fileName, new ImageIcon(), 1);//TODO:ImageIcon
                add(icon);

                this.fileName = new JLabel(fileName);
                add(this.fileName);
            }
        }
    }
}
