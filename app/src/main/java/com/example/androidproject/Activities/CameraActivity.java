package com.example.androidproject.Activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.androidproject.R;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";
    private Toolbar toolbar;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int PERMISSION_CODE = 1000;
    private ImageView imageView;
    private Button btnCamera;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    Uri image_uri;
    private Boolean screenMode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Log.d(TAG, "onCreate: Starting Camera Activity");

        Button btnBackToMain = (Button) findViewById(R.id.btnBackToMain);

        btnCamera = (Button)findViewById(R.id.btnCamera);
        imageView = (ImageView)findViewById(R.id.imageView);

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

        View cameraView = findViewById(R.id.cameraView);
        View mainMenuView = findViewById(R.id.myToolbar);

        if(screenMode){
            cameraView.setBackgroundColor(getResources().getColor(R.color.colorDark));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.toolbarDark));

        } else {
            cameraView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener to go back to the main page
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btnBackToMain clicked");
                Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Listener to open the camera application
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checks if your device's current OS >= Marshmallow
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });
    }

    private void openCamera(){
        Log.d(TAG, "openCamera: Opening Camera Application");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Bitmap imageBitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageURI(image_uri);
        }
    }

    // Request access to the camera application
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT);
                }
            }
        }
    }

}
