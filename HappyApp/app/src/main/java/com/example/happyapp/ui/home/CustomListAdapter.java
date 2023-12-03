package com.example.happyapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.happyapp.R;
import com.example.happyapp.localDatabase.ShoppingList;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingList> shoppingLists; // Replace User with your data model

    public CustomListAdapter(Context context, List<ShoppingList> shoppingLists) {
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    @Override
    public int getCount() {
        return shoppingLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ShoppingList shoppingList = (ShoppingList) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView id = convertView.findViewById(R.id.textViewId);
        TextView title = convertView.findViewById(R.id.shoppingListTitle_tv);
        TextView totalPrice = convertView.findViewById(R.id.shoppingListPrice);

        // Populate the data into the template view using the data object
        id.setText(shoppingList.getId());
        title.setText(shoppingList.getTitle());
        totalPrice.setText(String.valueOf(shoppingList.getTotalPrice()));

        // Return the completed view to render on screen
        return convertView;
    }
}
