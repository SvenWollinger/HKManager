package io.wollinger.hkmanager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class Save {
    private ArrayList<File> files = new ArrayList<>();
    private boolean valid = false;

    private String name;

    private int masks;
    private int geo;
    private float completionPercentage;
    private int soulVessels;
    private float playTime;
    private int nailUpgrades;
    private int mode; //0 = normal, 1 = steelsoul, 2 = godseeker
    private String version;

    public enum KIND {USER1, USER2, USER3, USER4}

    public Save(String baseFilename) {
        File hkFolder = HKManager.getHollowKnightFolder();
        File[] hkFolderFiles = hkFolder.listFiles();
        for(File cFile : hkFolderFiles) {
            //If file starts with fileX add it to list
            if(cFile.getName().startsWith(baseFilename))
                files.add(cFile);
            //if its the main save file eg fileX.dat load data from it
            if(cFile.getName().equals(baseFilename + ".dat"))
                loadData(cFile);
        }

        File infoFile = new File(hkFolder.getAbsolutePath() + File.separator + baseFilename + ".json");
        if(infoFile.exists())
            loadInfo(infoFile);
    }

    public Save(KIND kind) {
        this(kind.toString().toLowerCase());
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
            masks = playerData.getInt("heartPieces") + 5;
            soulVessels = playerData.getInt("MPReserveMax") / 33;
            mode = 0;
            if(playerData.getInt("permadeathMode") == 1)
                mode = 1;
            if(playerData.getBoolean("newDataGodseekerMask"))
                mode = 2;

            //Loading complete, file is valid!
            valid = true;
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    private void loadInfo(File file) {

    }

    public boolean isValid() {
        return valid;
    }

    public int getMasks() {
        return masks;
    }

    public int getGeo() {
        return geo;
    }

    public float getCompletionPercentage() {
        return completionPercentage;
    }

    public float getPlayTime() {
        return playTime;
    }

    public int getNailUpgrades() {
        return nailUpgrades;
    }

    public int getSoulVessels() {
        return soulVessels;
    }

    public int getMode() {
        return mode;
    }

    public String getVersion() {
        return version;
    }

}
