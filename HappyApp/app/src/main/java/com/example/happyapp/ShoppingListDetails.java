package com.example.happyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyapp.localDatabase.DatabaseHelper;
import com.example.happyapp.localDatabase.ShoppingList;
import com.example.happyapp.localDatabase.ShoppingListItem;
import com.example.happyapp.shoppingListItems.CustomAdapterShoppingListItems;
import com.example.happyapp.ui.home.CustomListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetails extends AppCompatActivity {
    private TextView dataNotFound_tv;
    private TextView title;
    private TextView totalPrice;
    private Button deleteShoppingList_btn;
    private Button addShoppingItem;

    private ListView listView;
    private List<ShoppingListItem> shoppingListItems;
    private CustomAdapterShoppingListItems adapter;

    private ShoppingList shoppingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_details);

        title = findViewById(R.id.textViewTitle);
        totalPrice = findViewById(R.id.textViewShoppingPrice);
        addShoppingItem = findViewById(R.id.addShoppingListItem_btn);
        listView = findViewById(R.id.listViewShoppingListItems);

        //get data for chosen shoppingList from the list view
        Bundle b = getIntent().getExtras();
        if (b == null) {
            dataNotFound_tv.setText("Data not found");
        }
        shoppingList = new ShoppingList(
                b.getString("ID"),
                b.getString("TITLE"),
                Double.parseDouble(b.getString("TOTALPRICE"))
        );

        title.setText(shoppingList.getTitle());

        // Populate userList with your data (e.g., from a database)
        shoppingListItems = fillListView();
        // Set up the adapter
        adapter = new CustomAdapterShoppingListItems(getApplicationContext(), shoppingListItems);
        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        //pass the id of the shoppinglist
        addShoppingItem.setOnClickListener(v -> {
            Bundle b1 = new Bundle();
            b1.putString("ID", shoppingList.getId());

            Intent intent = new Intent(ShoppingListDetails.this, CreateShoppingListItem.class);
            intent.putExtras(b1);

            startActivityForResult(intent, 200, b1);
        });

        deleteShoppingList_btn = findViewById(R.id.btnDeleteShoppingList);
        deleteShoppingList_btn.setOnClickListener(v -> {
            deleteShoppingList();
            Intent intent = new Intent(ShoppingListDetails.this, MainActivity.class);
            startActivity(intent);
        });

        updateShoppingListTotalPrice();
        totalPrice.setText(String.valueOf(shoppingList.getTotalPrice()));
    }

    private void updateShoppingListTotalPrice() {
        double totalPrice = 0;
        for (ShoppingListItem item : shoppingListItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
            db.updateShoppingList(
                    Integer.parseInt(shoppingList.getId()), totalPrice);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        // TODO: 27.11.2023 г.
    }

    private void deleteShoppingList() {
        try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
            db.deleteShoppingList(Integer.parseInt(shoppingList.getId()));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private List<ShoppingListItem> fillListView() {
        try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
            List<ShoppingListItem> shoppingListItems = db.selectShoppingListItemsByShoppingListId(shoppingList.getId());
            return shoppingListItems;
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return new ArrayList<>();
    }
}