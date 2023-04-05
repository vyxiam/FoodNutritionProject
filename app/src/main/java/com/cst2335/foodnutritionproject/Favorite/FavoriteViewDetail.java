package com.cst2335.foodnutritionproject.Favorite;

import android.content.Context;
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
import com.cst2335.foodnutritionproject.SearchFragements.FoodDetails;
import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;
import com.cst2335.foodnutritionproject.databinding.FragmentFavoriteViewDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteViewDetail#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FavoriteViewDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentFavoriteViewDetailBinding binding;
    private FavoriteViewModel viewModel;
    private FoodDAO fDAO;
    private int clickPosition;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteViewDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteViewDetail newInstance(String param1, String param2) {
        FavoriteViewDetail fragment = new FavoriteViewDetail();
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
        binding =FragmentFavoriteViewDetailBinding.inflate(getLayoutInflater(),container,false);
        View view=binding.getRoot();
        FoodDatabase db = FoodDatabase.getInstance(requireContext());
        fDAO = db.foodDAO();

        initialization();
        binding.unfavorite.setOnClickListener(v->{
            if (viewModel.getFood().getValue() != null){
                deleteFavoriteFood(viewModel.getFood().getValue());
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
                if (clickPosition != -1){
                    viewModel.getAdapter().getValue().notifyAdapter(clickPosition);
                    clickPosition = -1;
                }

            }
            else
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.toast_message_na), Toast.LENGTH_SHORT).show();

        });


        return view;


    }

    /**
     * This private method is used to initialize the main activity by setting up the logo, buttons
     * with customized attributes
     */
    private void initialization(){
        setUpLogo();

    }

    /**
     * This private method is used to setup the main logo of the main Activity. The logo section in
     * the layout contains 2 ImageViews (logo, logoBackground) and this method is used to control
     * them.
     * It sets the elevation of the logo to make it appear in front of the background and then uses
     * the ImageViewUtility class static method to crop the logo and apply the animation for the
     * background.
     */
    private void setUpLogo(){
        binding.mainLogo.setElevation(2f);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        binding.mainLogo.setImageBitmap(CustomViewUtility.getViewUtilityClass().getRoundedBitmap(logoBitmap,30));
        Animation circleRotation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.circle_rotation);
        binding.mainLogoBackground.startAnimation(circleRotation);
    }
    /**
     * This override method called when the fragment's view is created to fill up the content
     * of the fragments. It call
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
     * This method loads the information from bundle into the views of Favorite View Detail. It is
     * can be called directly from the activity to set the value of Food, info, Database ID, and
     * unfavorite.
     * @param bundle the passed-in values from other fragments
     */
    public void loadDetails(Bundle bundle){
        double calories = bundle.getDouble("CALORIES");
        double protein = bundle.getDouble("PROTEIN");
        double carbohydrate = bundle.getDouble("CARBOHYDRATE");
        double fat = bundle.getDouble("FAT");
        double fiber = bundle.getDouble("FIBER");
        String description =bundle.getString("DESCRIPTION");
        String foodlabel =bundle.getString("LABEL");
        clickPosition = bundle.getInt("POSITION");


        binding.caloriesText.setText(String.format("%.2f",calories));
        binding.proteintext.setText(String.format("%.2f",protein));
        binding.carbonhydrateText.setText(String.format("%.2f",carbohydrate));
        binding.fatText.setText(String.format("%.2f",fat));
        binding.fiberText.setText(String.format("%.2f",fiber));
        binding.descriptionText.setText("Description:  "+description);
        binding.foodLable.setText(foodlabel);
    }


    public void setViewModel(FavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }
    private void deleteFavoriteFood(Food food){
        new Thread(()->{
            fDAO.removeFood(food);

        }).start();
    }
}