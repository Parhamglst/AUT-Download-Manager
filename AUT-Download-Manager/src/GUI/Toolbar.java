package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Toolbar extends JToolBar implements ActionListener {
    public static final String ICON_PACK = "./Icons/";
    private static final Dimension TOOLBAR_BUTTONS_SIZE = new Dimension(40,40);

    private JButton newDownload;
    private JButton resume;
    private JButton pause;
    private JButton cancel;
    private JButton remove;
    private JButton settings;
    public Toolbar() {
        setPreferredSize(new Dimension(600,40));
        setFloatable(false);
        setBackground(Color.white);
        setRollover(false);

        add(new JLabel(new ImageIcon(new ImageIcon(ICON_PACK + "A (Another Download Manager).png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH))));

        newDownload = new JButton(new ImageIcon(new ImageIcon(ICON_PACK +"download.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        newDownload.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        newDownload.addActionListener(this);
        newDownload.setToolTipText("New Download");
        newDownload.setBorderPainted(false);
        newDownload.setBackground(Color.WHITE);
        newDownload.setFocusable(false);
        add(newDownload);

        resume = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "play-button.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        resume.addActionListener(this);
        resume.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        resume.setToolTipText("Resume");
        resume.setBorderPainted(false);
        resume.setBackground(Color.white);
        resume.setFocusable(false);
        add(resume);

        pause = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "pause.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        pause.addActionListener(this);
        pause.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        pause.setToolTipText("Pause");
        pause.setBorderPainted(false);
        pause.setBackground(Color.white);
        pause.setFocusable(false);
        add(pause);

        cancel = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "stop.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        cancel.addActionListener(this);
        cancel.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        cancel.setToolTipText("Stop");
        cancel.setBorderPainted(false);
        cancel.setBackground(Color.white);
        cancel.setFocusable(false);
        add(cancel);

        remove = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "multiply.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        remove.addActionListener(this);
        remove.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        remove.setToolTipText("Remove from Download List");
        remove.setBorderPainted(false);
        remove.setBackground(Color.white);
        remove.setFocusable(false);
        add(remove);

        settings = new JButton(new ImageIcon(new ImageIcon(ICON_PACK + "settings-4.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        settings.addActionListener(this);
        settings.setPreferredSize(TOOLBAR_BUTTONS_SIZE);
        settings.setToolTipText("Settings");
        settings.setBackground(Color.white);
        settings.setBorderPainted(false);
        settings.setFocusable(false);
        add(settings);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newDownload){
            new NewDownload();
        }
        if(e.getSource() == resume){
            //TODO:resume download
        }
        if(e.getSource() == remove){
            //TODO:remove download
        }
        if(e.getSource() == settings){
            Settings.getInstance();
        }
        if(e.getSource() == pause){
            //TODO:pause the download
        }
        if(e.getSource() == cancel){
            //TODO:cancel the download
        }
    }
}
