package com.anra.souschef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.anra.souschef.R;
import com.anra.souschef.R.layout;
import com.anra.souschef.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView foodListView;
    List<String> listHeaders;
    LinkedHashMap<String, List<String>> foodMap;
    
    EditText search;
    
    private void prepareListData() {
        listHeaders = new ArrayList<String>();
        foodMap = new LinkedHashMap<String, List<String>>();
 
        // Adding child data
        listHeaders.add("Vegetables");
        listHeaders.add("Meats");
        listHeaders.add("Fruits");
 
        // Adding child data
        List<String> vegetables = new ArrayList<String>();
        vegetables.add("Celery");
        vegetables.add("Brocolli");
 
        List<String> meats = new ArrayList<String>();
        meats.add("chicken");
        meats.add("beef");
 
        List<String> fruits = new ArrayList<String>();
        fruits.add("banana");
        fruits.add("apple");
 
        foodMap.put(listHeaders.get(0), vegetables); // Header, Child data
        foodMap.put(listHeaders.get(1), meats);
        foodMap.put(listHeaders.get(2), fruits);
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Getting the list view
		foodListView = (ExpandableListView) findViewById(R.id.ingredientsList);
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listHeaders, foodMap);
		foodListView.setAdapter(listAdapter);
		for(int i=0; i < listAdapter.getGroupCount(); i++)
		    foodListView.expandGroup(i);
		
		foodListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkListItem);
				checkBox.setChecked(!checkBox.isChecked());
				return true;
			}
		});
        

		//create the search
		search = (EditText) findViewById(R.id.searchText);
		search.addTextChangedListener(new TextWatcher(){
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		        MainActivity.this.listAdapter.getFilter().filter(cs);   
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
		        // TODO Auto-generated method stub
		         
		    }

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	


}
