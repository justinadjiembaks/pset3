package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
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
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


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
        shared = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = shared.edit();

        Gson gson = new Gson();

        String test = shared.getString("name", "");
        String test2 = shared.getString("imgURL", "");

        if(test.equals("")) {
            trackArray = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            trackArray = gson.fromJson(test, type);
            trackArray.add(test);
        }

        if(test2.equals("")) {
            imageURL = new ArrayList<>();
        } else {
            Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
            imageURL = gson.fromJson(test2, type2);
            imageURL.add(test2);

        }

        trackArray.add(product);
        imageURL.add(songURL);

        String jsonText = gson.toJson(trackArray);
        String jsonText2 = gson.toJson(imageURL);

        editor.putString("name", jsonText);
        editor.putString("imgURL", jsonText2);
        editor.apply();

        Toast.makeText(this.getApplicationContext(), "Added to your favorite list", Toast.LENGTH_SHORT).show();
    }

    public void goToFavorites(View view) {
        Intent i = new Intent(getApplicationContext(), FavoritesActivity.class);
        // Sending data to new activity
        i.putExtra("listview", product);
        i.putExtra("songURL", songURL);
        startActivity(i);
    }

}
