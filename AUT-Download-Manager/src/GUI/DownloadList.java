package GUI;

import javafx.scene.control.Cell;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

//TODO:Not so sure whether should I use the File class as Generic Type
public class DownloadList extends JList<Entity> {
    public static final String ICON_PACK = "./Icons/";
    private static final Dimension COMMAND_ICON_DIMENSION = new Dimension(17, 17);

    private static DownloadList singleton;

    protected DownloadList(DefaultListModel<Entity> listModel) {
        super(listModel);
        setBackground(new Color(255, 255, 255));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setBorder(new BorderUIResource.LineBorderUIResource(Color.GRAY));
        setVisibleRowCount(-1);
        setCellRenderer(new CellRenderer());
    }

    @Override
    public String toString() {
        return "Downloads";
    }

    private class CellRenderer extends DownloadListContent implements ListCellRenderer<Entity> {

        //TODO:Assign a Generic Type and complete the renderer
        CellRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends Entity> list, Entity value, int index, boolean isSelected, boolean cellHasFocus) {
//            if (isSelected) {
//                setBackground(list.getSelectionBackground());
//                setForeground(list.getSelectionForeground());
//            } else {
//                setBackground(list.getBackground());
//                setForeground(list.getForeground());
//            }
//            setEnabled(list.isEnabled());
            return this;
        }
    }

    private class DownloadListContent extends JPanel {
        JLabel fileIcon;
        FileNameAndProgress fileNameAndProgress;
        CommandButtons commandButtons;

        DownloadListContent() {
            setLayout(new FlowLayout());
            setBackground(Color.WHITE);

            fileIcon = new JLabel(new ImageIcon(new ImageIcon(ICON_PACK + "internet.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));//TODO:fileIcon should differ between each download format
            add(fileIcon);

            fileNameAndProgress = new FileNameAndProgress("downloading");
            add(fileNameAndProgress);

            commandButtons = new CommandButtons();
            add(commandButtons);
        }

        private class FileNameAndProgress extends JPanel {
            private JLabel fileName;
            private JProgressBar downloadProgress;

            FileNameAndProgress(String fileName) {//TODO:progress
                setLayout(new GridLayout(2, 1));
                setBackground(Color.WHITE);

                this.fileName = new JLabel(fileName);
                add(this.fileName);

                downloadProgress = new JProgressBar(0,100);
                downloadProgress.setValue(50);
                downloadProgress.setStringPainted(true);
                downloadProgress.setPreferredSize(new Dimension(700,1));
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

            CommandButtons() {
                setLayout(new FlowLayout());
                setBackground(Color.WHITE);

                resumeButton = new JButton();
                resumeButton.setBackground(Color.WHITE);
                resumeButton.setFocusable(false);
                resumeButton.setBorderPainted(false);
                resumeButton.setSize(COMMAND_ICON_DIMENSION);
                resumeButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "play-button.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));
                resumeButton.addActionListener(new ButtonActionListener());
                add(resumeButton);

                pauseButton = new JButton();
                pauseButton.setFocusable(false);
                pauseButton.setBorderPainted(false);
                pauseButton.setBackground(Color.WHITE);
                pauseButton.setSize(COMMAND_ICON_DIMENSION);
                pauseButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "pause.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));
                pauseButton.addActionListener(new ButtonActionListener());
                add(pauseButton);

                stopButton = new JButton();
                stopButton.setFocusable(false);
                stopButton.setBorderPainted(false);
                stopButton.setBackground(Color.WHITE);
                stopButton.setSize(COMMAND_ICON_DIMENSION);
                stopButton.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "stop.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));
                stopButton.addActionListener(new ButtonActionListener());
                add(stopButton);
            }

            private class ButtonActionListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == resumeButton) {
                        //TODO:Resume the download
                    }
                    if (e.getSource() == stopButton) {
                        //TODO:terminate the download
                    }
                    if (e.getSource() == pauseButton) {
                        //TODO:Pause the download
                    }
                }
            }
        }
    }
    public static DownloadList getIntsance(DefaultListModel<Entity> listModel){
        if(singleton == null){
            singleton = new DownloadList(listModel);
        }
        return singleton;
    }
}
