package com.example.androidproject.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Adapters.GalleryAdapter;
import com.example.androidproject.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    List<CellActivity> allFilePaths;
    private Toolbar toolbar;
    private Boolean screenMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // toolbar
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

        View galleryView = findViewById(R.id.galleryView);
        View mainMenuView = findViewById(R.id.myToolbar);

        if(screenMode){
            galleryView.setBackgroundColor(getResources().getColor(R.color.colorDark));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.toolbarDark));

        } else {
            galleryView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // For storage permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            showImages();
        }
    }

    // Show the images on the screen
    private void showImages(){
        // This is the folder with all the images
        String path = Environment.getExternalStorageDirectory() +"/Pictures/";
        allFilePaths = new ArrayList<>();
        allFilePaths = listAllFiles(path);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CellActivity> cells = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(adapter);
    }

    // prepare the images for the list
    private ArrayList<CellActivity> prepareData(){
        ArrayList<CellActivity> allImages = new ArrayList<>();
        for (CellActivity c : allFilePaths) {
            CellActivity cell = new CellActivity();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return allImages;
    }
    // Load all files from the folder
    private List<CellActivity> listAllFiles(String pathName) {
        List<CellActivity> allFiles = new ArrayList<>();
        File file = new File(pathName);
        File[] files = file.listFiles();

        if(files != null) {
            for (File f: files) {
                CellActivity cell = new CellActivity();
                cell.setTitle(f.getName());
                cell.setPath(f.getAbsolutePath());
                allFiles.add(cell);
            }
        }

        return allFiles;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImages();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
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
