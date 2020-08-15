package com.example.roomdatabase02062020.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"en"}, unique = true)})
public class FoodEnity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(defaultValue = "")
    private String en;

    @ColumnInfo(defaultValue = "")
    private String vn;

    @ColumnInfo(defaultValue = "0")
    private Integer ismemorized;

    @Ignore
    public FoodEnity(Long id, String en, String vn, Integer ismemorized) {
        this.id = id;
        this.en = en;
        this.vn = vn;
        this.ismemorized = ismemorized;
    }

    public FoodEnity() {
    }

    public Long getId() {
        return id;
    }


    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public Integer getIsmemorized() {
        return ismemorized;
    }

    public void setIsmemorized(Integer ismemorized) {
        this.ismemorized = ismemorized;
    }
}