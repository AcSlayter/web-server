package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Live {
    static final Logger LOGGER = LogManager.getLogger(Live.class.getName());

    public static void main(String[] args) {
        LOGGER.info("test2");
        LOGGER.debug("debug");
        LOGGER.error("error");


    }
}
