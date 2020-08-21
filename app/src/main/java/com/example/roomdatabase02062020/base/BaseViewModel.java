package com.example.roomdatabase02062020.base;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel implements LifecycleObserver {
    private  MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init(){
        mLoading.setValue(false);
    }
    public synchronized void setLoading(Boolean isLoading){
        mLoading.setValue(isLoading);
    }

    public LiveData<Boolean> getLoading(){
        return mLoading;
    }
}
