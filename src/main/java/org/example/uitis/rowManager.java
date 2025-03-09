package org.example.uitis;

import java.io.*;
import java.util.Properties;

public class rowManager {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            // File doesn't exist yet, create with default value
            properties.setProperty("recordsPerPage", "2");
            saveConfig();
        }
    }

    private static void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Product Management Configuration");
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    public static int getRecordsPerPage() {
        return Integer.parseInt(properties.getProperty("recordsPerPage", "2"));
    }

    public static void setRecordsPerPage(int records) {
        properties.setProperty("recordsPerPage", String.valueOf(records));
        saveConfig();
    }
}