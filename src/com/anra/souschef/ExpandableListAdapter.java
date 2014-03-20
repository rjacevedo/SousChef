package com.anra.souschef;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable{
	private Context _context;
	private List<String> _listHeadings;
	private LinkedHashMap<String, List<String>> _listData;
	private LinkedHashMap<String, List<String>> _origlistData;
	private ExpandableListFilter filter;
	
	public ExpandableListAdapter(Context context, List<String> listHeadings,
			LinkedHashMap<String, List<String>> listData){
		this._context = context;
		this._listHeadings = listHeadings;
		this._listData = listData;
		this._origlistData = _listData;
	}
	
	
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listData.get(this._listHeadings.get(groupPosition)).get(childPosititon);
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.food_item, null);
        }
 
        CheckBox txtListChild = (CheckBox) convertView
                .findViewById(R.id.checkListItem);
 
        txtListChild.setText(childText);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listData.get(this._listHeadings.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listData.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listHeadings.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {    	
    	
    	System.out.println("GETTING GROUP VIEW");
    	System.out.println(groupPosition);
    	
    	String headerTitle = _listHeadings.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.food_heading, null);
        }
        

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listHeading);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        
//        ExpandableListView elv = (ExpandableListView) parent;
//        elv.expandGroup(groupPosition);

        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    public void resetData() {
    	_listData = _origlistData;
    }
    
	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ExpandableListFilter();
		}
		
		return filter;
	}
	
	private class ExpandableListFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			constraint = constraint.toString().toLowerCase();
			FilterResults results = new FilterResults();
			
			if (constraint == null || constraint.length() == 0) {
				results.values = _origlistData;
				results.count = _origlistData.size();
			}
			else {
				List<String> filtered_items = new ArrayList<String>();
				LinkedHashMap<String, List<String>> filteredMap = new LinkedHashMap<String, List<String>>();
				
				for (String key : _origlistData.keySet()) {
					for (String listitem : _origlistData.get(key)) {
						if (listitem.toLowerCase().contains(constraint)){
							filtered_items.add(listitem);
						}
					}
				}
				
				filteredMap.put("search", filtered_items);
				results.values = filteredMap;
				results.count = filteredMap.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			
			if (results.count == 0) {
				notifyDataSetInvalidated();
			}
			else {
				List<String> temp = new ArrayList<String>();				
				for (String s : ((LinkedHashMap<String, List<String>>) results.values).keySet()) {
					temp.add(s);
				}
				
				_listHeadings = temp;
				_listData = (LinkedHashMap<String, List<String>>) results.values;
				System.out.println(_listData);
				notifyDataSetChanged();
			}			
		}
		
	}
}
