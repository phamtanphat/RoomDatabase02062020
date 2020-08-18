package com.example.roomdatabase02062020.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.database.entity.WordEnity;
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
        mainViewModel.getInsertRowId().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Toast.makeText(MainActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
            }
        });
        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("BBB",s);
            }
        });

        // event
        mainViewModel.callDataWords();
        mainViewModel.saveWord(new WordEnity("One","Mot",0));

      }
}