package com.cst2335.foodnutritionproject.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey
    @NonNull
    private String foodID;
    @ColumnInfo(name = "Label")
    private String label;
    @ColumnInfo(name = "KnownAs")
    private String knownAs;
    @ColumnInfo(name = "Contents")
    private String foodContentsLabel;
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

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getFoodContentsLabel() {
        return foodContentsLabel;
    }

    public void setFoodContentsLabel(String foodContentsLabel) {
        this.foodContentsLabel = foodContentsLabel;
    }
}
