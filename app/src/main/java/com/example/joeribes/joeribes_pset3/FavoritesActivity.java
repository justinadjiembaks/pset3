package com.example.joeribes.joeribes_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    TextView favoriteView;
    ListView lvItems;
    Songs [] songArray;
    SharedPreferences shared;
    ArrayList<String> trackArray;
    ArrayList<String> imageURL;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize views
        //favoriteView = (TextView) findViewById(R.id.favoriteView);
        lvItems = (ListView) findViewById(R.id.listViewID);

        loadFromSharedPrefs();
        makeTrackAdapter();
    }




    public void loadFromSharedPrefs(){
        shared = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        Gson gson = new Gson();

        String jsonText = shared.getString("name", "");
        String jsonText2 = shared.getString("imgURL", "");

        if(jsonText.equals("")) {
            trackArray = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            trackArray = gson.fromJson(jsonText, type);
        }

        if(jsonText2.equals("")) {
            imageURL = new ArrayList<>();
        } else {
            Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
            imageURL = gson.fromJson(jsonText2, type2);
        }
    }


    public void makeTrackAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, trackArray);

        lvItems = (ListView) findViewById(R.id.listViewID);
        assert lvItems != null;
        lvItems.setAdapter(arrayAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Receive the strings at the clicked position
                String lv = lvItems.getItemAtPosition(position).toString();
                String songURL = imageURL.get(position);

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), InformationActivity.class);

                // Sending data to new activity
                i.putExtra("listview", lv);
                i.putExtra("songURL", songURL);
                startActivity(i);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String track = lvItems.getItemAtPosition(position).toString();

                removeTrack(track);
                return true;
            }
        });


    }

    private void removeTrack(String track) {
        shared = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = shared.edit();

        Gson gson = new Gson();

        String jsonText = shared.getString("name", "");
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        trackArray = gson.fromJson(jsonText, type);

        String jsonText2 = shared.getString("imgURL", "");
        Type type2 = new TypeToken<ArrayList<String>>(){}.getType();
        imageURL = gson.fromJson(jsonText2, type2);


        for (int i=0;i<trackArray.size();i++) {
            if(track.equals(trackArray.get(i))) {
                trackArray.remove(i);
                imageURL.remove(i);
            }
        }

        // Convert to String
        String jsonText1 = gson.toJson(trackArray);
        String jsonText3 = gson.toJson(imageURL);

        // Load the Strings in the editor
        editor.putString("name", jsonText1);
        editor.putString("imgURL", jsonText3);
        editor.apply();

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), FavoritesActivity.class);

        // Sending data to new activity
        i.putExtra("listview", track);
        i.putExtra("songURL", imageURL);
        startActivity(i);



    }



}
