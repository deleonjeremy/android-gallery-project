package com.example.androidproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.androidproject.R;
import com.example.androidproject.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private Switch switchScreenMode;
    private Boolean screenMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // SharedPreferences
        androidx.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        // Screen Mode (Dark Mode, Light Mode)
        Boolean screenModePreference = sharedPrefs.getBoolean("screen_mode", false);
        screenMode = screenModePreference.booleanValue();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        View settingsView = findViewById(R.id.settingsView);
        View mainMenuView = findViewById(R.id.myToolbar);

        if(screenMode){
            settingsView.setBackgroundColor(getResources().getColor(R.color.colorDark));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.toolbarDark));

        } else {
            settingsView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchScreenMode = findViewById(R.id.screenModeSwitch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        menu.findItem(R.id.refresh).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Log.d(TAG, "onOptionsItemSelected: Settings");
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
