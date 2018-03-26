package com.austinhlee.android.recipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<Recipe> mRecipeList;
    private List<Recipe> mFilteredList;
    private RecipeListAdapter mRecipeListAdapter;

    public static int UNCATEGORIZED_PREPTIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mContext = this;
        Intent intent = getIntent();
        String diet = intent.getStringExtra(SearchActivity.DIET_CATEGORY_KEY);
        String serving = intent.getStringExtra(SearchActivity.SERVING_CATEGORY_KEY);
        String prep = intent.getStringExtra(SearchActivity.PREP_CATEGORY_KEY);
        mFilteredList = new ArrayList<Recipe>();
        mRecipeList = Recipe.getRecipesFromFile("recipes.json", mContext);
        switch (serving) {
            case "Less than 4":
                mFilteredList = filterByServing(0,4);
                break;
            case "4-6":
                mFilteredList = filterByServing(3,7);
                break;

            case "7-9":
                mFilteredList = filterByServing(6,10);
                break;

            case "More than 10":
                mFilteredList = filterByServing(10,Integer.MAX_VALUE);
                break;

            default:
                mFilteredList = mRecipeList;
        }
        switch (prep){
            case "30 mins or less":
                filterByTime(mFilteredList, 0,31);
                break;
            case "Less than 1 hr":
                filterByTime(mFilteredList, 0,61);
                break;
            case "More than 1 hr":
                filterByTime(mFilteredList, 60,Integer.MAX_VALUE);
                break;
        }
        if (!diet.equalsIgnoreCase("all")) {
            filterByDiet(mFilteredList, diet);
        }
        mRecipeListAdapter = new RecipeListAdapter(mContext, mFilteredList);
        mRecyclerView = (RecyclerView)findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setAdapter(mRecipeListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private List<Recipe> filterByServing(int min, int max){
        List<Recipe> recipes = new ArrayList<Recipe>();
        for (Recipe r: mRecipeList){
            if (r.getServings() < max && r.getServings() > min){
                recipes.add(r);
            }
        }
        return recipes;
    }

    private List<Recipe> filterByTime(List<Recipe> list, int min, int max){
        List<Recipe> recipes = list;
        Iterator<Recipe> iter = recipes.iterator();
        while (iter.hasNext()) {
            Recipe r = iter.next();
            int prepTime = convertToInt(r.getPrepTime());
            if (prepTime > max || prepTime < min) {
                iter.remove();
            }
        }
        return recipes;
        /*for (Recipe r: list){
            int prepTime = convertToInt(r.getPrepTime());
            if (prepTime > max || prepTime < min){
                recipes.remove(r);
            }
        }
        return recipes;*/
    }
    private int convertToInt(String prepString){
        int prepTimeinMins;
        switch (prepString){
            case "25 minutes":
                prepTimeinMins = 25;
                break;
            case "1 hour":
                prepTimeinMins = 60;
                break;
            case "20 minutes":
                prepTimeinMins = 20;
                break;
            case "35 minutes":
                prepTimeinMins = 35;
                break;
            case "6 hours":
                prepTimeinMins = 360;
                break;
            case "1 hour and 20 minutes":
                prepTimeinMins = 80;
                break;
            case "40 minutes":
                prepTimeinMins = 40;
                break;
            case "50 minutes":
                prepTimeinMins = 50;
                break;
            case "3 hours and 15 minutes":
                prepTimeinMins = 195;
                break;
            case "15 minutes":
                prepTimeinMins = 15;
                break;
            default:
                prepTimeinMins = UNCATEGORIZED_PREPTIME;
        }
        return prepTimeinMins;
    }
    private List<Recipe> filterByDiet(List<Recipe> list, String dietChoice){
        List<Recipe> recipes = list;
        Iterator<Recipe> iter = recipes.iterator();
        while (iter.hasNext()) {
            Recipe r = iter.next();
            String diet = r.getDietLabel();
            if (!diet.equalsIgnoreCase(dietChoice)) {
                iter.remove();
            }
        }
        return recipes;
    }
}
