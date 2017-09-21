package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
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
        shared = this.getSharedPreferences("App_settings", this.MODE_PRIVATE);
        trackArray = new ArrayList<>();
        imageURL = new ArrayList<>();

        // Load the sets
        Set<String> set = shared.getStringSet("name", null);
        Set<String> set2 = shared.getStringSet("imageURL", null);
        trackArray.addAll(set);
        imageURL.addAll(set2);
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


    }



}
