package com.cst2335.foodnutritionproject.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cst2335.foodnutritionproject.Data.Food;

import java.util.List;

@Dao
public interface FoodDAO {

    @Insert
    void addFood(Food food);

    @Query("SELECT * FROM Food")
    List<Food> getAllFood();

    @Delete
    void removeFood(Food food);

    @Query("SELECT * FROM Food WHERE foodID = :id")
    Food getFoodByID(int id);

}
