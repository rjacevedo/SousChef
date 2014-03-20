package com.anra.souschef;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class DisplayResults extends Activity {

	public static DisplayResults instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance=this;
		setContentView(R.layout.activity_display_results);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_results, menu);
		return true; 
	}
	
	public void displayResults(String result){
		List<Recipe> recipes = parseJSON(result);
		for (Recipe r : recipes){
			
		}
	}
	
	private List<Recipe> parseJSON(String JSONfile){
		Log.e("blah","parseStart");
		try{
			Log.e(":",JSONfile);
			JSONObject json = new JSONObject(JSONfile);
			JSONArray recipes = json.getJSONArray("matches");
			List<Recipe> parsedRecipes = new ArrayList<Recipe>();
			for (int i = 0; i < recipes.length(); i++){
				JSONObject recipe = recipes.getJSONObject(i);
				System.out.println(recipe.toString());
				String imgUrl = recipe.getString("smallImageUrls");
				List<String> ingredients= new ArrayList<String>();
				JSONArray jsoningredients = recipe.getJSONArray("ingredients");
				for (int j = 0; j < jsoningredients.length(); j++){
					ingredients.add(jsoningredients.getString(j));
				}
				double totalTimeInSec = recipe.getDouble("totalTimeInSeconds");
				int rating = recipe.getInt("rating");
				String url = "http://www.yummly.com/recipe/" + recipe.getString("id");
				parsedRecipes.add(new Recipe(ingredients,url,imgUrl,totalTimeInSec,rating));
			}
			return parsedRecipes;
		}
		catch(JSONException e){
			Log.e("","JSON parse failed");
			return null;
		}
		
	}
	
}
