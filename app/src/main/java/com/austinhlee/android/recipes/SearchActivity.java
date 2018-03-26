package com.austinhlee.android.recipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private Context mContext;
    private Button mButton;
    private Spinner mDietSpinner;
    private Spinner mServingSpinner;
    private Spinner mPrepSpinner;

    private String TAG = SearchActivity.class.getName();
    public static String DIET_CATEGORY_KEY = "com.austinhlee.android.recipes.DIET_CATEGORY";
    public static String SERVING_CATEGORY_KEY = "com.austinhlee.android.recipes.SERVING_CATEGORY";
    public static String PREP_CATEGORY_KEY = "com.austinhlee.android.recipes.PREP_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        mDietSpinner = (Spinner) findViewById(R.id.diet_spinner);
        mServingSpinner = (Spinner) findViewById(R.id.serving_spinner);
        mPrepSpinner = (Spinner) findViewById(R.id.prep_spinner);
        mButton = (Button) findViewById(R.id.search_button);

//        String[] dietCats = new String[]{"diet restriction", "serving restriction", "preparation time"};
        List<String> dietCats = getDietCats();
        String[] servingCats = new String[]{"All", "Less than 4", "4-6", "7-9", "More than 10"};
        String[] prepCats = new String[]{"All", "30 mins or less", "Less than 1 hr", "More than 1 hr"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dietCats);
        ArrayAdapter<String> servingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servingCats);
        ArrayAdapter<String> prepAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, prepCats);
        mDietSpinner.setAdapter(dataAdapter);
        mServingSpinner.setAdapter(servingAdapter);
        mPrepSpinner.setAdapter(prepAdapter);
        mDietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, adapterView.getItemAtPosition(i).toString() + " selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG, "Nothing selected");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeListActivity.class);
                intent.putExtra(DIET_CATEGORY_KEY, mDietSpinner.getSelectedItem().toString());
                intent.putExtra(SERVING_CATEGORY_KEY, mServingSpinner.getSelectedItem().toString());
                intent.putExtra(PREP_CATEGORY_KEY, mPrepSpinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }

    private List<String> getDietCats(){
        List<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        List<String> dietCats = new ArrayList<String>();
        dietCats.add("All");
        for (int i = 0; i < recipeList.size(); i++){
            if (dietCats.contains(recipeList.get(i).getDietLabel())){
                continue;
            } else {
                dietCats.add(recipeList.get(i).getDietLabel());
            }
        }
        return dietCats;
    }

}
