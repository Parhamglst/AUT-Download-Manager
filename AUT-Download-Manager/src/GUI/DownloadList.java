package GUI;

import javafx.scene.control.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

//TODO:Not so sure whether should I use the File class as Generic Type
public class DownloadList extends JList<File> {
    public static final String ICON_PACK = "./Icons/";
    public static final Dimension COMMAND_ICON_DIMENSION = new Dimension(10, 10);

    private JList<File> downloadList;
    private File[] downloadListArray;

    private DownloadList() {
        downloadList = new DownloadList();
        downloadList.setBackground(new Color(255, 255, 255));
        downloadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        downloadList.setVisibleRowCount(-1);
        downloadList.setCellRenderer(new CellRenderer());
        downloadList.addMouseListener(new CustomMouseAdapter());
    }

    private class CustomMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON2) {

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
                add(this.downloadStatues,10);

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

    private class CellRenderer extends DownloadListContent implements ListCellRenderer {

        //TODO:Assign a Generic Type and complete the renderer
        CellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {


            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            return this;
        }
    }

    private class DownloadListContent extends JPanel {
        JButton fileIcon;
        FileNameAndProgress fileNameAndProgress;
        CommandButtons commandButtons;

        DownloadListContent() {
            setLayout(new FlowLayout());

            fileIcon = new JButton("", new ImageIcon());//TODO:fileIcon should differ between each download format
            add(fileIcon);

            fileNameAndProgress = new FileNameAndProgress();
            add(fileNameAndProgress);

            commandButtons = new CommandButtons();
            add(commandButtons);
        }

        private class FileNameAndProgress extends JPanel {
            private JLabel fileName;
            private JProgressBar downloadProgress;

            FileNameAndProgress() {
                setLayout(new GridLayout(2, 1));

                fileName = new JLabel();
                add(fileName);

                downloadProgress = new JProgressBar();
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

                resumeButton = new JButton("Resume");
                resumeButton.setSize(COMMAND_ICON_DIMENSION);
                resumeButton.setIcon(new ImageIcon(ICON_PACK + "play-button.png"));
                resumeButton.addActionListener(new ButtonActionListener());
                add(resumeButton);

                pauseButton = new JButton("Pause");
                pauseButton.setSize(COMMAND_ICON_DIMENSION);
                pauseButton.setIcon(new ImageIcon(ICON_PACK + "pause.png"));
                pauseButton.addActionListener(new ButtonActionListener());
                add(pauseButton);

                stopButton = new JButton("Stop");
                stopButton.setSize(COMMAND_ICON_DIMENSION);
                stopButton.setIcon(new ImageIcon(ICON_PACK + "stop.png"));
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
}
