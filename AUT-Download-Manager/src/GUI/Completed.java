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
public class Completed extends JList<Entity> {
    public static final String ICON_PACK = "./Icons/";
    public static final Dimension COMMAND_ICON_DIMENSION = new Dimension(17, 17);

    private JList<File> downloadList;
    private File[] downloadListArray;

    public Completed(DefaultListModel<Entity> listModel) {
        super(listModel);
        setBackground(new Color(255, 255, 255));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setVisibleRowCount(-1);
        setCellRenderer(new CellRenderer());
    }

    @Override
    public String toString() {
        return "Completed Downloads";
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
        JLabel fileName;
        CommandButtons commandButtons;

        DownloadListContent() {
            setLayout(new FlowLayout());
            setBackground(Color.WHITE);

            fileIcon = new JLabel(new ImageIcon(new ImageIcon(ICON_PACK + "internet.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));//TODO:fileIcon should differ between each download format
            add(fileIcon);

            fileName = new JLabel("filename");
            add(fileName);

            commandButtons = new CommandButtons();
            add(commandButtons);
        }

        private class CommandButtons extends JPanel {
            private JButton open;
            private JButton openFolder;
            private JButton delete;

            CommandButtons() {
                setLayout(new FlowLayout());
                setBackground(Color.WHITE);

                open = new JButton();
                open.setSize(COMMAND_ICON_DIMENSION);
                open.setFocusable(false);
                open.setBorderPainted(false);
                open.setBackground(Color.WHITE);
                open.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "file-1.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));
                open.addActionListener(new ButtonActionListener());
                add(open);

                openFolder = new JButton();
                openFolder.setSize(COMMAND_ICON_DIMENSION);
                openFolder.setFocusable(false);
                openFolder.setBorderPainted(false);
                openFolder.setBackground(Color.WHITE);
                openFolder.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "folder-13.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));
                openFolder.addActionListener(new ButtonActionListener());
                add(openFolder);

                delete = new JButton();
                delete.setSize(COMMAND_ICON_DIMENSION);
                delete.setFocusable(false);
                delete.setBorderPainted(false);
                delete.setBackground(Color.WHITE);
                delete.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "error.png").getImage().getScaledInstance(17,17,Image.SCALE_SMOOTH)));
                delete.addActionListener(new ButtonActionListener());
                add(delete);
            }

            private class ButtonActionListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == open) {
                        //TODO:Open the downloaded file
                    }
                    if (e.getSource() == openFolder) {
                        //TODO:Open the downloaded file's folder
                    }
                    if (e.getSource() == delete) {
                        //TODO:Delete the download from list
                    }
                }
            }
        }
    }
}
