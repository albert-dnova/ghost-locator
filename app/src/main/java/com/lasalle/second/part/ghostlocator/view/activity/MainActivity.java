package com.lasalle.second.part.ghostlocator.view.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.lasalle.second.part.ghostlocator.R;
import com.lasalle.second.part.ghostlocator.view.fragment.FirstUserExperienceFragment;
import com.lasalle.second.part.ghostlocator.view.fragment.MapFragment;
import com.lasalle.second.part.ghostlocator.view.fragment.ProfileFragment;
import com.lasalle.second.part.ghostlocator.view.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        FirstUserExperienceFragment.FirstUserExperienceFragmentEvents {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.main_activity_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displayFirstUserExperience();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if((item.getItemId() == R.id.activity_main_bottom_menu_search) ||
           (item.getItemId() == R.id.activity_main_bottom_menu_map) ||
           (item.getItemId() == R.id.activity_main_bottom_menu_profile))
        {
            setUpFragment(item.getItemId());
            return true;
        }

        return false;
    }

    @Override
    public void onStartButtonClicked() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        setUpFragment(R.id.activity_main_bottom_menu_search);
    }

    private void setUpFragment(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment existingFragment = fragmentManager.findFragmentByTag(Integer.toString(id));
        if(existingFragment == null) {
            existingFragment = createFragment(id);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, existingFragment, Integer.toString(id));
        fragmentTransaction.commit();
    }

    private Fragment createFragment(int id) {
        Fragment fragment = null;

        if(id == R.id.activity_main_bottom_menu_search) {
            fragment = SearchFragment.newInstance();
        }
        else if(id == R.id.activity_main_bottom_menu_map) {
            fragment = MapFragment.newInstance();
        }
        else if(id == R.id.activity_main_bottom_menu_profile) {
            fragment = ProfileFragment.newInstance();
        }
        else if(id == R.id.first_user_experience_fragment) {
            fragment = FirstUserExperienceFragment.newInstance();
        }

        return fragment;
    }

    private void displayFirstUserExperience() {
        bottomNavigationView.setVisibility(View.GONE);
        setUpFragment(R.id.first_user_experience_fragment);
    }


}
