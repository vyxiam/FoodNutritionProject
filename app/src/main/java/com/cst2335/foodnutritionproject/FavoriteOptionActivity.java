package com.cst2335.foodnutritionproject;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.Favorite.FavoriteViewDetail;
import com.cst2335.foodnutritionproject.Favorite.FavoriteViewList;
import com.cst2335.foodnutritionproject.Favorite.FavoriteViewModel;
import com.cst2335.foodnutritionproject.SearchFragements.FoodDetails;
import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;
import com.cst2335.foodnutritionproject.databinding.ActivityFavoriteOptionBinding;
/**
 * This Activity is secondary activity of the project which is the entrance of the FAVORITE food.
 * This inflates the activity favorite option layout using ViewBinding and uses the activity_favorite_option layout.
 *
 * @author Van Vy Nguyen
 * @see CustomViewUtility
 * @version 1.0
 */

public class FavoriteOptionActivity extends AppCompatActivity implements FoodDetails {
    ActivityFavoriteOptionBinding binding;

    private FavoriteViewList fv;
    private FavoriteViewDetail fvd;
    private FragmentManager fm;
    private FrameLayout fl2;

    /**
     * Create cycleView, separate which is table or phone
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Verify if frame layout 2 is loaded
        fl2 = findViewById(R.id.frameLayout2);
        fm = getSupportFragmentManager();

        FavoriteViewModel favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        // Create the fragments
        fv = new FavoriteViewList();
        fvd = new FavoriteViewDetail();
        favoriteViewModel.setAdapter(fv);
        fv.setViewModel(favoriteViewModel);
        fvd.setViewModel(favoriteViewModel);

        // This is a phone
        if (fl2 == null) {
            fm.beginTransaction().add(R.id.frameLayout1, fv).commit();
        }

        // This is a tablet
        if (fl2 != null) {
            fm.beginTransaction().replace(R.id.frameLayout1, fv).add(R.id.frameLayout2, fvd).commit();
        }
    }

    /**
     * Notify onclick action including a bunch of details. Renew view of fragment when ever change.
     * Transfer the fragment into corresponding layout. If phone, fragment need to be add to backtrack.
     * If not, fragment can't be updated
     * @param position position
     */
    @Override
    public void onClickedToDetails(int position) {
        Food clickedFavorite = fv.getClickedFavorite(position);
        Bundle bundle = new Bundle();
        bundle.putDouble("CALORIES", clickedFavorite.getCalories());
        bundle.putDouble("PROTEIN", clickedFavorite.getProtein());
        bundle.putDouble("CARBOHYDRATE", clickedFavorite.getCarbohydrate());
        bundle.putDouble("FAT", clickedFavorite.getFat());
        bundle.putDouble("FIBER", clickedFavorite.getFiber());
        bundle.putString("DESCRIPTION", clickedFavorite.getFoodContentsLabel());
        bundle.putString("LABEL",clickedFavorite.getLabel());
        bundle.putInt("POSITION",position);


        if (fl2 != null) {
            fvd.loadDetails(bundle);
            fm.beginTransaction().replace(R.id.frameLayout2, fvd).commit();
        }
        if (fl2 == null) {
            fvd.setArguments(bundle);
            fm.beginTransaction().replace(R.id.frameLayout1, fvd).addToBackStack("fv").commit();

        }
    }
}
