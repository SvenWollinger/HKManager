package io.wollinger.hkmanager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class Save {
    private ArrayList<File> files = new ArrayList<>();
    private boolean valid = false;

    private String name;

    private int geo;
    private float completionPercentage;
    private float playTime;
    private int nailUpgrades;
    private String version;
    private int masks;
    private boolean steelsoul;

    public enum KIND {USER1, USER2, USER3, USER4}

    public Save(KIND kind) {
        File hkFolder = HKManager.getHollowKnightFolder();
        File[] hkFolderFiles = hkFolder.listFiles();
        String lookingFor = kind.toString().toLowerCase();
        for(File cFile : hkFolderFiles) {
            //If file starts with fileX add it to list
            if(cFile.getName().startsWith(lookingFor))
                files.add(cFile);
            //if its the main save file eg fileX.dat load data from it
            if(cFile.getName().equals(lookingFor + ".dat"))
                loadData(cFile);
        }

        File infoFile = new File(hkFolder.getAbsolutePath() + File.separator + lookingFor + ".json");
        if(infoFile.exists())
            loadInfo(infoFile);
    }

    private void loadData(File file) {
        try {
            JSONObject saveData = SaveLoader.loadSave(file);
            JSONObject playerData = saveData.getJSONObject("playerData");

            geo = playerData.getInt("geo");
            completionPercentage = playerData.getFloat("completionPercentage");
            playTime = playerData.getFloat("playTime");
            nailUpgrades = playerData.getInt("nailSmithUpgrades");
            version = playerData.getString("version");
            masks = playerData.getInt("maxHealth");
            steelsoul = playerData.getBoolean("permadeathMode");
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    private void loadInfo(File file) {

    }

    public boolean isValid() {
        return valid;
    }
}
