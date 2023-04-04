package com.cst2335.foodnutritionproject.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Nutrient")
public class Nutrient {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Calories")
    private double calories;
    @ColumnInfo(name = "Protein")
    private double protein;
    @ColumnInfo(name = "Fat")
    private double fat;
    @ColumnInfo(name = "Carbohydrate")
    private double carbohydrate;
    @ColumnInfo(name = "Fiber")
    private double fiber;

    public Nutrient(){

    }

    public Nutrient(double calories, double protein, double fat, double carbohydrate, double fiber) {
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.fiber = fiber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }
}
