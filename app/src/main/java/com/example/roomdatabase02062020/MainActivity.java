package com.example.roomdatabase02062020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyListenerLifeCycle myListenerLifeCycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListenerLifeCycle = new MyListenerLifeCycle();
        getLifecycle().addObserver(myListenerLifeCycle);

    }
}