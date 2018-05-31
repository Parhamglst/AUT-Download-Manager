package GUI;

import Utils.Download;
import Utils.Downloads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.logging.Level;

public class Toolbar extends JToolBar implements ActionListener {
    public static final String ICON_PACK = "./Icons/";
    private static final Dimension TOOLBAR_BUTTONS_SIZE = new Dimension(40, 40);

    private JButton newDownload;
    private JButton resume;
    private JButton pause;
    private JButton cancel;
    private JButton remove;
    private JButton settings;
    private Search searchField;

    public Toolbar() {
        setPreferredSize(new Dimension(600, 70));
        setFloatable(false);
        setBackground(Color.white);
        setRollover(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        newDownload = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "download.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        newDownload.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        newDownload.addActionListener(this);
        newDownload.setToolTipText("New Download");
        newDownload.setBorderPainted(false);
        newDownload.setBackground(Color.WHITE);
        newDownload.setFocusable(false);
        add(newDownload);

        resume = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "play-button.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        resume.addActionListener(this);
        resume.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        resume.setToolTipText("Resume");
        resume.setBorderPainted(false);
        resume.setBackground(Color.white);
        resume.setFocusable(false);
        add(resume);

        pause = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "pause.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        pause.addActionListener(this);
        pause.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        pause.setToolTipText("Pause");
        pause.setBorderPainted(false);
        pause.setBackground(Color.white);
        pause.setFocusable(false);
        add(pause);

        cancel = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "stop.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        cancel.addActionListener(this);
        cancel.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        cancel.setToolTipText("Stop");
        cancel.setBorderPainted(false);
        cancel.setBackground(Color.white);
        cancel.setFocusable(false);
        add(cancel);

        remove = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "multiply.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        remove.addActionListener(this);
        remove.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        remove.setToolTipText("Remove from Download List");
        remove.setBorderPainted(false);
        remove.setBackground(Color.white);
        remove.setFocusable(false);
        add(remove);

        settings = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "settings-4.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        settings.addActionListener(this);
        settings.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        settings.setToolTipText("Settings");
        settings.setBackground(Color.white);
        settings.setBorderPainted(false);
        settings.setFocusable(false);
        add(settings);

        searchField = new Search();
        add(searchField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newDownload) {
            new NewDownload();
        }
        if (e.getSource() == resume) {
            //TODO:resume download
        }
        if (e.getSource() == remove) {
            if (DownloadList.getInstance(null).isActivePanel()) {
                List<Download> list = DownloadList.getInstance(null).getSelectedValuesList();
                for (Download download :
                        list) {
                    Downloads.getInstance().remove(download, 0);
                }
                MainFrame.getInstance(null).repaint();
            }
            if (Completed.getInstance(null).isActivePanel()) {
                List<Download> list = Completed.getInstance(null).getSelectedValuesList();
                for (Download download :
                        list) {
                    Downloads.getInstance().remove(download, 1);
                }
                MainFrame.getInstance(null).repaint();
            }
        }
        if (e.getSource() == settings) {
            Settings.getInstance();
        }
        if (e.getSource() == pause) {
            //TODO:pause the download
        }
        if (e.getSource() == cancel) {
            //TODO:cancel the download
        }
    }

    private class Search extends JTextField {
        public Search() {
            Dimension resolution = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
            setPreferredSize(new Dimension((int) (resolution.getWidth() / 8), 20));
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String curTextField = getText();
                    JPopupMenu results = new JPopupMenu("Results");
                    add(results);
                    results.setFocusable(false);
                    ArrayList<Download> activeDownloads = Downloads.getInstance().getActiveDownloads();
                    for (Download download :
                            activeDownloads) {
                        if (download.getName().contains(curTextField) || download.getURL().getPath().contains(curTextField))
                            results.add(new result(download, curTextField));
                    }
                    if(results.getSubElements().length != 0)    results.addSeparator();
                    ArrayList<Download> completedDownloads = Downloads.getInstance().getCompletedDownloads();
                    for (Download download :
                            completedDownloads) {
                        if (download.getName().contains(curTextField) || download.getURL().getPath().contains(curTextField))
                            results.add(new result(download, curTextField));
                    }
                    if (results.getSubElements().length == 0) {
                        JMenuItem noResults = new JMenuItem("<No Results>");
                        noResults.setEnabled(false);
                        results.add(noResults);

                        results.show((JTextField) e.getSource(), getX() - 277, getY()+2);
                        results.setPopupSize(new Dimension((int) (resolution.getWidth() / 8), 100));
                        results.setDefaultLightWeightPopupEnabled(false);
                    }
                }
            });
        }

        private class result extends JMenuItem {
            private JLabel icon;
            private JPanel nameAndURL;


            result(Download download, String searchValue) {
                setLayout(new FlowLayout(FlowLayout.LEFT));

                icon = new JLabel();
                icon.setIcon(download.getIcon());
                add(icon);

                nameAndURL = new JPanel();
                nameAndURL.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.WEST;

                gbc.gridx = 0;
                gbc.gridy = 0;
                if (download.getName().contains(searchValue))
                    nameAndURL.add(new resultText(download.getName(), searchValue), gbc);
                else nameAndURL.add(new JLabel(download.getName()), gbc);

                gbc.gridy = 1;
                gbc.gridx = 0;
                if (download.getURL().getPath().contains(searchValue))
                    nameAndURL.add(new resultText(download.getURL().getPath(), searchValue), gbc);
                else nameAndURL.add(new JLabel(download.getURL().getPath()), gbc);

                setToolTipText("Date added: " + download.getDateAdded() + "\tLast time resumed: " + download.getLastTimeResumed());
            }

            private class resultText extends JEditorPane {
                resultText(String container, String containee) {
                    String[] temp = container.split(containee);
                    setContentType("text/plain");
                    setText(temp[0] + "<font color=\"red\"><b>" + containee + "</b></font>" + temp[1]);
                }
            }
        }
    }
}
