package com.example.androidproject.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.androidproject.R;

public class Functions {
    // Changes frame layout inside of the activity_unsplash.xml
    // Separate because I was going to integrate a Favourites section but made another Activity for it instead
    public static void changeMainFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
}
