package com.cst2335.foodnutritionproject.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {
    private static final String DATABASE = "FoodNutrient";
    public abstract FoodDAO foodDAO();
    private static FoodDatabase foodDatabase;

    public static synchronized FoodDatabase getInstance(Context context){
        if (foodDatabase == null){
            foodDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                                FoodDatabase.class, DATABASE)
                                .fallbackToDestructiveMigration()
                                .build();
        }
        return foodDatabase;
    }
}
