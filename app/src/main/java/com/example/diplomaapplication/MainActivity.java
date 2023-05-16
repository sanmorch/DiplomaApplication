package com.example.diplomaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.diplomaapplication.View.AboutUsFragment;
import com.example.diplomaapplication.View.FirstCourseFragment;
import com.example.diplomaapplication.View.FourthCourseFragment;
import com.example.diplomaapplication.View.HomeFragment;
import com.example.diplomaapplication.View.SecondCourseFragment;
import com.example.diplomaapplication.View.SettingsFragment;
import com.example.diplomaapplication.View.SignInFragment;
import com.example.diplomaapplication.View.ThirdCourseFragment;
import com.example.diplomaapplication.ViewModel.AuthViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_first_course) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FirstCourseFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_second_course) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SecondCourseFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_third_course) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThirdCourseFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_fourth_course) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FourthCourseFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_about_us) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
        }
        else if (item.getItemId() == R.id.nav_log_out) {
            viewModel.signOut();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SignInFragment()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void init() {
        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }
}
