package com.abdok.snapnotes.Ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.abdok.snapnotes.Ui.Home.HomeFragment;
import com.abdok.snapnotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(binding.container.getId() , new HomeFragment() , "home")
                .commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}