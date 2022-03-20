package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Instant;
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

    private ArrayList<SavePanel> loadedPanels = new ArrayList<>();

    public HKManager() {
        ensureFolders();

        setSize(1024, 512);
        setTitle("HKManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));

        panelSavesSP.getVerticalScrollBar().setUnitIncrement(20);
        panelLoadedSavesSP.getVerticalScrollBar().setUnitIncrement(20);

        loadSaves();

        add(panelSavesSP);
        add(panelLoadedSavesSP);
        setVisible(true);
        resizePanels();
    }

    public void resizePanels() {
        revalidate();
        for(SavePanel panel : loadedPanels) {
            panel.triggerResize();
        }
        revalidate();
        repaint();
    }

    public void loadSaves() {
        loadedSaves[0] = new Save(Save.KIND.USER1);
        loadedSaves[1] = new Save(Save.KIND.USER2);
        loadedSaves[2] = new Save(Save.KIND.USER3);
        loadedSaves[3] = new Save(Save.KIND.USER4);

        saves.clear();
        for(File folder : savesFolder.listFiles()) {
            saves.add(new Save(folder, Save.KIND.USER0));
        }

        panelSaves.removeAll();
        panelLoadedSaves.removeAll();
        loadedPanels.clear();

        for(Save save : saves) {
            if (save.isValid()) {
                SavePanel panel = new SavePanel(this, save, panelSavesSP);
                panelSaves.add(panel);
                loadedPanels.add(panel);
            }
        }

        for(Save save : loadedSaves) {
            if(save.isValid()) {
                SavePanel panel = new SavePanel(this, save, panelLoadedSavesSP);
                panelLoadedSaves.add(panel);
                loadedPanels.add(panel);
            }
        }
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

    public void moveToStorage(Save save) {
        moveSaveFile(save, backupsFolder, "user0", true);
        moveSaveFile(save, savesFolder, "user0", true);
        deleteSaveFiles(save);
        loadSaves();
        resizePanels();
    }

    public void moveToSlot(Save save, int slot) {
        moveSaveFile(save, backupsFolder, "user0", true);
        moveSaveFile(save, getHollowKnightFolder(), "user" + slot, false);
        deleteSaveFiles(save);
        loadSaves();
        resizePanels();
    }

    private void deleteSaveFiles(Save save) {
        for(File f : save.getFiles()) {
            f.delete();
        }
    }

    private void moveSaveFile(Save save, File location, String userID, boolean randomFolder) {
        String addition = "";
        if(randomFolder) {
            addition += File.separator + getUnixtime() + (Math.random() * 1000);
        }
        File nFolder = new File(location.getAbsolutePath() + addition);
        nFolder.mkdir();
        for(File f : save.getFiles()) {
            try {
                String newName = f.getName();
                if(userID.endsWith("0")) {
                    newName = newName.replace("user1", userID);
                    newName = newName.replace("user2", userID);
                    newName = newName.replace("user3", userID);
                    newName = newName.replace("user4", userID);
                } else {
                    newName = newName.replace("user0", userID);
                }

                File newFile = new File(nFolder.getAbsolutePath() + File.separator + newName);
                Files.copy(f.toPath(), newFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static long getUnixtime() {
        return Instant.now().getEpochSecond();
    }
}
