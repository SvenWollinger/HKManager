package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SavePanel extends JPanel {
    public SavePanel(Save save, JScrollPane parent) {
        SavePanelRenderer renderer = new SavePanelRenderer(save);

        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                renderer.setPreferredSize(new Dimension(parent.getViewport().getWidth(), parent.getViewport().getWidth() / 2));
                revalidate();
            }
        });

        add(renderer);
    }
}
