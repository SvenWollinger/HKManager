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

    public HKManager() {
        ensureFolders();
        loadLoadedSaves();

        setSize(512, 512);
        setTitle("HKManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        JPanel panelSaves = new JPanel();
        JPanel panelLoadedSaves = new JPanel();

        for(Save save : loadedSaves)
            if(save.isValid())
                panelLoadedSaves.add(new SavePanel(save, panelLoadedSaves));

        for(Save save : saves)
            if(save.isValid())
                panelSaves.add(new SavePanel(save, panelSaves));

        add(panelSaves);
        add(panelLoadedSaves);
        setVisible(true);
    }

    public void loadLoadedSaves() {
        loadedSaves[0] = new Save(Save.KIND.USER1);
        loadedSaves[1] = new Save(Save.KIND.USER2);
        loadedSaves[2] = new Save(Save.KIND.USER3);
        loadedSaves[3] = new Save(Save.KIND.USER4);
    }

    public void ensureFolders() {
        String userHome = System.getProperty("user.home");
        String mainFolderPath = userHome + File.separator + ".hkmanager";

        File mainFolder = new File(mainFolderPath);
        File savesFolder = new File(mainFolderPath + File.separator + "saves");
        File backupFolder = new File(mainFolder + File.separator + "backups");
        File logFolder = new File(mainFolder + File.separator + "logs");

        //TODO: When add logger, add checks
        if(!mainFolder.exists())
            mainFolder.mkdir();

        if(!savesFolder.exists())
            savesFolder.mkdir();

        if(!backupFolder.exists())
            backupFolder.mkdir();

        if(!logFolder.exists())
            logFolder.mkdir();
    }

    public static File getHollowKnightFolder() {
        File appdata = new File(System.getenv("APPDATA")).getParentFile();
        return new File(appdata.getAbsolutePath() + File.separator + "LocalLow" + File.separator + "Team Cherry" + File.separator + "Hollow Knight");
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
