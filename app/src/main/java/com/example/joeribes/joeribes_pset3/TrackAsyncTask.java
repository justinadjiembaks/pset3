package com.example.joeribes.joeribes_pset3;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joeri Bes on 19-9-2017.
 */

public class TrackAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    MainActivity mainAct;
    Songs[] trackData;

    public TrackAsyncTask(MainActivity main){
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "searching for tracks...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result.length() == 0) {
            Toast.makeText(context, "Something went wrong getting the data from server", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject trackStreamObj = new JSONObject(result);
                JSONObject resultObj = trackStreamObj.getJSONObject("results");
                JSONObject trackMatches = resultObj.getJSONObject("trackmatches");
                JSONArray tracksObj = trackMatches.getJSONArray("track");

                trackData = new Songs[tracksObj.length()];

                // get the track, artist and image url from all the search results
                for(int i = 0; i < tracksObj.length(); i++) {
                    JSONObject track = tracksObj.getJSONObject(i);
                    String name = track.getString("name");
                    String artist = track.getString("artist");
                    JSONArray imageArray = track.getJSONArray("image");
                    JSONObject imageObj = imageArray.getJSONObject(3);
                    String imageURL = imageObj.getString("#text");
                    trackData[i] = new Songs(name, artist, imageURL);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mainAct.trackStartIntent(trackData);
        }



    }




}

