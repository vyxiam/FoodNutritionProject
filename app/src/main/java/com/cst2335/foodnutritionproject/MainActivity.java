package com.cst2335.foodnutritionproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;
import com.cst2335.foodnutritionproject.databinding.ActivityMainBinding;

/**
 * This Activity is the main activity of the project which is the entrance of the Food Nutrition app.
 * This inflates the activity main layout using ViewBinding and uses the activity_main layout.
 *
 * @author Van Vy Nguyen
 * @see CustomViewUtility
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot()); //inflate the layout using binding

        initialize(); //initialize the Views in the layout

        mainBinding.mainSearchButton.setOnClickListener(view -> toSearchActivity()); //search button
        // favorite button
        mainBinding.mainFavoriteButton.setOnClickListener(view -> toSearchFavoriteOption());

    }

    /**
     * This private method is used to initialize the main activity by setting up the logo, buttons
     * with customized attributes. Also, it sets the CustomViewUtility field screenHeight to store
     * device's screen height for later use.
     */
    private void initialize(){
        setUpLogo();
        CustomViewUtility.getViewUtilityClass().setScreenHeight(getResources().getDisplayMetrics().heightPixels);
        setSupportActionBar(mainBinding.mainToolBar);
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
        mainBinding.mainLogo.setElevation(2f);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        mainBinding.mainLogo.setImageBitmap(CustomViewUtility.getViewUtilityClass().getRoundedBitmap(logoBitmap,30));
        Animation circleRotation = AnimationUtils.loadAnimation(this, R.anim.circle_rotation);
        mainBinding.mainLogoBackground.startAnimation(circleRotation);
    }

    /**
     * This private helper method is used to define the action of SEARCH button in the layout
     * Note:
     * - This method is used for refactoring purpose to improve coupling and cohesion
     */
    private void toSearchActivity(){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    private void toSearchFavoriteOption(){
        Intent intent = new Intent(MainActivity.this, FavoriteOptionActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
}