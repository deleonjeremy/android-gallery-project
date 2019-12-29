package com.example.androidproject.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Adapters.FavouritesAdapter;
import com.example.androidproject.Adapters.GalleryAdapter;
import com.example.androidproject.Fragments.PhotosFragment;
import com.example.androidproject.R;
import com.example.androidproject.Utils.DatabaseHelper;
import com.example.androidproject.Utils.Functions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavouritesActivity extends AppCompatActivity {

    DatabaseHelper db;
    private static final String TAG = "FavouritesActivity";
    private Toolbar toolbar;
    private List<String> urlList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        db = new DatabaseHelper(this);
        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Cursor cursor = db.getAllFavourites();

        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(), "NO FAVOURITES FOUND", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                urlList.add(cursor.getString(1));
                //Toast.makeText(getApplicationContext(),"URL: " + cursor.getString(1), Toast.LENGTH_SHORT ).show();
            }
        }

        getPhotoUrls();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getPhotoUrls(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favouritesRecyclerView);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        FavouritesAdapter adapter = new FavouritesAdapter(getApplicationContext(), urlList);
        recyclerView.setAdapter(adapter);
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
