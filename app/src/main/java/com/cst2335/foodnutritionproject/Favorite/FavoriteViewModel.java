package com.cst2335.foodnutritionproject.Favorite;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cst2335.foodnutritionproject.Data.Food;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Food>> favorite = new MutableLiveData< >();
    private MutableLiveData<Food> food = new MutableLiveData<>();
    private MutableLiveData<FavoriteViewList> adapter = new MutableLiveData<>();


    public MutableLiveData<Food> getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food.setValue(food);
    }

    public MutableLiveData<FavoriteViewList> getAdapter() {
        return adapter;
    }

    public void setAdapter(FavoriteViewList adapter) {
        this.adapter.setValue(adapter);
    }
}
