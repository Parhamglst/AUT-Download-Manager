package GUI;

import javafx.scene.control.Cell;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//TODO:Not so sure whether should I use the File class as Generic Type

/**
 * The singleton class for the download lst containing the not-completed downloads
 */
public class DownloadList extends JList<Entity> {
    public static final String ICON_PACK = "./Icons/";
    private static final Dimension COMMAND_ICON_DIMENSION = new Dimension(17, 17);
    public static Color selectionBG;

    private static DownloadList singleton;

    CellRenderer cellRenderer;

    protected DownloadList(DefaultListModel<Entity> listModel) {
        super(listModel);
        setBackground(new Color(255, 255, 255));
        selectionBG = getSelectionBackground();
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setBorder(new BorderUIResource.LineBorderUIResource(Color.GRAY));
//        setVisibleRowCount(-1);

        cellRenderer = new CellRenderer();

        setCellRenderer(cellRenderer);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cellRenderer.getFileNameAndProgress().setPreferredSize(new Dimension(getWidth() - 60, 40));
                revalidate();
                repaint();

            }
        });
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && singleton.getSelectedIndices().length == 1 && singleton.locationToIndex(e.getPoint()) == singleton.getSelectedIndex()) {
                    CustomMenu customMenu = new CustomMenu();
                    customMenu.show(singleton, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
//                getCellRenderer().getListCellRendererComponent(null,null, getSelectedIndex(),true,true);
            }
        });
    }

    @Override
    public String toString() {
        return "Downloads";
    }

    public static DownloadList getIntsance(DefaultListModel<Entity> listModel) {
        if (singleton == null) {
            singleton = new DownloadList(listModel);
        }
        return singleton;
    }

    @Override
    public ListCellRenderer<? super Entity> getCellRenderer() {
        return super.getCellRenderer();
    }

    private class CellRenderer extends DownloadListContent implements ListCellRenderer<Entity> {

        //TODO:Assign a Generic Type and complete the renderer
        CellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Entity> list, Entity value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(selectionBG);
                setForeground(selectionBG);
                getFileNameAndProgress().setBackground(selectionBG);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
                getFileNameAndProgress().setBackground(list.getBackground());
            }
            setEnabled(list.isEnabled());
            this.setBorder(BorderFactory.createSoftBevelBorder(0));

            return this;
        }
    }

    private class DownloadListContent extends JPanel {
        private JLabel fileIcon;
        private FileNameAndProgress fileNameAndProgress;

        DownloadListContent() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            setBackground(Color.WHITE);

            fileIcon = new JLabel(new ImageIcon(new ImageIcon(ICON_PACK + "internet.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));//TODO:fileIcon should differ between each download format
            add(fileIcon);

            fileNameAndProgress = new FileNameAndProgress("downloading");
            add(fileNameAndProgress);
        }

        public FileNameAndProgress getFileNameAndProgress() {
            return fileNameAndProgress;
        }

        private class FileNameAndProgress extends JPanel {
            private JLabel fileName;
            private JProgressBar downloadProgress;

            FileNameAndProgress(String fileName) {//TODO:progress
                setLayout(new GridLayout(2, 1, 5, 5));
                setBackground(Color.WHITE);

                this.fileName = new JLabel(fileName);
                add(this.fileName);

                downloadProgress = new JProgressBar(0, 100);
                downloadProgress.setValue(50);
                downloadProgress.setStringPainted(true);
                downloadProgress.setString("Speed: " + "154kbps" + "                 Size: " + "154mb" + "                Downloaded: " + "100mb");
                downloadProgress.setBorderPainted(false);
                add(downloadProgress);
            }

            JLabel getFileName() {
                return fileName;
            }

            void setFileName(JLabel fileName) {
                this.fileName = fileName;
            }

            JProgressBar getDownloadProgress() {
                return downloadProgress;
            }

            void setDownloadProgress(JProgressBar downloadProgress) {
                this.downloadProgress = downloadProgress;
            }
        }

        private class CommandButtons extends JPanel {
            private JButton resumeButton;
            private JButton pauseButton;
            private JButton stopButton;

            private ButtonActionListener buttonActionListener;

            CommandButtons() {
                buttonActionListener = new ButtonActionListener();
                setLayout(new FlowLayout());
                setBackground(Color.WHITE);

                resumeButton = new JButton();
                resumeButton.setBackground(Color.WHITE);
                resumeButton.setFocusable(false);
                resumeButton.setBorderPainted(false);
                resumeButton.setSize(COMMAND_ICON_DIMENSION);
                resumeButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "play-button.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                resumeButton.addActionListener(buttonActionListener);
                add(resumeButton);

                pauseButton = new JButton();
                pauseButton.setFocusable(false);
                pauseButton.setBorderPainted(false);
                pauseButton.setBackground(Color.WHITE);
                pauseButton.setSize(COMMAND_ICON_DIMENSION);
                pauseButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "pause.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                pauseButton.addActionListener(buttonActionListener);
                add(pauseButton);

                stopButton = new JButton();
                stopButton.setFocusable(false);
                stopButton.setBorderPainted(false);
                stopButton.setBackground(Color.WHITE);
                stopButton.setSize(COMMAND_ICON_DIMENSION);
                stopButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "stop.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                stopButton.addActionListener(buttonActionListener);
                add(stopButton);
            }

            public JButton getPauseButton() {
                return pauseButton;
            }

            public JButton getResumeButton() {
                return resumeButton;
            }

            public JButton getStopButton() {
                return stopButton;
            }

            private class ButtonActionListener implements ActionListener {

                public ButtonActionListener() {
                    super();
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == resumeButton) {
                        //TODO:Resume the download
                        System.out.println("Resumed");
                    }
                    if (e.getSource() == stopButton) {
                        //TODO:terminate the download
                        System.out.println("Stop");
                    }
                    if (e.getSource() == pauseButton) {
                        //TODO:Pause the download
                        System.out.println("pause");
                    }
                }
            }
        }

    }

    private class CustomMenu extends JPopupMenu {
        private CustomMenuItem customMenuItem;
        private DownloadInfo downloadInfo;

        CustomMenu() {
            customMenuItem = new CustomMenuItem();
            add(customMenuItem);

            addSeparator();

            downloadInfo = new DownloadInfo("da", "das", "dsjwa", "dsa", "dsa", "dsa");//TODO:Complete
            add(downloadInfo);
        }

        private class DownloadInfo extends JMenuItem implements ActionListener {
            FileName fileName;
            JTextField downloadDir;
            SaveTo saveTo;
            JLabel fileType;
            JLabel size;
            JLabel downloadStatues;
            JButton ok;

            DownloadInfo(String fileName, String fileType, String downloadDir, String size, String downloadStatues, String saveDir) {
                setPreferredSize(new Dimension(500, 300));
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                this.setEnabled(false);

                this.fileName = new FileName(fileName, fileType);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.weightx = 1;
                gbc.weighty = 4;
                add(this.fileName, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.WEST;
                add(new JLabel("File type:    "), gbc);
                this.fileType = new JLabel(fileType);
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 4;
                gbc.anchor = GridBagConstraints.WEST;
                add(this.fileType, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.WEST;
                add(new JLabel("File size:    "), gbc);
                this.size = new JLabel(size);
                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridwidth = 4;
                gbc.anchor = GridBagConstraints.WEST;
                add(this.size, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.WEST;
                add(new JLabel("Download address:    "), gbc);
                this.downloadDir = new JTextField(downloadDir);
                this.downloadDir.setEditable(false);
                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.gridwidth = 4;
                gbc.anchor = GridBagConstraints.WEST;
                add(this.downloadDir, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.WEST;
                add(new JLabel("Download statues:    "), gbc);
                this.downloadStatues = new JLabel();
                this.downloadStatues.setText(downloadStatues);//TODO:Improvement(show the completion in percentage)
                gbc.gridx = 1;
                gbc.gridy = 4;
                gbc.gridwidth = 4;
                gbc.anchor = GridBagConstraints.WEST;
                add(this.downloadStatues, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.WEST;
                saveTo = new SaveTo(saveDir);
                add(new JLabel("Save to:    "), gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                gbc.gridwidth = 4;
                gbc.anchor = GridBagConstraints.WEST;
                add(saveTo, gbc);
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
                    setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

                    this.saveDir = new JTextField();
                    this.saveDir.setText(saveDir);
                    this.saveDir.setPreferredSize(new Dimension(315,23));
                    this.saveDir.setEditable(false);
                    add(this.saveDir);

                    this.changeSaveDir = new JButton("Change");
                    add(changeSaveDir);
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
                    icon = new JLabel(fileName);//TODO:ImageIcon
                    add(icon);

                    this.fileName = new JLabel(fileName);
                    add(this.fileName);
                }
            }

        }

        private class CustomMenuItem extends JMenuItem {
            private JButton resumeButton;
            private JButton pauseButton;
            private JButton stopButton;

            CustomMenuItem() {
                setLayout(new GridLayout(1, 3));
                setSelected(false);
                setEnabled(false);
                setPreferredSize(new Dimension(100, 30));

                resumeButton = new JButton();
//                resumeButton.setBackground(Color.white);
                resumeButton.setFocusable(false);
                resumeButton.setBorderPainted(false);
                resumeButton.setSize(COMMAND_ICON_DIMENSION);
                resumeButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "play-button.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                resumeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Resumed");
                    }
                });
                add(resumeButton);

                pauseButton = new JButton();
                pauseButton.setFocusable(false);
                pauseButton.setBorderPainted(false);
//                pauseButton.setBackground(Color.WHITE);
                pauseButton.setSize(COMMAND_ICON_DIMENSION);
                pauseButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "pause.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                pauseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Pause");
                    }
                });
                add(pauseButton);

                stopButton = new JButton();
                stopButton.setFocusable(false);
                stopButton.setBorderPainted(false);
//                stopButton.setBackground(Color.WHITE);
                stopButton.setSize(COMMAND_ICON_DIMENSION);
                stopButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "stop.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                stopButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("stop");
                    }
                });
                add(stopButton);
            }
        }
    }
}
