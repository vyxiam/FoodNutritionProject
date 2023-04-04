package com.cst2335.foodnutritionproject.Favorite;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cst2335.foodnutritionproject.Data.Food;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Food>> favorite = new MutableLiveData< >();
}
