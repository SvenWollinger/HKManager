package io.wollinger.hkmanager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class SavePanel extends JPanel {
    private SavePanel instance;

    private final JScrollPane parent;
    private final SavePanelRenderer renderer;
    private JLabel title;
    private ImagePanel gear;
    private JPanel buttonPanel;

    public SavePanel(HKManager hkManager, Save save, JScrollPane parent) {
        instance = this;
        this.parent = parent;
        setLayout(new FlowLayout());

        renderer = new SavePanelRenderer(save);

        title = new JLabel(genName(save));
        title.setFont(HKManager.getHKFont().deriveFont(24F));
        title.setHorizontalAlignment(JLabel.CENTER);

        gear = new ImagePanel(ImageManager.gear);
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String newName = (String) JOptionPane.showInputDialog(instance, "Enter new name", "Savegame name", JOptionPane.PLAIN_MESSAGE, new ImageIcon(ImageManager.grub), null, save.getName());
                save.setName(newName);
                title.setText(genName(save));
            }
        });

        buttonPanel = new JPanel(new GridLayout(0, 4));

        if(save.getKind() != Save.KIND.USER0) {
            JButton btn = new JButton("<- Storage");
            btn.addActionListener(e -> hkManager.moveToStorage(save));
            buttonPanel.add(btn);
        } else {
            for(int i = 0; i < 4; i++) {
                if(!hkManager.getLoadedSave(i).isValid()) {
                    int slot = i + 1;
                    JButton btn = new JButton("Slot " + slot + " ->");
                    btn.addActionListener(e -> hkManager.moveToSlot(save, slot));
                    buttonPanel.add(btn);
                } else {
                    JPanel panel = new JPanel();
                    buttonPanel.add(panel);
                }
            }
        }

        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                triggerResize();
            }
        });

        add(title);
        add(gear);
        add(renderer);
        add(buttonPanel);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public void triggerResize() {
        final int safetyMargin = 40;

        renderer.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin, parent.getViewport().getWidth() / 2));
        int titleHeight = title.getFont().getSize();
        title.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin - titleHeight, titleHeight));
        gear.setPreferredSize(new Dimension(titleHeight, titleHeight));
        buttonPanel.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin, 60));

        setPreferredSize(new Dimension(parent.getViewport().getWidth(), (parent.getViewport().getWidth() / 2) + titleHeight + 60 + safetyMargin));

        revalidate();
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

    @Override
    public void paint (Graphics g) {
        super.paint(g);
        //g.drawRect(0 ,0, getWidth(), getHeight());
    }
}
