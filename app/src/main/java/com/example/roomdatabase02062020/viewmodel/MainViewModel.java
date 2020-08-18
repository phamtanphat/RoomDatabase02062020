package com.example.roomdatabase02062020.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.example.roomdatabase02062020.database.entity.WordEnity;
import com.example.roomdatabase02062020.repository.WordRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<WordEnity>> mArrayWords;
    private WordRepository mWordRepository;

    public MainViewModel(Context context) {
        mArrayWords = new MutableLiveData<>();
        mWordRepository = WordRepository.getInstance(context);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init(){
        if (mArrayWords == null){
            mArrayWords = new MutableLiveData<>();
        }
    }

    @SuppressLint("CheckResult")
    public void callDataWords(){
        mWordRepository
                .getAllWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodEnities -> mArrayWords.setValue(foodEnities));
    }
    public LiveData<List<WordEnity>> getWords(){
        return mArrayWords;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear(){
        if (mArrayWords != null){
            mArrayWords = null;
        }
    }
}
