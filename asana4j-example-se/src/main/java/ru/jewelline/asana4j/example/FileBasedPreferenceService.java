package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.utils.PreferencesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class FileBasedPreferenceService implements PreferencesService {
    private Properties store = new Properties();
    private final String propsFileName;

    public FileBasedPreferenceService(){
        this("asana.properties");
    }

    public FileBasedPreferenceService(String propertiesFileName) {
        this.propsFileName = propertiesFileName;
        File file = new File(this.propsFileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            store.load(new FileInputStream(file));
        } catch (IOException ioEx){
            throw new RuntimeException("Unable to instantiate FileBasedPreferenceService. " + ioEx.getLocalizedMessage());
        }
    }

    @Override
    public Integer getInteger(String key) {
        return Integer.parseInt(store.getProperty(key));
    }

    @Override
    public Long getLong(String key) {
        return Long.parseLong(store.getProperty(key));
    }

    @Override
    public String getString(String key) {
        return store.getProperty(key);
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        Integer value = getInteger(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        Long value = getLong(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = getString(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public void setInteger(String key, Integer value) {
        putWithNullCheck(key, value);
    }

    @Override
    public void setLong(String key, Long value) {
        putWithNullCheck(key, value);
    }

    @Override
    public void setString(String key, String value) {
        putWithNullCheck(key, value);
    }

    private void putWithNullCheck(String key, Object value){
        if (key != null && value != null) {
            store.put(key, value);
        } else if(key != null){
            store.remove(key);
        }
        try {
            store.store(new FileOutputStream(new File(this.propsFileName)), null);
        } catch (IOException ioEx) {
            Logger.getLogger(this.getClass().getName()).severe("Unable to store preferences, reason = " + ioEx.getLocalizedMessage());
        }
    }
}
