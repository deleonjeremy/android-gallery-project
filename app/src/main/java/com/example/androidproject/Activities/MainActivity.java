package com.example.androidproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidproject.Fragments.PhotosFragment;
import com.example.androidproject.R;
import com.example.androidproject.Utils.Functions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private boolean screenMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting Main Activity");

        Button btnCamera = (Button) findViewById(R.id.btnSecondActivity);
        Button btnGallery = (Button) findViewById(R.id.btnGallery);
        Button btnUnsplash = (Button) findViewById(R.id.btnUnsplash);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        // Get passed intent date
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");
        TextView welcome = findViewById(R.id.txtViewWelcome);
        welcome.setText("Logged in as: " + userEmail);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked btnCamera");

                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked btnGallery");
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        btnUnsplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked btnFlicker");
                Intent intent = new Intent(MainActivity.this, UnsplashActivity.class);
                startActivity(intent);
            }
        });

        // SharedPreferences
        androidx.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        // Screen Mode
        Boolean screenModePreference = sharedPrefs.getBoolean("screen_mode", false);
        screenMode = screenModePreference.booleanValue();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        View mainView = findViewById(R.id.mainView);
        View mainMenuView = findViewById(R.id.myToolbar);

        if(screenMode){
            mainView.setBackgroundColor(getResources().getColor(R.color.colorDark));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.toolbarDark));
        } else {
            mainView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                Log.d(TAG, "onOptionsItemSelected: Settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
