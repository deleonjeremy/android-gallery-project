package com.example.androidproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.androidproject.Models.Photo;
import com.example.androidproject.R;
import com.example.androidproject.Utils.DatabaseHelper;
import com.example.androidproject.Utils.GlideApp;
import com.example.androidproject.Webservices.ApiInterface;
import com.example.androidproject.Webservices.ServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualPhoto extends AppCompatActivity {

    DatabaseHelper db;

    @BindView(R.id.individual_photo)
    ImageView individualPhoto;

    private static final String TAG = "IndividualActivity";
    private boolean screenMode;
    private Unbinder unbinder;
    private Toolbar toolbar;
    private Photo getPicUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        unbinder = ButterKnife.bind(this);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get photo info
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);

        // SharedPreferences
        androidx.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        // Screen Mode
        Boolean screenModePreference = sharedPrefs.getBoolean("screen_mode", false);
        screenMode = screenModePreference.booleanValue();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        View individualPhotoUnsplash = findViewById(R.id.individual_activity_unsplash_view);
        View mainMenuView = findViewById(R.id.myToolbar);

        if(screenMode){
            individualPhotoUnsplash.setBackgroundColor(getResources().getColor(R.color.colorDark));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.toolbarDark));

        } else {
            individualPhotoUnsplash.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainMenuView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    // Make request to get photo using ApiInterface call
    private void getPhoto(String id){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if(response.isSuccessful()){
                    Photo photo = response.body();
                    getPicUrl = photo;
                    updateUI(photo);
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }

    private void updateUI(Photo photo){
        try{
            GlideApp.with(IndividualPhoto.this)
                    .load(photo.getUrl().getRegular())
                    .centerInside()
                    .into(individualPhoto);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        menu.findItem(R.id.favourite_icon).setVisible(true);
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
            case R.id.favourite_icon:
                db = new DatabaseHelper(this);
                Boolean x = false;
                if(x == false) {
                    x = true;
                    Boolean insertFav = db.insertFavourite(getPicUrl.getUrl().getRegular());
                    if(insertFav) {
                        Toast.makeText(getApplicationContext(), "Added to Favourites " + getPicUrl.getUrl().getRegular(), Toast.LENGTH_SHORT).show();
                    }
                }
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
