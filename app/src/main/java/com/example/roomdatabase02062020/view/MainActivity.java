package com.example.roomdatabase02062020.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.database.entity.WordEnity;
import com.example.roomdatabase02062020.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        getLifecycle().addObserver(mainViewModel);

        // observer

        // data words
        mainViewModel.getWords().observe(this, wordEnities  -> {
            Log.d("BBB",wordEnities.toString());
        });

        // data id insert
//        mainViewModel.getInsertRowId().observe(this, new Observer<Long>() {
//            @Override
//            public void onChanged(Long aLong) {
//                Toast.makeText(MainActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//         data errors
        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("BBB",s);
            }
        });

        // event
        //select all
        mainViewModel.callDataWords();
        //insert
//        mainViewModel.saveWord(new WordEnity("Three","Ba",0));
        //update
//        mainViewModel.updateWord(wordEnities.get(0));
        // delete
//        mainViewModel.deleteWord(mArrayWords.get(0));
      }
}