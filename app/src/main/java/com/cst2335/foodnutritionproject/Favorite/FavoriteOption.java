package com.cst2335.foodnutritionproject.Favorite;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.R;
import com.cst2335.foodnutritionproject.SearchFragements.FoodDetails;
import com.cst2335.foodnutritionproject.databinding.ActivityFavoriteOptionBinding;

public class FavoriteOption extends AppCompatActivity implements FoodDetails {
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
