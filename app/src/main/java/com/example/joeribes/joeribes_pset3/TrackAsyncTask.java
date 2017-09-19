package com.example.joeribes.joeribes_pset3;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Joeri Bes on 19-9-2017.
 */

public class TrackAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    MainActivity mainAct;

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
            ArrayList<String> trackData = new ArrayList<>();

            try {
                JSONObject trackStreamObj = new JSONObject(result);
                JSONObject resultObj = trackStreamObj.getJSONObject("results");
                JSONObject trackMatches = resultObj.getJSONObject("trackmatches");
                JSONArray tracksObj = trackMatches.getJSONArray("track");

                // get the track, artist and image url from all the search results
                for(int i = 0; i < tracksObj.length(); i++) {
                    JSONObject track = tracksObj.getJSONObject(i);
                    String trackName = track.getString("name");
                    String artistName = track.getString("artist");
                    //JSONObject imageURL = tracksObj.getJSONObject(1);
                    //String image = imageURL.getString("#text");

                    // add the data from search results to the TrackData Arraylist
                    trackData.add(trackName + " - " + artistName);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mainAct.trackStartIntent(trackData);
        }



    }




}

