package com.example.happyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happyapp.localDatabase.DatabaseHelper;
import com.example.happyapp.localDatabase.ShoppingListItem;

public class CreateShoppingListItem extends AppCompatActivity {

    private EditText shoppingItemName;
    private EditText shoppingItemQuantity;
    private EditText shoppingItemPrice;
    private Button createShoppingListItem;
    private String shoppingListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shopping_list_item);

        shoppingItemName = findViewById(R.id.editTextShoppingListItemName);
        shoppingItemQuantity = findViewById(R.id.editTextShoppingListItemQuantity);
        shoppingItemPrice = findViewById(R.id.editTextShoppingListItemPrice);
        createShoppingListItem = findViewById(R.id.btnCreateShoppingListItem);

        Bundle b = getIntent().getExtras();
        shoppingListId = b.getString("ID");


        createShoppingListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShoppingListItem();
                //Intent intent = new Intent(CreateShoppingListItem.this, )
                finish();
            }
        });


    }
    private void createShoppingListItem(){
        try(DatabaseHelper dbh = new DatabaseHelper(getApplicationContext())) {
            dbh.insertShoppingListItem(new ShoppingListItem(
                    shoppingItemName.getText().toString(),
                    Integer.parseInt(shoppingItemQuantity.getText().toString()),
                    Double.parseDouble(shoppingItemPrice.getText().toString()),
                    Integer.parseInt(shoppingListId)
            ));
            Toast.makeText(getApplicationContext(),
                            "Successfully added",
                            Toast.LENGTH_LONG)
                    .show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                            e.getLocalizedMessage(),
                            Toast.LENGTH_LONG)
                    .show();
        }
    }

}