package com.example.roomdatabase02062020;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyListenerLifeCycle implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(){
        Log.d("BBB","onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void destroy(){
        Log.d("BBB","onStop");
    }
}
