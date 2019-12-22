package main.webserver.helper;

import main.fileSystem.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Created by aaron on 4/10/2017.
 */
public class RequestInfo {
    static final Logger LOGGER = LogManager.getLogger(Config.class.getName());

    static String HOST = "Host";
    static String CONNECTION = "Connection";
    static String CACHE_CONTROL = "Cache-Control";
    static String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    static String USER_AGENT = "User-Agent";
    static String ACCEPT = "Accept";
    static String ACCEPT_ENCODING = "Accept-Encoding";
    static String ACCEPT_LANGUAGE = "Accept-Language";
    static String GET = "GET";


    private HashMap<String, String> info;

    public RequestInfo() {
        info = new HashMap<String,String>();
    }
    public void addItem(String input){
        String[] split = input.split(":");
        info.put(split[0],split[1]);
    }

    public String get(String key) {
        return info.get(key);
    }

    public void addGet(String line) {
        String[] split = line.split(" ");
        info.put(GET, split[1]);
        LOGGER.info(String.format("reqestEndpoint=%s", split[1]));
    }

    public String getRequestURL() {
        String getURL = info.get(GET);
        if(getURL == null){
            return "";
        }
        return getURL;
    }
//    GET / HTTP/1.1
//    Host: localhost:8080
//    Connection: keep-alive
//    Cache-Control: max-age=0
//    Upgrade-Insecure-Requests: 1
//    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//Accept-Encoding: gzip, deflate, sdch, br
//Accept-Language: en-US,en;q=0.8

}
