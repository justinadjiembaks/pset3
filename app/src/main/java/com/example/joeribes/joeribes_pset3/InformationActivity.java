package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InformationActivity extends AppCompatActivity {
    TextView txtProduct;
    String product;
    String songURL;

    SharedPreferences shared;
    ArrayList<String> trackArray = new ArrayList<>();
    ArrayList<String> imageURL = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Initialize views
        TextView txtProduct = (TextView) findViewById(R.id.trackName);

        Intent i = getIntent();
        // Getting attached intent data
        product = i.getStringExtra("listview");
        songURL = i.getStringExtra("songURL");
        txtProduct.setText(product);





        // Display the image of the Track
        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(songURL);
    }

    public void saveToSharedPrefs(View view) {
        shared = this.getSharedPreferences("App_settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        trackArray = new ArrayList<>();
        imageURL = new ArrayList<>();

        // Add values to the trackArray
        trackArray.add(product);
        imageURL.add(songURL);

        // Retrieve string from shared
        Set<String> set = shared.getStringSet("name", null);
        Set<String> set2 = shared.getStringSet("imageURL", null);

        // Add values to the set
        set.addAll(trackArray);
        set2.addAll(imageURL);

        // Overwrite the old set
        editor.putStringSet("name", set);
        editor.putStringSet("imageURL", set2);
        editor.apply();
    }

    public void goToFavorites(View view) {
        Intent i = new Intent(getApplicationContext(), FavoritesActivity.class);
        // Sending data to new activity
        i.putExtra("listview", product);
        i.putExtra("songURL", songURL);
        startActivity(i);
    }

}
