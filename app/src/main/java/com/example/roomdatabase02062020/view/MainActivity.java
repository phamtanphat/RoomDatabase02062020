package com.example.roomdatabase02062020.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        getLifecycle().addObserver(mainViewModel);

        // observer

        mainViewModel.getWords().observe(this, foodEnities -> {
            Toast.makeText(MainActivity.this, foodEnities.size() + "", Toast.LENGTH_SHORT).show();
        });

        // event
        mainViewModel.callDataWords();

      }
}