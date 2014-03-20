package com.anra.souschef;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class SearchActivity extends Activity {

	EditText search;
	LinearLayout checkBoxContainer;
	String [] checklist;
	Map<String,CheckBox> ingredients = new HashMap<String,CheckBox>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		search = (EditText) findViewById(R.id.search);
		checkBoxContainer = (LinearLayout)findViewById(R.id.search_panel);
		for(String s : MainMenu.allIngredients.keySet()){
			CheckBox cb = new CheckBox(this);
			cb.setText(s);
			cb.setChecked(MainMenu.allIngredients.get(s).isChecked());
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton btn, boolean b) {
				// TODO Auto-generated method stub
					
					MainMenu.allIngredients.get(btn.getText()).setChecked(b);
					Log.e("blah",btn.getText() + " " + MainMenu.allIngredients.get(btn.getText()).isChecked());

				}
			});
			cb.setVisibility(View.GONE);
			ingredients.put(s, cb);
			checkBoxContainer.addView(cb);
			
		}
	}
	
	TextWatcher textWatcher = new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
			
	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	    }

	    @Override
	    public void afterTextChanged(Editable s) {
	    	if (s.toString().equals("")){
	    		for (CheckBox cb : ingredients.values()){
	    			cb.setVisibility(View.GONE);
	    		}
	    		return;
	    	}
	        for (String key : ingredients.keySet()){
	        	if (key.toLowerCase().startsWith((s.toString().toLowerCase()))){
	        		ingredients.get(key).setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		ingredients.get(key).setVisibility(View.GONE);
	        	}
	        }
	    }

	};
	
	@Override
	public void onResume(){
		super.onResume();
		search.addTextChangedListener(textWatcher);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		checkBoxContainer.removeAllViews();
	}

}
