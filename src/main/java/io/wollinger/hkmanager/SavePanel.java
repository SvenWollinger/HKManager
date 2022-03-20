package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SavePanel extends JPanel {
    private SavePanel instance;

    public SavePanel(Save save, JScrollPane parent) {
        instance = this;
        setLayout(new FlowLayout());

        SavePanelRenderer renderer = new SavePanelRenderer(save);

        JLabel title = new JLabel(genName(save));
        title.setFont(HKManager.getHKFont().deriveFont(24F));
        title.setHorizontalAlignment(JLabel.CENTER);

        ImagePanel gear = new ImagePanel(ImageManager.gear);
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String newName = (String) JOptionPane.showInputDialog(instance, "Enter new name", "Savegame name", JOptionPane.PLAIN_MESSAGE, new ImageIcon(ImageManager.geo), null, save.getName());
                save.setName(newName);
                title.setText(genName(save));
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

    public String genName(Save save) {
        String name = "";

        switch(save.getKind()) {
            case USER1: name = "Slot 1"; break;
            case USER2: name = "Slot 2"; break;
            case USER3: name = "Slot 3"; break;
            case USER4: name = "Slot 4"; break;
            case USER0: name = ""; break;
        }

        if(save.getName() != null && !save.getName().isEmpty()) {
            if(save.getKind() != Save.KIND.USER0)
                name = save.getName() + " (" + name + ")";
            else
                name = save.getName();
        }
        return name;
    }
}
