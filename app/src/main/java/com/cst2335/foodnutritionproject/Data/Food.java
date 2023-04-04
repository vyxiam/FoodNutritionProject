package com.cst2335.foodnutritionproject.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Food")
public class Food {
    @PrimaryKey
    private String foodID;
    @ColumnInfo(name = "Label")
    private String label;
    @ColumnInfo(name = "KnownAs")
    private String knownAs;
    @ColumnInfo(name = "Nutrient")
    private Nutrient nutrient;
    @ColumnInfo(name = "Contents")
    private String foodContentsLabel;

    public Food(){
        this.nutrient = new Nutrient();
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

    public Nutrient getNutrient() {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public String getFoodContentsLabel() {
        return foodContentsLabel;
    }

    public void setFoodContentsLabel(String foodContentsLabel) {
        this.foodContentsLabel = foodContentsLabel;
    }
}
