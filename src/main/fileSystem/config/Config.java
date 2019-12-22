package main.fileSystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    static final Logger LOGGER = LogManager.getLogger(Config.class.getName());

    public static final String MAIN_PORT = "MPORT";
    public static final String ERROR_PORT = "EPORT";
    public static final String IS_DEBUG = "DEBUG";
    public static final String ETFAPIHOST = "ETFAPIHOST";
    public static final String ETFAPIPORT = "ETFAPIPORT";
    public static final String SITE_ROOT = "SITE_ROOT";

    public static void main(String[] args) {
        try {
            Properties test = getProperties();
            LOGGER.info(String.format("Default_PORT=%s",test.getProperty("Default-Port")));
        } catch (Exception e){

        }
    }

    public static void SaveData(Properties applicationProps) {
        String name = "app.Properties.data";
        try {
            LOGGER.info(String.format("Saving=%s ", name ));
            FileOutputStream out = new FileOutputStream(name);
            applicationProps.store(out, "---No Comment---");
            out.close();
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
    }

    public static Properties getProperties()   {
        Properties defaultProps;
        try {
            defaultProps = new Properties();
            FileInputStream in = new FileInputStream("app.Properties.data");
            defaultProps.load(in);
            in.close();
            return defaultProps;
        } catch(IOException e){
            LOGGER.error(e.toString());
        }
        LOGGER.info("message=\"LOADING System Defaults\"");
        defaultProps = getDefaults();
        SaveData(defaultProps);
        return defaultProps;
    }

    public static Properties getDefaults() {
        Properties config = new Properties();
        config.setProperty(Config.MAIN_PORT, "8080");
        config.setProperty(Config.ERROR_PORT, "2020");
        config.setProperty(Config.IS_DEBUG, "true");
        config.setProperty(Config.IS_DEBUG, "true");
        config.setProperty(Config.ETFAPIHOST, "http://etfapi.ac-local.com");
        config.setProperty(Config.ETFAPIPORT,"2846");
        config.setProperty(Config.SITE_ROOT,"C:\\Users\\aaron\\Documents\\GitHub\\web-server\\WEBROOT");
        return config;

    }
}
