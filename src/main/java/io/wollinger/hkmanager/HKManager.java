package io.wollinger.hkmanager;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class HKManager extends JFrame {
    private ArrayList<Save> saves = new ArrayList<>();

    public HKManager() {
        ensureFolders();

        setSize(512, 512);
        setTitle("HKManager");
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
}
