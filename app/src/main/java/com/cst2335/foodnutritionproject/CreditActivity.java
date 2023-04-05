package com.cst2335.foodnutritionproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.cst2335.foodnutritionproject.Credit.CreditFragment;
import com.cst2335.foodnutritionproject.databinding.ActivityCreditBinding;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreditBinding binding = ActivityCreditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CreditFragment fragment = new CreditFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.creditFrame,fragment).commit();
    }
}