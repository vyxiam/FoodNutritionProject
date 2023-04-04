package com.cst2335.foodnutritionproject.Favorite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.Data.FoodDAO;
import com.cst2335.foodnutritionproject.Data.FoodDatabase;
import com.cst2335.foodnutritionproject.R;
import com.cst2335.foodnutritionproject.SearchFragements.FoodDetails;
import com.cst2335.foodnutritionproject.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentFavoriteBinding binding;
    ArrayList<Food> favoriteLists;
    private RecyclerView.Adapter myAdapter;
    private FoodDatabase db;
    private FoodDAO mDAO;
    private FavoriteViewModel viewModel;
    private FoodDetails mItemListener;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteView newInstance(String param1, String param2) {
        FavoriteView fragment = new FavoriteView();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    binding =FragmentFavoriteBinding.inflate(getLayoutInflater(),container,false);
    View view=binding.getRoot();
    // interact with database
        db = FoodDatabase.getInstance(requireContext());
        mDAO = db.foodDAO();
        loadAllFoods();
        if(favoriteLists == null)
            favoriteLists = new ArrayList<>();
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                FavoriteList obj = favoriteLists.get(position);
                holder.favoriteView.setText(obj.getFavorites());

            }
            @Override
            public int getItemCount() {
                return favoriteLists.size();
            }

                });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }
    /**
     * This method is called when a fragment is attached to FavoriteOption activity and overriden to
     * construct the field mItemListener
     * @param context the FavoriteOption activity context
     */
    @Override public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mItemListener = (OnItemSelectedListener) context; }
        catch (ClassCastException castException) {
            Log.e("TAG", "error in onAttach(). Missing a Listener in the Activity");
        }
    }
    /**
     * This class is a custom ViewHolder class to be used in the RecyclerView above
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        Button favoriteViewButton;

        /**
         * Construct the class with passed-in view and a listener is added when a view is clicked
         * @param itemView the view
         */
        @SuppressLint("ClickableViewAccessibility")
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAbsoluteAdapterPosition();

                mItemListener.onItemSelected(position);
            });

            itemView.setOnTouchListener( (view, motionEvent) -> {
                return gestureDetector.onTouchEvent(motionEvent);
            });
            favoriteViewButton = itemView.findViewById(R.id.favorite_view);


        }
        /**
         * This method is called when do detele event following by user's gesture
         */
        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Question");
                builder.setMessage("Do you want to delete this Favorite Food:" + favoriteViewButton.getText());
                int position = getAbsoluteAdapterPosition();
                builder.setPositiveButton("Yes", (dialog, which) -> {
                            // Xử lý khi người dùng chọn "Có"
                            deleteNewMessage(favoriteLists.get(position));// delete in database
                            favoriteLists.remove(position);// delete in viewholder
                            myAdapter.notifyItemRemoved(favoriteLists.size());// notify item deleted to adapter
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }
    /**
     * This method will load all the foods from the database into the Food field using a
     * separate thread.
     */
    private void loadAllFoods(){
        new Thread(() -> {
            favoriteLists.addAll(mDAO.getAllFood());
        }).start();
    }
    /**
     * This message will insert a new favorite food into the database using DAO interface using a separate
     * thread.
     * @param favoriteFood the inserted message
     */
    private void insertNewFood(ChatMessage chatMessage){
        new Thread(() -> {
            chatMessage.id = mDAO.insertMessage(chatMessage);
        }).start();
    }
    private void deleteNewMessage(ChatMessage chatMessage){
        new Thread(()->{
            mDAO.deleteMessage(chatMessage);

        }).start();
    }
    /**
     * This message will return the click food object. This method will implement the
     * findFood method with another thread and wait for this thread done before the next code
     * is being executed.
     * @param position the current click position
     * @return the clicked food
     */
    public Food getClickedFavorite(int position){
        return favoriteLists.get(position);
    }

    /**
     * This method will clear all the content of the fragment when pausing. This override version
     * is used to clear the content of previous transaction in the FavoriteOption activity.
     */
    @Override
    public void onPause() {
        super.onPause();

        // Clear the contents of the RecyclerView
        favoriteLists.clear();
        myAdapter.notifyDataSetChanged();
    }

    public void setViewModel(FavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }
 }
}