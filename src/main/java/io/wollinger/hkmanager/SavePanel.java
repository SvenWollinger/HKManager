package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        title.setFont(HKManager.getHKFont().deriveFont(24F));
        title.setHorizontalAlignment(JLabel.CENTER);

        ImagePanel gear = new ImagePanel(ImageManager.gear);
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("hello world");
            }
        });

        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                final int safetyMargin = 20;

                renderer.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin, parent.getViewport().getWidth() / 2));
                int titleHeight = title.getFont().getSize();
                title.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin - titleHeight, titleHeight));
                gear.setPreferredSize(new Dimension(titleHeight, titleHeight));
                setPreferredSize(new Dimension(parent.getViewport().getWidth(), (parent.getViewport().getWidth() / 2) + titleHeight));

                revalidate();
            }
        });

        add(title);
        add(gear);

        add(renderer);
    }
}
