package io.wollinger.hkmanager;

import java.io.File;
import java.util.ArrayList;

public class Save {
    private ArrayList<File> files = new ArrayList<>();
    private boolean valid = false;

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
    }

    private void loadData(File file) {

    }

    public boolean isValid() {
        return valid;
    }
}
