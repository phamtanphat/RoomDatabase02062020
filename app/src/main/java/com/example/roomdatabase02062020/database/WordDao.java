package com.example.roomdatabase02062020.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabase02062020.database.entity.WordEnity;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface WordDao {

    @Query("SELECT * FROM word ")
    Observable<List<WordEnity>> getAllWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Maybe<Long> saveWord(WordEnity wordEnity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    Maybe<Integer> updateWord(WordEnity wordEnity);

    @Delete
    Maybe<Integer> deleteWord(WordEnity wordEnity);
}
