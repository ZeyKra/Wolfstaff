package fr.zeykra.wolfstaff.enums;

import fr.zeykra.wolfstaff.WolfStaff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;
//Class avec les fonction pour generer les fichiers quand ils sont inexistant
public enum FileEnum {
    CONFIG("config.yml"),
    LANG("lang.yml"),
    SANCTIONGUI("sanctionsgui.yml");

    private final String fileName;
    private final File dataFolder;
    private final WolfStaff instance = WolfStaff.Instance;

    FileEnum(String fileName) {
        this.fileName = fileName;
        this.dataFolder = instance.getDataFolder();
    }

    public void create(Logger logger) {
        if(fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("ResourcePath cannot be found");
        }
        InputStream in = instance.getResource(fileName);
        if(in == null) {
            throw new IllegalArgumentException(fileName + "n'a pas était trouvé dans les sources du .jar");
        }
        if(!dataFolder.exists() && !dataFolder.mkdir()) {
            logger.severe("Le dossier n'a pas pu être crée");
        }
        File outFile = getFile();

        try {
            if(!outFile.exists()) {
                logger.info(fileName + "n'a pas été trouvé, création du fichier en cours");

                OutputStream out = new FileOutputStream(outFile);
                byte[] buff = new byte[1024];
                int n;

                while((n = in.read(buff)) >= 0) {
                    out.write(buff, 0, n);
                }
                out.close();
                in.close();
                if(!outFile.exists()) {
                    logger.severe("Duplication du fichier impossible");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {
        return new File(dataFolder, fileName);
    }

}
