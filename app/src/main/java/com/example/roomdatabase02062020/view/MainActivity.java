package com.example.roomdatabase02062020.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.database.AppDatabase;
import com.example.roomdatabase02062020.database.entity.FoodEnity;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase
                .getInStance(this)
                .foodDao()
                .getAllWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FoodEnity>>() {
                    @Override
                    public void accept(List<FoodEnity> foodEnities) throws Exception {
                        Toast.makeText(MainActivity.this, foodEnities.size() + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}