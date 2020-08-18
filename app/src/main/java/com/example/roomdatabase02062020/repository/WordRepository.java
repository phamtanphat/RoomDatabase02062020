package com.example.roomdatabase02062020.repository;

import android.content.Context;

import com.example.roomdatabase02062020.database.AppDatabase;
import com.example.roomdatabase02062020.database.WordDao;
import com.example.roomdatabase02062020.database.entity.WordEnity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class WordRepository {
    private static WordRepository mWordRepository = null;
    private WordDao mWordDao = null;

    private WordRepository(Context context){
        mWordDao = AppDatabase.getInStance(context).foodDao();
    }
    public synchronized static WordRepository getInstance(Context context){
        if (mWordRepository == null){
            mWordRepository = new WordRepository(context);
        }
        return mWordRepository;
    }

    public Observable<List<WordEnity>> getAllWords(){
        return mWordDao.getAllWords();
    }
    public Maybe<Long> saveWords(WordEnity wordEnity){
        return mWordDao.saveWord(wordEnity);
    }
}
