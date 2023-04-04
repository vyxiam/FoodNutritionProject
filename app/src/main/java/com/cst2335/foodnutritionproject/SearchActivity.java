package com.cst2335.foodnutritionproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.SearchFragements.FoodDetails;
import com.cst2335.foodnutritionproject.Data.Nutrient;
import com.cst2335.foodnutritionproject.SearchFragements.SearchDetailsFragment;
import com.cst2335.foodnutritionproject.SearchFragements.SearchFragment;
import com.cst2335.foodnutritionproject.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity implements FoodDetails {

    private ActivitySearchBinding searchBinding;
    private SearchFragment searchFragment;
    private SearchDetailsFragment detailsFragment;
    private FragmentManager fragmentManager;
    private FrameLayout detailsFrameLayout; //for right frame layout apply to tablet


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());

        detailsFrameLayout = searchBinding.detailsFrame;
        fragmentManager = getSupportFragmentManager();

        searchFragment = new SearchFragment();
        detailsFragment = new SearchDetailsFragment();

        if (detailsFrameLayout == null) { //phone is on use
            fragmentManager.beginTransaction().add(R.id.searchFrame,searchFragment).commit();
        }

        if (detailsFrameLayout != null){ //tablet is on use
            fragmentManager.beginTransaction().replace(R.id.searchFrame,searchFragment).add(R.id.detailsFrame,detailsFragment).commit();
        }
    }

    @Override
    public void onClickedToDetails(int position) {
        Food food = searchFragment.getClickedFood(position);
        Bundle bundle = new Bundle();
        bundle.putString("ID", food.getFoodID());
        bundle.putString("NAME", food.getLabel());
        bundle.putString("DESCRIPTION", food.getFoodContentsLabel());
        double[] nutrients = {food.getCalories(),food.getProtein(),food.getFat(),food.getCarbohydrate(),food.getFiber()};
        bundle.putDoubleArray("NUTRIENTS",nutrients);


        if (detailsFrameLayout == null) { //phone is on use
            detailsFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.searchFrame,detailsFragment).addToBackStack("searchFragment").commit();
        }

        if (detailsFrameLayout != null){ //tablet is on use
            detailsFragment.loadDetails(bundle);
            fragmentManager.beginTransaction().replace(R.id.detailsFrame,detailsFragment).commit();
        }
    }
}