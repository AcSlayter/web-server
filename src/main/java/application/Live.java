package application;

import main.webserver.WebServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Live {
    static final Logger LOGGER = LogManager.getLogger(Live.class.getName());

    public static void main(String[] args) {
        LOGGER.info("initialising webServer webServer");
        WebServer webServer = new WebServer();

        Thread webServerTHREAD = new Thread(webServer, "Thread 1");

        webServerTHREAD.start();

        while(webServerTHREAD.isAlive() || webServerTHREAD.isAlive()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
