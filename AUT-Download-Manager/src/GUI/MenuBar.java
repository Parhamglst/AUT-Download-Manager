package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar implements ActionListener {
    private JMenuItem newDownload;
    private JMenuItem resume;
    private JMenuItem pause;
    private JMenuItem cancel;
    private JMenuItem remove;
    private JMenuItem settings;
    private JMenuItem quit;
    private JMenuItem about;


    public MenuBar(){
        JMenu file = new JMenu("File");
        add(file);
        file.setVisible(true);
        file.setMnemonic(KeyEvent.VK_F);
        setBackground(Color.white);

        newDownload = new JMenuItem("Add URL");
        newDownload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.ALT_MASK));
        newDownload.getAccessibleContext().setAccessibleDescription("Add a new Download");
        newDownload.addActionListener(this);
        file.add(newDownload);

        resume = new JMenuItem("Resume");
        resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.ALT_MASK));
        resume.getAccessibleContext().setAccessibleDescription("Resume the selected download file");
        resume.addActionListener(this);
        file.add(resume);

        pause = new JMenuItem("Pause");
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.ALT_MASK));
        pause.getAccessibleContext().setAccessibleDescription("Pause the selected download file");
        pause.addActionListener(this);
        file.add(pause);

        cancel = new JMenuItem("Stop");
        cancel.getAccessibleContext().setAccessibleDescription("Cancel the download progress of the selected file");
        cancel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
        cancel.addActionListener(this);
        file.add(cancel);

        remove = new JMenuItem("Remove");
        remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.ALT_MASK));
        remove.getAccessibleContext().setAccessibleDescription("Remove the selected file from download list");
        remove.addActionListener(this);
        file.add(remove);

        settings = new JMenuItem("Settings");
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
        settings.addActionListener(this);
        file.add(settings);

        quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.ALT_MASK));
        quit.addActionListener(this);
        file.add(quit);
//        #######################################################################################################################33
        JMenu help = new JMenu("Help");
        add(help);

        about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new About();
            }
        });
        help.add(about);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newDownload){
            //TODO:add a new download
        }
        if(e.getSource() == resume){
            //TODO:resume download
        }
        if(e.getSource() == remove){
            //TODO:remove download
        }
        if(e.getSource() == quit){
            System.exit(0);
        }
        if(e.getSource() == settings){
            //TODO:open the settings page
        }
        if(e.getSource() == pause){
            //TODO:pause the download
        }
        if(e.getSource() == cancel){
            //TODO:cancel the download
        }
    }
    private class About extends JFrame{
        About(){
            setVisible(true);
            setLayout(new GridLayout(10,1));
            setSize(new Dimension(500,200));
            setLocationRelativeTo(null);
            add(new JLabel("Developed by Parham Golestaneh"));
            add(new JLabel("Project's Starting Day: 5\\16\\2018"));
            add(new JLabel("Student No.: 9631062"));
        }
    }
}
