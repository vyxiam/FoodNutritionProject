package com.cst2335.foodnutritionproject.SearchFragements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.Data.Nutrient;
import com.cst2335.foodnutritionproject.R;
import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;
import com.cst2335.foodnutritionproject.databinding.FragmentSearchDetailsBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSearchDetailsBinding fragmentSearchDetailsBinding;
    public SearchDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDetailsFragment newInstance(String param1, String param2) {
        SearchDetailsFragment fragment = new SearchDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchDetailsBinding = FragmentSearchDetailsBinding.inflate(getLayoutInflater());
        View view = fragmentSearchDetailsBinding.getRoot();
        initialize();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            loadDetails(bundle);
        }

    }

    private void initialize(){
        fragmentSearchDetailsBinding.detailsFoodLogo.setElevation(2f);
        Bitmap foodLogo = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        fragmentSearchDetailsBinding.detailsFoodLogo.setImageBitmap(CustomViewUtility.getViewUtilityClass().getRoundedBitmap(foodLogo,50));
        Animation circleRotation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.circle_rotation);
        fragmentSearchDetailsBinding.detailsFoodBackground.startAnimation(circleRotation);
    }

    private void loadDetails(Bundle bundle){
        Food food = new Food();
        food.setFoodID(bundle.getString("ID"));
        food.setLabel(bundle.getString("NAME"));
        food.setFoodContentsLabel(bundle.getString("DESCRIPTION"));
        double[] nutrients = bundle.getDoubleArray("NUTRIENTS");
        Nutrient nutrient = new Nutrient(nutrients[0],nutrients[1],nutrients[2],nutrients[3],nutrients[4]);
        food.setNutrient(nutrient);

        fragmentSearchDetailsBinding.detailsTitle.setText(food.getLabel());
        fragmentSearchDetailsBinding.detailsDescriptionContent.setText(food.getFoodContentsLabel());
        fragmentSearchDetailsBinding.detailsCaloriesValue.setText(
                String.format(Locale.CANADA, Double.toString(food.getNutrient().getCalories())));
        fragmentSearchDetailsBinding.detailsProteinValue.setText(
                String.format(Locale.CANADA,Double.toString(food.getNutrient().getProtein())));
        fragmentSearchDetailsBinding.detailsFatValue.setText(
                String.format(Locale.CANADA,Double.toString(food.getNutrient().getFat())));
        fragmentSearchDetailsBinding.detailsCarbValue.setText(
                String.format(Locale.CANADA,Double.toString(food.getNutrient().getCarbohydrate())));
        fragmentSearchDetailsBinding.detailsFiberValue.setText(
                String.format(Locale.CANADA,Double.toString(food.getNutrient().getCarbohydrate())));
    }
}