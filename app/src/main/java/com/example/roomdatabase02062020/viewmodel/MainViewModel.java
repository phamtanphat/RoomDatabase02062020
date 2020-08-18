package com.example.roomdatabase02062020.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.example.roomdatabase02062020.model.entity.WordEnity;
import com.example.roomdatabase02062020.repository.WordRepository;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<WordEnity>> mArrayWords;
    private MutableLiveData<Long> mRowId;
    private MutableLiveData<String> mError;
    private WordRepository mWordRepository;

    public MainViewModel(Context context) {
        mArrayWords = new MutableLiveData<>();
        mRowId = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mWordRepository = WordRepository.getInstance(context);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init(){
        if (mArrayWords == null){
            mArrayWords = new MutableLiveData<>();
        }
    }
    public LiveData<String> getError(){
        return mError;
    }

    @SuppressLint("CheckResult")
    public void callDataWords(){
        mWordRepository
                .getAllWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WordEnity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<WordEnity> wordEnities) {
                        mArrayWords.setValue(wordEnities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mError.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public LiveData<List<WordEnity>> getWords(){
        return mArrayWords;
    }

    public void saveWord(WordEnity wordEnity){
        mWordRepository
                .saveWord(wordEnity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        if (aLong == -1){
                            mError.setValue("Thêm thất bại");
                        } else {
                            mRowId.setValue(aLong);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mError.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public LiveData<Long> getInsertRowId(){
        return mRowId;
    }

    public void updateWord(WordEnity wordEnity){
        mWordRepository
                .updateWord(wordEnity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer == -1){
                            mError.setValue("Cập nhật thất bại");
                        } else {
                            mRowId.setValue(Long.valueOf(integer.toString()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mError.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void deleteWord(WordEnity wordEnity){
        mWordRepository
                .deleteWord(wordEnity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer == -1){
                            mError.setValue("Cập nhật thất bại");
                        } else {
                            mRowId.setValue(Long.valueOf(integer.toString()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mError.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear(){
        if (mArrayWords != null){
            mArrayWords = null;
        }
    }
}
