package com.cst2335.foodnutritionproject.SearchFragements;

/**
 * This interface definition for a callback to be invoked when a food item is selected
 *
 * @author Van Vy Nguyen
 * @version 1.0
 */
public interface FoodDetails {
    /**
     * This method is called when a food search result is selected
     * @param position the clicked position in the RecyclerView
     */
    void onClickedToDetails(int position);
}
