package GUI;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

/**
 * Tbas class cotaining
 */
public class Tabs extends JPanel{
    private JList tabs;
    public Tabs(ListModel tabList){
        tabs = new JList(tabList);
        add(tabs);
        setBackground(Color.WHITE);
        setBorder(new BorderUIResource.LineBorderUIResource(Color.GRAY));
    }

    public JList getTabs() {
        return tabs;
    }
}
