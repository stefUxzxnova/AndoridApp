package com.example.happyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happyapp.localDatabase.DatabaseHelper;
import com.example.happyapp.localDatabase.ShoppingList;

public class CreateShoppingList extends AppCompatActivity {

    private EditText editTextTitle;
    private Button createShoppingListBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shopping_list);
        createShoppingListBtn = findViewById(R.id.createShoppingListBtn);
        editTextTitle = findViewById(R.id.editTextTitle);


        createShoppingListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertShoppingListInLocalDatabase();
                Intent intent = new Intent(CreateShoppingList.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
        private void insertShoppingListInLocalDatabase() {
        try(DatabaseHelper dbh = new DatabaseHelper(getApplicationContext())) {
            dbh.insertShoppingList(new ShoppingList(
                    editTextTitle.getText().toString(),
                    0.0
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