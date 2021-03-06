package io.wollinger.hkmanager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SavePanel extends JPanel {
    private final SavePanel instance;

    private final JScrollPane parent;
    private final SavePanelRenderer renderer;
    private final JLabel title;
    private final ImagePanel folder;
    private final ImagePanel gear;
    private final ImagePanel trash;
    private final JPanel buttonPanel;

    public SavePanel(HKManager hkManager, Save save, JScrollPane parent) {
        instance = this;
        this.parent = parent;
        setLayout(new FlowLayout());

        renderer = new SavePanelRenderer(save);

        title = new JLabel(genName(save));
        title.setFont(HKManager.getHKFont().deriveFont(28F));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);

        folder = new ImagePanel(ImageManager.folder);
        folder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                File f = save.getFiles().get(0).getParentFile();
                try {
                    Desktop.getDesktop().open(save.getFiles().get(0).getParentFile());
                } catch (IOException ex) {
                    HKManager.errorMessage("Couldnt open folder for file <" + f.getAbsolutePath() + ">! Message: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        gear = new ImagePanel(ImageManager.gear);
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String newName = (String) JOptionPane.showInputDialog(instance, "Enter new name", "Savegame name", JOptionPane.PLAIN_MESSAGE, new ImageIcon(ImageManager.grub), null, save.getName());
                save.setName(newName);
                title.setText(genName(save));
            }
        });

        trash = new ImagePanel(ImageManager.trash);
        trash.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this savegame? (It will still be backed up)", "Delete Savegame", JOptionPane.YES_NO_OPTION);
                if(result != 0)
                    return;

                //If this fails we exit before deleting the save file, dont worry.
                hkManager.moveToBackup(save);

                hkManager.deleteSaveFiles(save, true);

                hkManager.loadSaves();
                hkManager.resizePanels();
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
        add(folder);
        add(gear);
        add(trash);
        add(renderer);
        add(buttonPanel);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public void triggerResize() {
        final int safetyMargin = 40;

        renderer.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin, parent.getViewport().getWidth() / 2));
        int titleHeight = title.getFont().getSize() + 6;
        title.setPreferredSize(new Dimension(parent.getViewport().getWidth() - safetyMargin - titleHeight*3, titleHeight));

        folder.setPreferredSize(new Dimension(titleHeight, titleHeight));
        gear.setPreferredSize(new Dimension(titleHeight, titleHeight));
        trash.setPreferredSize(new Dimension(titleHeight, titleHeight));

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
