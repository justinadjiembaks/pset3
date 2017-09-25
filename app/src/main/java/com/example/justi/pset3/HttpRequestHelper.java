package com.example.justi.pset3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by justi on 20-9-2017.
 */

public class HttpRequestHelper {
    protected static synchronized String downloadFromServer(String... params) {
        String result = "";
        String chosenTag = params[0];

        String urllastfm = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="
                + chosenTag + "&api_key=862cbe4b25a7e8b3ac882ed37e7f8720&format=json";

        URL url = null;

        try {
            url = new URL(urllastfm);
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
