package main.webserver;
import main.fileSystem.FileSystemFile;
import main.webserver.helper.RequestInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by aaron on 5/24/2018.
 */
public class ConnectionHandler implements Runnable {
    static final Logger LOGGER = LogManager.getLogger(ConnectionHandler.class.getName());
    private final Socket socket;
    private String rootDir;
    private ApiHandler apiHandler;

    public ConnectionHandler(Socket local_socket, String rootDirectory,ApiHandler apiHandler){
        this.socket = local_socket;
        this.rootDir = rootDirectory;
        this.apiHandler = apiHandler;
    }
    public ConnectionHandler(Socket local_socket) {
        this(local_socket, "Static", new ApiHandler());
    }

    public void run() {
        RequestInfo info = new RequestInfo();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();

            String line;
            while( (line = in.readLine()) != null){
                if(line.isEmpty()){
                    break;
                }else if(line.contains(":")) {
                    info.addItem(line);
                }else if(line.contains("GET")) {
                    info.addGet(line);
                }
            }

            byte[] returnType = new byte[0];

            String type = "text/html";
            boolean notFound = false;
            try {
                if(info.getRequestURL().contains("api")){
                    returnType = apiHandler.getByteResponse(info.getRequestURL()) ;
                    type = "application/json";
                } else {
                    returnType = FileSystemFile.getFileSystemFile(this.rootDir,info.getRequestURL());
                }
                socket.getRemoteSocketAddress();
                LOGGER.info(String.format("RequestURL=%s, remoteSocket=%s",info.getRequestURL(), this.socket.getRemoteSocketAddress().toString()));

            } catch (Exception e) {
                notFound = true;
                LOGGER.error(String.format("RequestURL=%s, remoteSocket=%s",info.getRequestURL(), this.socket.getRemoteSocketAddress().toString()));
                LOGGER.error(String.format("error=\"%s\"",e.toString()),e);
            }

            if(info.getRequestURL().contains(".png")){
                type =  "image/png";
            } else if (info.getRequestURL().contains(".css")){
                type =  "text/css";
            }

            String httpResponse = "";
            byte[] bytes = null;
            if (notFound) {
                httpResponse = "HTTP/1.1 404 Not Found ; Content-Type: text/html \r\n\r\n";
                bytes = httpResponse.getBytes("UTF-8");
                out.write(bytes);
                out.write("NOT FOUND".getBytes());
            } else {
                httpResponse = "HTTP/1.1 200 charset=UTF-8 OK; Content-Type: " + type +"; \r\n\r\n";
                bytes = httpResponse.getBytes("UTF-8");
                out.write(bytes);
                out.write(returnType);
            }

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
