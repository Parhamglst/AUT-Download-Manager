package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static GUI.DownloadList.selectionBG;


//TODO:Not so sure whether should I use the File class as Generic Type
public class Completed extends JList<Entity> {
    public static final String ICON_PACK = "./Icons/";
    public static final Dimension COMMAND_ICON_DIMENSION = new Dimension(17, 17);

    private static Completed singleton;

    CellRenderer cellRenderer;
    private JList<File> Completed;
    private File[] CompletedArray;

    private Completed(DefaultListModel<Entity> listModel) {
        super(listModel);
        setBackground(new Color(255, 255, 255));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setVisibleRowCount(-1);
        cellRenderer = new CellRenderer();
        setCellRenderer(cellRenderer);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cellRenderer.setPreferredSize(new Dimension(getWidth() - 60, 40));
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
        return "Completed Downloads";
    }

    private class CellRenderer extends CompletedContent implements ListCellRenderer<Entity> {

        //TODO:Assign a Generic Type and complete the renderer
        CellRenderer() {
            super("dsa","dsa","dsa","dsa","dsa");
            setOpaque(true);
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends Entity> list, Entity value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(selectionBG);
                setForeground(selectionBG);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
//            this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.LIGHT_GRAY));
            this.setBorder(BorderFactory.createSoftBevelBorder(0));

            return this;
        }
    }

    public static Completed getInstance(DefaultListModel<Entity> listModel){
        if(singleton == null){
            singleton = new Completed(listModel);
        }
        return singleton;
    }

    private class CustomMenu extends JPopupMenu {
        private Completed.CustomMenu.CustomMenuItem customMenuItem;
        private Completed.CustomMenu.DownloadInfo downloadInfo;

        CustomMenu() {
            customMenuItem = new Completed.CustomMenu.CustomMenuItem();
            add(customMenuItem);

            addSeparator();

            downloadInfo = new Completed.CustomMenu.DownloadInfo("da", "das", "dsjwa", "dsa", "dsa", "dsa");//TODO:Complete
            add(downloadInfo);
        }

        private class DownloadInfo extends JMenuItem implements ActionListener {
            Completed.CustomMenu.DownloadInfo.FileName fileName;
            JTextField downloadDir;
            Completed.CustomMenu.DownloadInfo.SaveTo saveTo;
            JLabel fileType;
            JLabel size;
            JLabel downloadStatues;
            JButton ok;

            DownloadInfo(String fileName, String fileType, String downloadDir, String size, String downloadStatues, String saveDir) {
                setPreferredSize(new Dimension(500, 300));
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                this.setEnabled(false);

                this.fileName = new Completed.CustomMenu.DownloadInfo.FileName(fileName, fileType);
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
                saveTo = new Completed.CustomMenu.DownloadInfo.SaveTo(saveDir);
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

            private class SaveTo extends JPanel {
                JTextField saveDir;

                SaveTo(String saveDir) {
                    setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

                    this.saveDir = new JTextField();
                    this.saveDir.setText(saveDir);
                    this.saveDir.setPreferredSize(new Dimension(315,23));
                    this.saveDir.setEditable(false);
                    add(this.saveDir);
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
            private JButton open;
            private JButton openF;
            private JButton lRemove;

            CustomMenuItem() {
                setLayout(new GridLayout(1, 3));
                setSelected(false);
                setEnabled(false);
                setPreferredSize(new Dimension(100, 30));

                open = new JButton();
//                open.setBackground(Color.white);
                open.setFocusable(false);
                open.setBorderPainted(false);
                open.setSize(COMMAND_ICON_DIMENSION);
                open.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "file-1.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                open.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Open");
                    }
                });
                add(open);

                openF = new JButton();
                openF.setFocusable(false);
                openF.setBorderPainted(false);
//                openF.setBackground(Color.WHITE);
                openF.setSize(COMMAND_ICON_DIMENSION);
                openF.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "folder-13.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                openF.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Open Folder");
                    }
                });
                add(openF);

                lRemove = new JButton();
                lRemove.setFocusable(false);
                lRemove.setBorderPainted(false);
//                lRemove.setBackground(Color.WHITE);
                lRemove.setSize(COMMAND_ICON_DIMENSION);
                lRemove.setIcon(new ImageIcon(new ImageIcon(ICON_PACK + "error.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
                lRemove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Delete from List");
                    }
                });
                add(lRemove);
            }
        }
    }

    private class CompletedContent extends JPanel {
        JLabel fileIcon;
        JTextField downloadDir;
        JTextField saveTo;
        JLabel fileType;
        JLabel size;

        CompletedContent(String fileName, String fileType, String downloadDir, String size, String saveDir) {
            Completed.this.setBorder(new BorderUIResource.LineBorderUIResource(Color.GRAY));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1;
            gbc.insets = new Insets(5,5,5,5);
            setBackground(Color.WHITE);

            fileIcon = new JLabel(new ImageIcon(new ImageIcon(ICON_PACK + "internet.png").getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));//TODO:fileIcon should differ between each download format
            gbc.gridy = 0;
            gbc.gridx = 0;
            add(fileIcon,gbc);

            gbc.gridx = 1;
            add(new JLabel(fileName),gbc);

            this.fileType = new JLabel(fileType + " file");
            gbc.gridx = 2;
            add(this.fileType,gbc);

            this.size = new JLabel(size + "megabytes");
            gbc.gridx = 3;
            add(this.size,gbc);

            this.downloadDir = new JTextField(downloadDir);
            this.downloadDir.setEditable(false);
            gbc.gridx = 4;
            add(this.downloadDir,gbc);

            saveTo = new JTextField(saveDir);
            gbc.gridx = 5;
            add(saveTo,gbc);
        }
    }
}
