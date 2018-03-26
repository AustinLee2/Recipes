package com.austinhlee.android.recipes;

import android.content.Context;
import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Austin Lee on 3/5/2018.
 */

public class Recipe {

    private String title;
    private String image;
    private String url;
    private String description;
    private int servings;
    private String prepTime;
    private String dietLabel;

    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<>();

        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);

            JSONArray recipes = json.getJSONArray("recipes");

            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.setTitle(recipes.getJSONObject(i).getString("title"));
                recipe.setImage(recipes.getJSONObject(i).getString("image"));
                recipe.setUrl(recipes.getJSONObject(i).getString("url"));
                recipe.setDescription(recipes.getJSONObject(i).getString("description"));
                recipe.setServings(recipes.getJSONObject(i).getInt("servings"));
                recipe.setPrepTime(recipes.getJSONObject(i).getString("prepTime"));
                recipe.setDietLabel(recipes.getJSONObject(i).getString("dietLabel"));
                //add to arrayList
                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getDietLabel() {
        return dietLabel;
    }

    public void setDietLabel(String dietLabel) {
        this.dietLabel = dietLabel;
    }
}
