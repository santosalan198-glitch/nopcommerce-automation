package com.nopcommerce.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key no encontrada en config: " + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        if (key.equals("explicit.wait")) {
            String envWait = System.getenv("EXPLICIT_WAIT");
            if (envWait != null) return Integer.parseInt(envWait);
        }
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
