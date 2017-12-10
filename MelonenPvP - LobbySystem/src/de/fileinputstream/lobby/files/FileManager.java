package de.fileinputstream.lobby.files;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class FileManager {



    public FileManager() {

    }

    public void createConfig() {

    }

    public void createDirecories() {
        File dir = new File("plugins//MelonLobbySystem//Warps//");
        if(!dir.exists()) {
            dir.mkdirs();
        }
    }
}
