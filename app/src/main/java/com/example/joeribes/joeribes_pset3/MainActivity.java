package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText etTrack;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.navigation_favorites:
                    Intent intent2 = new Intent(getBaseContext(), FavoritesActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        etTrack = (EditText) findViewById(R.id.etTrack);
        assert etTrack != null;
        etTrack.setHint("Search for a Track");

    }

    public void trackSearch(View view) {
        String trackSearch = etTrack.getText().toString();

        if(trackSearch.equals("")){
            Toast.makeText(this.getApplicationContext(), "Please insert at least one character",
                    Toast.LENGTH_SHORT).show();

        } else {
            TrackAsyncTask asyncTask = new TrackAsyncTask(this);
            asyncTask.execute(trackSearch);
            etTrack.getText().clear();
        }
    }

    public void trackStartIntent(Songs[] trackData) {
        Intent dataIntent = new Intent(this, DataActivity.class);

        // Passing the serializable via a bundle
        // https://stackoverflow.com/questions/14333449/passing-data-through-intent-using-serializable
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", trackData);
        dataIntent.putExtras(bundle);

        this.startActivity(dataIntent);
    }



}
