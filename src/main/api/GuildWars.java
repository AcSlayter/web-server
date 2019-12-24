package main.api;

import main.webserver.ApiHandler;
import main.webserver.exception.ApiNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class GuildWars extends ApiHandler {
    public byte[] getByteResponse(String requestURL) throws ApiNotFoundException, Exception {
        System.out.println(requestURL);
        HashMap<String, String> arguments = getArguments(requestURL);
        String[] urlArray = getURLBreak(requestURL);

        if(urlArray[1].equals("gw2")){
            switch (urlArray[3]){
                case "accountCharacters" :
                    return makeInternalApiCall("accountCharacters", arguments.get("account"));
                case "core" :
                    return makeInternalApiCall("core", arguments.get("account"),arguments.get("name"));
                default:
                    return "{}".getBytes();
            }
        } else {
            return "{}".getBytes();
        }
    }

    private byte[] makeInternalApiCall(String call, String... args) {
        if(args[0] == null){
            return null;
        }
        String urlString = null;
        switch(call){
            case "accountCharacters":
                urlString = String.format("http://localhost:2845/guildwars2/character/all?accountName=%s",args[0]);
                break;
            case "core":
                urlString = String.format("http://localhost:2845/guildwars2/character/core?accountName=%s&name=%s",args[0],args[1]);
                break;
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            StringBuilder stringBuilder = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                stringBuilder.append(output);
            }

            conn.disconnect();
            return stringBuilder.toString().getBytes();
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    private  String[]  getURLBreak(String url){
        String urlWithoutParamaters = url.split("\\?")[0];
        return urlWithoutParamaters.split("/");
    }
    private HashMap<String, String> getArguments(String url){
        HashMap<String , String > argHash = new HashMap<>();
        String urlArgs = url.split("\\?")[1];

        String[] argList = urlArgs.split("\\&");

        for(String arg : argList){
            argHash.put(arg.split("=")[0],arg.split("=")[1]);
        }


        return  argHash;
    }
}
