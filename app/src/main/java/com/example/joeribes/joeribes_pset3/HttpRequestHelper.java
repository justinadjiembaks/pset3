package com.example.joeribes.joeribes_pset3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Joeri Bes on 19-9-2017.
 */

public class HttpRequestHelper {
    protected static synchronized String downloadFromServer(String... params) {
        String result = "";
        String chosenTag = params[0];

        // Create the url link in order to search for a song track
        String API_KEY = "04703f87d9812a511d527c3a322e69c5";
        String trackURL = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="
                + chosenTag + "&api_key=" + API_KEY + "&format=json";

        URL url = null;

        try {
            url = new URL(trackURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connect;

        if(url != null) {
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Integer responseCode = connect.getResponseCode();

                if (responseCode >= 200 && responseCode < 300) {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;
                    while ((line = bReader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
