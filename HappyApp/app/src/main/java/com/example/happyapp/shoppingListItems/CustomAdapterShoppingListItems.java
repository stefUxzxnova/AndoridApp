package com.example.happyapp.shoppingListItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.happyapp.R;
import com.example.happyapp.localDatabase.ShoppingList;
import com.example.happyapp.localDatabase.ShoppingListItem;

import java.util.List;

public class CustomAdapterShoppingListItems extends BaseAdapter {

    private Context context;
    private List<ShoppingListItem> shoppingListItems; // Replace User with your data model

    public CustomAdapterShoppingListItems(Context context, List<ShoppingListItem> shoppingListItems) {
        this.context = context;
        this.shoppingListItems = shoppingListItems;
    }
    @Override
    public int getCount() {
        return shoppingListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ShoppingListItem shoppingListItem = (ShoppingListItem) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopping_list_item, parent, false);
        }

        // Lookup view for data population
        TextView id = convertView.findViewById(R.id.textViewID_tv);
        TextView name = convertView.findViewById(R.id.textViewName_tvv);
        TextView quantity = convertView.findViewById(R.id.textViewQuantity_tvv);
        TextView price = convertView.findViewById(R.id.textViewPrice_tvv);
        TextView totalPrice = convertView.findViewById(R.id.textViewTotalP_tvv);


        // Populate the data into the template view using the data object
        id.setText(String.valueOf(shoppingListItem.getItemId()));
        name.setText(shoppingListItem.getName());
        quantity.setText(String.valueOf(shoppingListItem.getQuantity()));
        price.setText(String.valueOf(shoppingListItem.getPrice()));
        totalPrice.setText(String.valueOf(shoppingListItem.getPrice() * shoppingListItem.getQuantity()));

        // Return the completed view to render on screen
        return convertView;
    }
}
