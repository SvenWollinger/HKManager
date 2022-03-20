package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HKManager extends JFrame {
    private final Save[] loadedSaves = new Save[4];
    private final ArrayList<Save> saves = new ArrayList<>();
    private static Font font;

    private JPanel panelSaves = new JPanel(new GridLayout(0, 1));
    private JPanel panelLoadedSaves = new JPanel(new GridLayout(0,1));
    private JScrollPane panelSavesSP = new JScrollPane (panelSaves, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private JScrollPane panelLoadedSavesSP = new JScrollPane (panelLoadedSaves, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    private File savesFolder;
    private File backupsFolder;

    public HKManager() {
        ensureFolders();

        setSize(1024, 512);
        setTitle("HKManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));

        panelSavesSP.getVerticalScrollBar().setUnitIncrement(20);
        panelLoadedSavesSP.getVerticalScrollBar().setUnitIncrement(20);

        ArrayList<SavePanel> panels = loadSaves();

        add(panelSavesSP);
        add(panelLoadedSavesSP);
        setVisible(true);
        for(SavePanel panel : panels)
            panel.triggerResize();
    }

    public ArrayList<SavePanel> loadSaves() {
        loadedSaves[0] = new Save(Save.KIND.USER1);
        loadedSaves[1] = new Save(Save.KIND.USER2);
        loadedSaves[2] = new Save(Save.KIND.USER3);
        loadedSaves[3] = new Save(Save.KIND.USER4);

        for(File folder : savesFolder.listFiles()) {
            saves.add(new Save(folder, Save.KIND.USER0));
        }

        ArrayList<SavePanel> panels = new ArrayList<>();

        for(Save save : saves) {
            if (save.isValid()) {
                SavePanel panel = new SavePanel(this, save, panelSavesSP);
                panelSaves.add(panel);
                panels.add(panel);
            }
        }

        for(Save save : loadedSaves) {
            if(save.isValid()) {
                SavePanel panel = new SavePanel(this, save, panelLoadedSavesSP);
                panelLoadedSaves.add(panel);
                panels.add(panel);
            }
        }
        return panels;
    }

    public void ensureFolders() {
        String userHome = System.getProperty("user.home");
        String mainFolderPath = userHome + File.separator + ".hkmanager";

        File mainFolder = new File(mainFolderPath);
        savesFolder = new File(mainFolderPath + File.separator + "saves");
        backupsFolder = new File(mainFolder + File.separator + "backups");
        File logFolder = new File(mainFolder + File.separator + "logs");

        //TODO: When add logger, add checks
        if(!mainFolder.exists())
            mainFolder.mkdir();

        if(!savesFolder.exists())
            savesFolder.mkdir();

        if(!backupsFolder.exists())
            backupsFolder.mkdir();

        if(!logFolder.exists())
            logFolder.mkdir();
    }

    public static File getHollowKnightFolder() {
        File appdata = new File(System.getenv("APPDATA")).getParentFile();
        return new File(appdata.getAbsolutePath() + File.separator + "LocalLow" + File.separator + "Team Cherry" + File.separator + "Hollow Knight");
    }

    public Save getLoadedSave(int index) {
        return loadedSaves[index];
    }

    public static Font getHKFont() {
        if(font == null) {
            try {
                InputStream is = HKManager.class.getResourceAsStream("/io/wollinger/hkmanager/fonts/trajanprobold.ttf");
                font = Font.createFont(Font.TRUETYPE_FONT,is);
                font = font.deriveFont(24f);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        return font;
    }
}
