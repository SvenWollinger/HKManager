package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SavePanel extends JPanel {
    public SavePanel(Save save, JScrollPane parent) {
        setLayout(new FlowLayout());

        SavePanelRenderer renderer = new SavePanelRenderer(save);

        String name = "";

        switch(save.getKind()) {
            case USER1: name = "Slot 1"; break;
            case USER2: name = "Slot 2"; break;
            case USER3: name = "Slot 3"; break;
            case USER4: name = "Slot 4"; break;
            case USER0: name = ""; break;
        }

        if(save.getName() != null && !save.getName().isEmpty()) {
            name = save.getName() + " (" + name + ")";
        }

        JLabel title = new JLabel(name);
        title.setFont(HKManager.getHKFont().deriveFont(20F));
        title.setHorizontalAlignment(JLabel.CENTER);

        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPreferredSize(new Dimension(parent.getViewport().getWidth(), parent.getViewport().getWidth() / 2));
                renderer.setPreferredSize(new Dimension(parent.getViewport().getWidth() - 10, parent.getViewport().getWidth() / 2));
                title.setPreferredSize(new Dimension(parent.getViewport().getWidth() - 10, title.getHeight()));

                revalidate();
            }
        });

        add(title);

        add(renderer);
    }
}
