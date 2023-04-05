package com.cst2335.foodnutritionproject.Credit;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cst2335.foodnutritionproject.R;
import com.cst2335.foodnutritionproject.databinding.FragmentCreditBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCreditBinding binding;
    private Credentials credentials = new Credentials();
    private int position;

    public CreditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditFragment newInstance(String param1, String param2) {
        CreditFragment fragment = new CreditFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentCreditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        position = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.credit_instruction))
                .setMessage(getString(R.string.credit_instruction_message))
                .setPositiveButton(getString(R.string.credit_instruction_dismiss), null).
                create()
                .show();

        Bitmap bitmapOriginal = BitmapFactory.decodeResource(getResources(),R.drawable.mohamed);
        binding.creditLogo.setImageBitmap(bitmapOriginal);
        binding.creditName.setText(credentials.getAuthors().get(position));
        binding.creditDescription.setText(credentials.getDescriptions().get(position));
        binding.creditNextButton.setOnClickListener(v -> {
            position++;
            if (position > 2){
                position = 0;
                Snackbar snackbar = Snackbar.make(binding.getRoot(),getString(R.string.credit_last_author),Snackbar.LENGTH_LONG);
                snackbar.show();
            }


            Toast.makeText(requireContext(), getString(R.string.credit_next_toast), Toast.LENGTH_SHORT).show();
            switch(position){
                case 0:{//Mohamed
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.mohamed);
                    binding.creditLogo.setImageBitmap(bitmap);
                    break;
                }
                case 1:{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.nhuan);
                    binding.creditLogo.setImageBitmap(bitmap);
                    break;
                }
                case 2:{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.vy);
                    binding.creditLogo.setImageBitmap(bitmap);
                    break;
                }
            }

            binding.creditName.setText(credentials.getAuthors().get(position));
            binding.creditDescription.setText(credentials.getDescriptions().get(position));
        });
        return view;
    }
}