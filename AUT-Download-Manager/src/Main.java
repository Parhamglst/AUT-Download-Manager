import Utils.Download;
import GUI.MainFrame;
import Utils.Downloads;

import javax.swing.*;
import java.net.URL;

public class Main {
    public static final String ICON_PACK = "./Icons/";

    public static void main(String[] args) {
        MainFrame.setLookAndFeel(4);
        MainFrame gui = MainFrame.getInstance("Another Download Manager");
        gui.setLocationRelativeTo(null);
    }
}
