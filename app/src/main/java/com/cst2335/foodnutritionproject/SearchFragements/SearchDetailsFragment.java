package com.cst2335.foodnutritionproject.SearchFragements;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.Data.FoodDAO;
import com.cst2335.foodnutritionproject.Data.FoodDatabase;
import com.cst2335.foodnutritionproject.R;
import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;
import com.cst2335.foodnutritionproject.databinding.FragmentSearchDetailsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    private Food food;
    private FoodDatabase database;
    private FoodDAO foodDAO;
    private List<Food> foodList;
    private CountDownLatch latch;
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

    /**
     * This method is called when the View is created
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchDetailsBinding = FragmentSearchDetailsBinding.inflate(getLayoutInflater());
        View view = fragmentSearchDetailsBinding.getRoot();
        Log.e("VIEW", "View Create");
        initialize();
        return view;
    }

    /**
     * This override version will set the bundle
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            loadDetails(bundle);
        }

    }

    /**
     * This helper private method is used to initialize the item attributes.
     */
    private void initialize(){
        if (foodList == null)
            foodList = new ArrayList<>();
        fragmentSearchDetailsBinding.detailsFoodLogo.setElevation(2f);
        Bitmap foodLogo = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        fragmentSearchDetailsBinding.detailsFoodLogo.setImageBitmap(CustomViewUtility.getViewUtilityClass().getRoundedBitmap(foodLogo,50));
        Animation circleRotation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.circle_rotation);
        fragmentSearchDetailsBinding.detailsFoodBackground.startAnimation(circleRotation);
        fragmentSearchDetailsBinding.detailsFavoriteButton.setOnClickListener(view -> showDialog());
    }

    /**
     * This helper method is used to show an instruction for the fragment.
     */
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.details_favorite_instruction_title))
                .setMessage(getString(R.string.details_favorite_instruction_description))
                .setPositiveButton(getString(R.string.details_favorite_instruction_continue),
                        (dialogInterface, i) -> {
                            favoriteFood();
                        }).
                create()
                .show();

    }

    /**
     * This private helper method is used to add the food to the database. This method also calls other
     * helper methods to check whether the food is stored in the database or not.
     */
    private void favoriteFood(){
        boolean isExistInDatabase = checkDatabaseContent(food.getFoodID());
        if (isExistInDatabase){
            Toast.makeText(requireContext(), getResources().getString(R.string.details_food_exist), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(requireContext(), getResources().getString(R.string.details_food_new), Toast.LENGTH_SHORT).show();
            favoriteFood(food);
        }
    }

    /**
     * This method is used to help refresh the items in the fragment with new information by the passed
     * in Bundle object. It contains the Food name, description and nutrients of the food.
     * @param bundle the Bundle object stores new information
     */
    public void loadDetails(Bundle bundle){
        if (food == null)
            food = new Food();

        food.setFoodID(bundle.getString("ID"));
        food.setLabel(bundle.getString("NAME"));
        food.setFoodContentsLabel(bundle.getString("DESCRIPTION"));
        double[] nutrients = bundle.getDoubleArray("NUTRIENTS");
        food.setCalories(nutrients[0]);
        food.setProtein(nutrients[1]);
        food.setFat(nutrients[2]);
        food.setCarbohydrate(nutrients[3]);
        food.setFiber(nutrients[4]);

        fragmentSearchDetailsBinding.detailsTitle.setText(food.getLabel());
        fragmentSearchDetailsBinding.detailsDescriptionContent.setText(food.getFoodContentsLabel());
        fragmentSearchDetailsBinding.detailsCaloriesValue.setText(
                String.format("%.2f", food.getCalories()));
        fragmentSearchDetailsBinding.detailsProteinValue.setText(
                String.format("%.2f",food.getProtein()));
        fragmentSearchDetailsBinding.detailsFatValue.setText(
                String.format("%.2f",food.getFat()));
        fragmentSearchDetailsBinding.detailsCarbValue.setText(
                String.format("%.2f",food.getCarbohydrate()));
        fragmentSearchDetailsBinding.detailsFiberValue.setText(
                String.format("%.2f",food.getCarbohydrate()));
    }

    /**
     * This private helper method is used to check the passed in foodID whether it is stored in the database
     * or not and return a boolean value based on this situation. This is used to help determine whether user
     * wants to add an exist food to the database to avoid duplicated content.
     * @param foodID the passed-in food ID to check
     * @return true if the foodID is stored, false if the foodID doesn't exist in the database
     */
    private boolean checkDatabaseContent(String foodID){
        database = FoodDatabase.getInstance(requireContext());
        foodDAO = database.foodDAO();
        latch = new CountDownLatch(1);
        getAllFoodInDatabase();
        try{latch.await();}
        catch (InterruptedException e){
            e.printStackTrace();
        }

        boolean result = foodList.stream().anyMatch(food -> food.getFoodID().equals(foodID));
        return result;
    }

    /**
     * This private helper method is used to get all the Food data from the database. This will
     * return a List<Food> and added into the foodList instance variable. It also uses CountDownLatch
     * to lock the UI thread to ensure all Foods are loaded before the logic is executed.
     */
    private void getAllFoodInDatabase(){
        new Thread(() -> {
            foodList.addAll(foodDAO.getAllFood());
            latch.countDown();
        }).start();
    }

    /**
     * This private helper method is used to access DAO and add passed-in Food object into the database
     * using a separate thread.
     * @param food the Food object will be added into database
     */
    private void favoriteFood(Food food){
        new Thread(() -> {
            foodDAO.addFood(food);
        }).start();
    }
}