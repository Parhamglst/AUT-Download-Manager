import GUI.Entity;
import GUI.MainFrame;

import javax.swing.*;

public class Main {
    public static final String ICON_PACK = "./Icons/";

    public static void main(String[] args) {
        MainFrame.setLookAndFeel(0);
        DefaultListModel<Entity> listModel = new DefaultListModel<>();
        listModel.addElement(new Entity("dsa", "dsa"));
        listModel.addElement(new Entity("dsa", "dsa"));
        MainFrame gui = MainFrame.getInstance("Another Download Manager",listModel, listModel);
        gui.setLocationRelativeTo(null);
    }
}
