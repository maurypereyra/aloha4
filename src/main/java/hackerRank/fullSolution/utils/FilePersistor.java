package hackerRank.fullSolution.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

import static java.io.File.separator;

public class FilePersistor {
    private static FilePersistor instance;
    private static final String homeUserDir = System.getProperty("user.home");

    private FilePersistor() {
    }

    public synchronized static FilePersistor getInstance() {
        if (instance == null) {
            instance = new FilePersistor();
        }
        return instance;
    }

    public boolean saveObjectToDisk(String fileName, Serializable object) {
        try (FileOutputStream fileOut = new FileOutputStream(getFilePath(fileName));
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Optional<Object> readObjectFromDisk(String fileName) {
        Optional<Object> maybeFile = Optional.empty();
        File f = new File(getFilePath(fileName));

        if (f.isFile() && f.canRead()) {
            try (FileInputStream fileIn = new FileInputStream(f);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                maybeFile = Optional.of(in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        return maybeFile;
    }

    private String getFilePath(String fileName) {
        return homeUserDir + separator + fileName;
    }

}
