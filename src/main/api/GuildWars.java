package main.api;

import main.webserver.ApiHandler;
import main.webserver.exception.ApiNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GuildWars extends ApiHandler {
    public byte[] getByteResponse(String requestURL) throws ApiNotFoundException, Exception {
        System.out.println(requestURL);

//        if(requestURL.contains("getAllCharacters")){
            try {

                URL url = new URL("http://localhost:2845/guildwars2/character/all?accountName=default");
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
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                    stringBuilder.append(output);
                }

                conn.disconnect();
                return stringBuilder.toString().getBytes();
            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

//        }

        return "{}".getBytes();
    }
}
