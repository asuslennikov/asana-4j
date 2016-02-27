package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.utils.PropertiesStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class FileBasedStore implements PropertiesStore {
    private Properties store = new Properties();
    private final String propsFileName;

    public FileBasedStore() {
        this("asana.properties");
    }

    public FileBasedStore(String propertiesFileName) {
        this.propsFileName = propertiesFileName;
        File file = new File(this.propsFileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            store.load(new FileInputStream(file));
        } catch (IOException ioEx) {
            throw new RuntimeException("Unable to instantiate FileBasedStore. " + ioEx.getLocalizedMessage());
        }
    }

    @Override
    public String getString(String key) {
        return store.getProperty(key);
    }

    @Override
    public void setString(String key, String value) {
        putWithNullCheck(key, value);
    }

    private void putWithNullCheck(String key, Object value) {
        if (key != null && value != null) {
            store.put(key, value);
        } else if (key != null) {
            store.remove(key);
        }
        try {
            store.store(new FileOutputStream(new File(this.propsFileName)), null);
        } catch (IOException ioEx) {
            Logger.getLogger(this.getClass().getName()).severe("Unable to store preferences, reason = " + ioEx.getLocalizedMessage());
        }
    }
}
