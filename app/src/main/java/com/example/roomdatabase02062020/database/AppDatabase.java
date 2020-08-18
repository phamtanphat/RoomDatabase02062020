package com.example.roomdatabase02062020.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabase02062020.database.entity.WordEnity;


@Database(entities = WordEnity.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDao foodDao();

    private static AppDatabase appDatabase = null;

    public synchronized static AppDatabase getInStance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "DatabaseWord.sql"
            )
                    .build();
        }
        return appDatabase;
    }
}
