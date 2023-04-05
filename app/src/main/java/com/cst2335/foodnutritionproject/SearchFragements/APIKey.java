package com.cst2335.foodnutritionproject.SearchFragements;

import com.cst2335.foodnutritionproject.Utility.CustomViewUtility;

/**
 * This enum contains the Credentials of API request including ID, KEY and the URL to access
 * the Food API
 *
 * @author Van Vy Nguyen
 * @version 1.0
 */
enum APIKey {
    /**
     * Enum value represents the Request API URL
     */
    URL(APIKey.REQUEST_URL);
    private static final String APPLICATION_ID = "dd756cb3";
    private static final String APPLICATION_KEY = "5940ed464424df230e4e99d060a36e00";

    private static final String URL_PARSER = "https://api.edamam.com/api/food-database/v2/parser?";
    private static final String URL_APP_ID = "app_id=";
    private static final String URL_APP_KEY = "app_key=";
    private static final String URL_FOOD_KEYWORD = "ingr=";
    private static final String AND = "&";
    private static final String REQUEST_URL = URL_PARSER + URL_APP_ID + APPLICATION_ID +
            AND + URL_APP_KEY + APPLICATION_KEY +
            AND + URL_FOOD_KEYWORD;
    private String value;
    APIKey(String value) {
        this.value = value;
    }

    /**
     * This setter method is used to get the URL link included ID, and KEY to access the API
     * @return the link to access the API
     */
    public String getValue(){
        return value;
    }
}
