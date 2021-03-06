package GUI;

import Utils.Download;
import Utils.Downloads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The man GUI panel containing all the components
 */
public class MainFrame extends JFrame {
    public static final String ICON_PACK = "./Icons/";

    private static MainFrame singleton;

    private MenuBar menuBar;
    private Toolbar toolbar;
    private LogoAndTabs tabs;
    private JPanel mainPanel;
    private DownloadList downloadList;
    private Completed completed;
    private Queues queues;
    private DefaultListModel<Download> incomplete;
    private DefaultListModel<Download> completedDownloads;

    /**
     * The constructor for instantiating the GUI
     * @param title title of the download manager
     */
    private MainFrame(String title) {
        super(title);
        Dimension resolution = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BorderLayout());
        setSize(new Dimension((int)(resolution.getWidth()*5/8), (int)(resolution.getHeight()*3/5)));
        setBackground(Color.lightGray);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        toolbar = new Toolbar();
        add(toolbar, BorderLayout.NORTH);
        toolbar.setVisible(true);

        loadCompletedDowloads();
        loadDownloads();

        completed = Completed.getInstance(completedDownloads);
        JScrollPane completedSP = new JScrollPane(completed);
        completedSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        completedSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        queues = Queues.getInstance();

        mainPanel = new JPanel();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        mainPanel.setVisible(true);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.lightGray);

        this.downloadList = DownloadList.getInstance(incomplete);
        JScrollPane incompleteSP = new JScrollPane(this.downloadList);
        incompleteSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        incompleteSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

//        add(queues);
        add(incompleteSP, BorderLayout.CENTER);
        this.downloadList.setActivePanel(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                downloadList.setPreferredSize(new Dimension(getWidth() - 30, 50));
                revalidate();
                repaint();
            }
        });

        DefaultListModel<Component> tabList = new DefaultListModel<>();
        tabList.addElement(this.downloadList);
        tabList.addElement(completed);
        tabList.addElement(queues);

        tabs = new LogoAndTabs(tabList,incomplete,completedDownloads);
        add(tabs, BorderLayout.WEST);

        systemTray();

        setVisible(true);
    }

    public static MainFrame getInstance(String title) {
        if (singleton == null) {
            singleton = new MainFrame(title);
        }
        return singleton;
    }

    public boolean loadDownloads(){
        Downloads downloads = Downloads.getInstance();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("./incomplete.jdm"))){
            downloads.setActiveDownloads((ArrayList<Download>) in.readObject());
            incomplete = new DefaultListModel<>();
            for (Download download:
                 downloads.getActiveDownloads()) {
                incomplete.addElement(download);
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./incomplete.jdm"))) {
                outputStream.writeObject(downloads.getActiveDownloads());
                incomplete = new DefaultListModel<>();
            } catch (IOException e1) {
                return false;
            }
            return true;
        }
    }

    public boolean loadCompletedDowloads(){
        Downloads downloads = Downloads.getInstance();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("./completed.jdm"))){
            downloads.setCompletedDownloads((ArrayList<Download>) in.readObject());
            completedDownloads = new DefaultListModel<>();
            for (Download download:
                    downloads.getActiveDownloads()) {
                completedDownloads.addElement(download);
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./completed.jdm"))) {
                outputStream.writeObject(downloads.getCompletedDownloads());
                completedDownloads = new DefaultListModel<>();
            } catch (IOException e1) {
                return false;
            }
            return true;
        }
    }

    public void systemTray(){
        SystemTray st = SystemTray.getSystemTray();
        TrayIcon ti = new TrayIcon(new ImageIcon(ICON_PACK + "download.png").getImage());
        try {
            st.add(ti);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        ti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
            }
        });
        PopupMenu popUp = new PopupMenu();
        ti.setPopupMenu(popUp);
        MenuItem exit = new MenuItem("Quit");
        popUp.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == exit){
                    System.exit(0);
                }
            }
        });
    }

    public static void setLookAndFeel(int choice) {
        switch (choice) {
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

    private class LogoAndTabs extends JPanel{
        private JLabel logo;
        private Tabs tabs;

        LogoAndTabs(DefaultListModel<Component> tabList, DefaultListModel downloadListModel, DefaultListModel completedList){
            setLayout(new BorderLayout());
            setBackground(Color.white);

            logo = new JLabel(new ImageIcon(new ImageIcon(ICON_PACK + "A2 (Another Download Manager).png").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
            add(logo,BorderLayout.NORTH);

            tabs = new Tabs(tabList);
            tabs.setBorder(null);
            add(tabs, BorderLayout.CENTER);

            tabs.getTabs().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1 && e.getClickCount() == 1) {
                        if ((tabs.getTabs().getSelectedValue()).toString().equals("Completed Downloads")) {
                            mainPanel.add(completed, BorderLayout.CENTER);
                            mainPanel.remove(downloadList);
                            downloadList.setVisible(false);
                            completed.setVisible(true);
                            mainPanel.updateUI();
                            revalidate();
                            repaint();
                        }
                        if ((tabs.getTabs().getSelectedValue()).toString().equals("Downloads")) {
                            mainPanel.add(DownloadList.getInstance(downloadListModel), BorderLayout.CENTER);
                            mainPanel.remove(completed);
                            completed.setVisible(false);
                            downloadList.setVisible(true);
                            mainPanel.updateUI();
                            revalidate();
                            repaint();
                        }
                    }
                    SwingUtilities.updateComponentTreeUI(mainPanel);
                }
            });
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
