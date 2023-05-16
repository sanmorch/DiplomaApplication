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

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
