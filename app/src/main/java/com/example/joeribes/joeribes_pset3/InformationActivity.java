package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView txtProduct = (TextView) findViewById(R.id.trackName);

        Intent i = getIntent();
        // Getting attached intent data
        String product = i.getStringExtra("listview");
        String songURL = i.getStringExtra("songURL");
        txtProduct.setText(product);

        // Display the image of the Track
        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(songURL);


    }
}
