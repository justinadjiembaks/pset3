package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {
    TextView tvResult;
    ListView lvItems;
    Songs [] songArray;
    ArrayList<String> trackArray = new ArrayList<String>();
    ArrayList<String> imgURL = new ArrayList<String>();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        // Initialize views
        lvItems = (ListView) findViewById(R.id.listViewID);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        songArray = (Songs[]) bundle.getSerializable("data");

        for(Songs song : songArray) {
            trackArray.add(song.getName() + " - " + song.getArtist());
            imgURL.add(song.getImageURL());
        }

        makeTrackAdapter();

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
                String songURL = imgURL.get(position);

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
