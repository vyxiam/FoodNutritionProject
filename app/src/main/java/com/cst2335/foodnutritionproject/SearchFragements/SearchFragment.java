package com.cst2335.foodnutritionproject.SearchFragements;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cst2335.foodnutritionproject.Data.Food;
import com.cst2335.foodnutritionproject.R;
import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;
import com.cst2335.foodnutritionproject.databinding.FragmentSearchBinding;
import com.cst2335.foodnutritionproject.databinding.SearchResultViewholderBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment class displays an interactive layout where user can search for a food name. After
 * the keyword is enter in the searchInput and a request is sent, the class will handle the response
 * from the API and fetch information into the layout and store the id for details fragment.
 *
 * @author Van Vy Nguyen
 * @version 1.0
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SearchFragment";
    private static final int DEFAULT_RESULT_TYPE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSearchBinding fragmentSearchBinding;
    private RecyclerView.Adapter recyclerAdapter;
    private List<String> resultList = new ArrayList<>();
    private List<Food> foodList = new ArrayList<>();
    private FoodDetails foodDetails;
    private SharedPreferences sharedPreferences;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            foodDetails = (FoodDetails) context;
        }
        catch (ClassCastException classCastException){
            Log.e(TAG, "onAttach() missing Listener in the activity");
        }
    }

    public Food getClickedFood(int position){
        return foodList.get(position);
    }

    /**
     * This override method defines the appearance and behavior of the Fragment.
     * In summary, the order of the function will follow this way:
     * Appearance: initialize() -> setRecyclerAdapter()
     * Behavior: keyword in searchInput -> searchFood() -> handleResponse() -> toFoodFrom(). After
     * this, a List<Food> will be returned from the API and notify the adapter.
     * Then the clicked behavior on a ViewHolder will be transfer to SearchDetailsFragment through
     * the SearchActivity.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(getLayoutInflater());
        View view = fragmentSearchBinding.getRoot();
        initialize();
        setRecyclerAdapter(); //set Recycler Adapter for fragmentSearchBinding
        fragmentSearchBinding.searchRecyclerView.setAdapter(recyclerAdapter);

        fragmentSearchBinding.searchFilter.setOnClickListener(recyclerView -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String searchKeyword = fragmentSearchBinding.searchInput.getText().toString();
            editor.putString("searched", searchKeyword);
            editor.apply();
            showSnakeBar(getResources().getString(R.string.search_snackbar) + " " + searchKeyword);
            searchFood(searchKeyword);
        });

        fragmentSearchBinding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    /**
     * This method is used to initialize the layout by setting the height of each UI element based on
     * the screen height to be compatible with different type of devices.
     */
    private void initialize(){
        sharedPreferences = getActivity().getSharedPreferences("history_search", Context.MODE_PRIVATE);

        int screenHeight = CustomViewUtility.getViewUtilityClass().getScreenHeight();
        int searchInputLayoutHeight = (int)(screenHeight * 0.05);
        int searchTitleHeight = (int)(screenHeight * 0.05);
        int searchRecyclerViewHeight = (int)(screenHeight * 0.7);

        fragmentSearchBinding.searchInputLayout.getLayoutParams().height = searchInputLayoutHeight;
        fragmentSearchBinding.searchTitle.getLayoutParams().height = searchTitleHeight;
        fragmentSearchBinding.searchRecyclerView.getLayoutParams().height = searchRecyclerViewHeight;
        fragmentSearchBinding.searchInput.setHint(setHistorySearch());
    }

    private String setHistorySearch(){
        String value = sharedPreferences.getString("searched","");
        if (value.equals(""))
            return getResources().getString(R.string.search_input);
        else
            return getResources().getString(R.string.search_input_advanced) + " " + value;

    }

    private void showSnakeBar(String message){
        Snackbar snackbar = Snackbar.make(fragmentSearchBinding.getRoot(),message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**
     * This method is used to adjust the height of the RecyclerView in the Fragment based on the runtime
     * screen height. If the height is changed compared to the original height of the device (means
     * the keyboard is on), then the height of the RecyclerView will be squeezed to create better UX.
     * This method will get the original screen height from CustomViewUtility (the height was set when the app
     * is launched), and be compared with the runtime screen by using Rect class
     */
    private void setViewReflectedToKeyboard(){
        Rect rect = new Rect();
        fragmentSearchBinding.searchConstraintLayout.getWindowVisibleDisplayFrame(rect);

        int originalHeight = CustomViewUtility.getViewUtilityClass().getScreenHeight();
        int currentRuntimeHeight = rect.bottom;
        int differentHeight = originalHeight - currentRuntimeHeight;

        int withoutKeyboard = (int) (originalHeight * 0.7);
        int withKeyboard = (int) ( originalHeight * 0.38);

        if (differentHeight > 0) {//keyboard is off due to the addOnGlobalLayoutListener is called first
            fragmentSearchBinding.searchRecyclerView.getLayoutParams().height = withoutKeyboard;
        }
        else  fragmentSearchBinding.searchRecyclerView.getLayoutParams().height = withKeyboard;
    }

    private void setRecyclerAdapter(){
        recyclerAdapter = new RecyclerView.Adapter<ResultViewHolder>() {
            /**
             * This override method sets the layout for the ViewHolder element
             * @param parent   The ViewGroup into which the new View will be added after it is bound to
             *                 an adapter position.
             * @param viewType The view type of the new View.
             * @return the ViewHolder as an element in the RecyclerView
             */
            @NonNull
            @Override
            public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SearchResultViewholderBinding binding = SearchResultViewholderBinding.inflate(getLayoutInflater());
                return new ResultViewHolder(binding.getRoot());
            }

            /**
             * This override method determines how data is bound to the view holder
             * @param holder   The ViewHolder which should be updated to represent the contents of the
             *                 item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
                String foodName = foodList.get(position).getLabel();
                holder.resultText.setText(foodName);
            }

            /**
             * This override method determines the number of items
             * @return
             */
            @Override
            public int getItemCount() {
                return foodList.size();
            }

        };
    }

    private class ResultViewHolder extends RecyclerView.ViewHolder{
        TextView resultText;

        private ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            resultText = itemView.findViewById(R.id.resultText);
            //Set Listener for the ViewHolder when clicking
            itemView.setOnClickListener(viewHolder -> {
                int clickedPosition = getAbsoluteAdapterPosition();
                foodDetails.onClickedToDetails(clickedPosition);
            });
        }

    }

    /**
     * This method will request Nutrient API based on given search keyword and return the related
     * Food objects
     * @param keyword
     */
    private void searchFood(String keyword){
        String url = APIKey.URL.getValue() + keyword;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,
                response -> {
                    if (foodList.size()==0)
                    foodList = handleResponse(response);
                    else{
                        foodList.clear();
                        foodList = handleResponse(response);
                    }
                    //after foodList is added with new information, the Recycler Adapter will be notified
                    //to update the RecyclerView with new content
                    if (foodList.size() == 0)
                        Toast.makeText(getActivity().getApplicationContext(), keyword + " " + getResources().getString(R.string.search_not_found), Toast.LENGTH_SHORT).show();
                    recyclerAdapter.notifyDataSetChanged();
                    new Handler().postDelayed(() -> {
                        fragmentSearchBinding.searchRecyclerView.smoothScrollToPosition(foodList.size()-1);
                    }, 200);
                },
                Throwable::printStackTrace); //TODO: turn into SnakeBar for no connection or sth
        queue.add(request);
    }

    /**
     * This method handles the response from the Nutrient API
     * @param response the response message from the API
     * @return a List of Food objects from API
     */
    private List<Food> handleResponse(JSONObject response){
        List<Food> itemList = new ArrayList<>();
        try{
            JSONArray hints = response.getJSONArray("hints");
            for (int i=0; i <(hints.length() - 1); i++){
                JSONObject hint = hints.getJSONObject(i);
                JSONObject jsonFood = hint.getJSONObject("food");
                itemList.add(toFoodFrom(jsonFood));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return itemList;
    }

    /**
     * This method is used during the request from the Nutrient API to get the Food object from
     * the given JSONObject.
     * @param element the JSONObject from response
     * @return an equivalent to passed-in JSONObject
     */
    private Food toFoodFrom(JSONObject element){
        Food food = new Food();
        boolean doneFetching, contentFound;
        doneFetching = false;
        contentFound = true;

        while (!doneFetching){
            try{
                food.setFoodID(element.getString("foodId"));
                food.setLabel(element.getString("label"));
                food.setKnownAs(element.getString("knownAs"));
                if (contentFound)
                    food.setFoodContentsLabel(element.getString("foodContentsLabel"));
                else
                    food.setFoodContentsLabel(element.getString("categoryLabel"));

                JSONObject nutrients = element.getJSONObject("nutrients");
                food.setCalories(nutrients.getDouble("ENERC_KCAL"));
                food.setProtein(nutrients.getDouble("PROCNT"));
                food.setFat(nutrients.getDouble("FAT"));
                food.setCarbohydrate(nutrients.getDouble("CHOCDF"));
                food.setFiber(nutrients.getDouble("FIBTG"));
                doneFetching = true;
            }
            catch (JSONException e){
                if (contentFound)
                    contentFound = false;
                else
                    doneFetching = true;
            }
        }

        return food;
    }
}