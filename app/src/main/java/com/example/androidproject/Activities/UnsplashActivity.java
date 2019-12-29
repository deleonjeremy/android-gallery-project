package com.example.androidproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.androidproject.Fragments.PhotosFragment;
import com.example.androidproject.R;
import com.example.androidproject.Utils.Functions;

public class UnsplashActivity extends AppCompatActivity {
    private static final String TAG = "UnsplashActivity";
    private Toolbar toolbar;
    private Boolean screenMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsplash);

        // Change fragment to Photos fragment
        PhotosFragment photosFragment = new PhotosFragment();
        Functions.changeMainFragment(UnsplashActivity.this, photosFragment);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // SharedPreferences
        androidx.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        // Screen Mode (Dark Mode, Light Mode)
        Boolean screenModePreference = sharedPrefs.getBoolean("screen_mode", false);
        screenMode = screenModePreference.booleanValue();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        View unsplashView = findViewById(R.id.unsplashView);
        View mainMenuView = findViewById(R.id.myToolbar);

        if(screenMode){
            unsplashView.setBackgroundColor(getResources().getColor(R.color.colorDark));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.toolbarDark));

        } else {
            unsplashView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        menu.findItem(R.id.action_favourites).setVisible(true);
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
            case R.id.action_favourites:
                Intent favouriteIntent = new Intent(this, FavouritesActivity.class);
                startActivity(favouriteIntent);
                Log.d(TAG, "onOptionsItemSelected: Favourites");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
