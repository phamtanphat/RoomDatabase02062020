package com.example.roomdatabase02062020.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.roomdatabase02062020.database.entity.FoodEnity;


import java.util.List;

import io.reactivex.Observable;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM food ")
    Observable<List<FoodEnity>> getAllWords();
}
