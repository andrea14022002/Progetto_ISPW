package com.plantnursery;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;

public class Main  {

    private static final String START_SETTINGS = "src/main/resources/properties/start.properties";
    private static final String LOGGER_SETTINGS = "src/main/resources/properties/logging.properties";
    private static final String VIEW_TYPE = "VIEW_TYPE";
    private static final String DAO_TYPE = "DAO_TYPE";


    public static void main(String[] args) throws IOException {

        InputStream input;

        input = new FileInputStream(LOGGER_SETTINGS);
        LogManager.getLogManager().readConfiguration(input);
        input.close();

        input = new FileInputStream(START_SETTINGS);
        Properties properties = new Properties();
        properties.load(input);
        input.close();

        System.setProperty(VIEW_TYPE, properties.getProperty(VIEW_TYPE));
        System.setProperty(DAO_TYPE, properties.getProperty(DAO_TYPE));

        String mainType = System.getProperty(VIEW_TYPE);
        switch (MainType.valueOf(mainType)) {
            case FX:
                MainFX.run(args);
                break;
            case CLI:
                MainCLI.run();
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }
}