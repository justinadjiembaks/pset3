package com.example.joeribes.joeribes_pset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    EditText etTrack;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        etTrack = (EditText) findViewById(R.id.etTrack);
        assert etTrack != null;
        etTrack.setHint("Search for a Track");

    }

    public void trackSearch(View view) {
        String trackSearch = etTrack.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        asyncTask.execute(trackSearch);
        etTrack.getText().clear();
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
