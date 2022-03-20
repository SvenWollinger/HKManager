package io.wollinger.hkmanager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class Save {
    private final ArrayList<File> files = new ArrayList<>();
    private boolean valid = false;

    private String name;

    private final KIND kind;

    private int masks;
    private int geo;
    private float completionPercentage;
    private int soulVessels;
    private float playTime;
    private int nailUpgrades;
    private int mode; //0 = normal, 1 = steelsoul, 2 = godseeker
    private String version;

    public enum KIND {USER1, USER2, USER3, USER4, USER0}

    public Save(File baseFolder, KIND kind) {
        this.kind = kind;
        String baseFilename = kind.toString().toLowerCase();
        File[] folderFiles = baseFolder.listFiles();
        for(File cFile : folderFiles) {
            //If file starts with fileX add it to list
            if(cFile.getName().startsWith(baseFilename))
                files.add(cFile);
            //if its the main save file eg fileX.dat load data from it
            if(cFile.getName().equals(baseFilename + ".dat"))
                loadData(cFile);
        }

        File configFile = getConfigFile();
        if(configFile != null && configFile.exists()) {
            try {
                JSONObject config = new JSONObject(new String(Files.readAllBytes(configFile.toPath())));
                name = config.getString("name");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Save(KIND kind) {
        this(HKManager.getHollowKnightFolder(), kind);
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

    public boolean isValid() {
        return valid;
    }

    public String getName() {
        return name;
    }

    private File getConfigFile() {
        if(files.size() == 0) return null;
        File parentFolder = files.get(0).getParentFile();
        return new File(parentFolder.getAbsolutePath() + File.separator + kind.toString().toLowerCase() + ".cfg");
    }

    public void setName(String name) {
        this.name = name;
        JSONObject config = new JSONObject();
        config.put("name", name);
        try {
            PrintWriter out = new PrintWriter(getConfigFile());
            out.write(config.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public KIND getKind() {
        return kind;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Save{" +
                "files=" + files +
                ", valid=" + valid +
                ", name='" + name + '\'' +
                ", kind=" + kind +
                ", masks=" + masks +
                ", geo=" + geo +
                ", completionPercentage=" + completionPercentage +
                ", soulVessels=" + soulVessels +
                ", playTime=" + playTime +
                ", nailUpgrades=" + nailUpgrades +
                ", mode=" + mode +
                ", version='" + version + '\'' +
                '}';
    }
}
