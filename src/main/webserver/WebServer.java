package main.webserver;

import main.api.GuildWars;
import main.fileSystem.config.Config;
import main.webserver.helper.TerminalInput;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by aaron on 4/28/2018.
 */
public class WebServer implements Runnable {
    private int port;
    private String rootDir ;
    ApiHandler apiHandler;

    public WebServer(){
        this(null,null);
    }

    public WebServer(String port, String rootDirectory){
        Properties properties = Config.getProperties();
        if(port == null){
            this.port = Integer.parseInt((String)properties.get(Config.MAIN_PORT));
        }else {
            this.port = Integer.parseInt(port);
        }
        if(rootDirectory == null){
            this.rootDir = (String)properties.get(Config.SITE_ROOT);
        }else {
            this.rootDir = rootDirectory;
        }

        this.apiHandler = new GuildWars();
    }

    public void run() {
        Socket local_socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            TerminalInput terminalInput = new TerminalInput();
            new Thread(terminalInput,"console ready").start();

            while( terminalInput.isAlive() ) {
                try {
                    local_socket =  serverSocket.accept();
                    new Thread(new ConnectionHandler(local_socket, this.rootDir, this.apiHandler), "client").start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}