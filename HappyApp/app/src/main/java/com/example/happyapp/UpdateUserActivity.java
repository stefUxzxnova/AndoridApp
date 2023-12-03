package com.example.happyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyapp.ui.profile.LoggedUser;
import com.example.happyapp.ui.profile.ProfileFragment;
import com.example.happyapp.usersRestApi.profile.Message;
import com.example.happyapp.usersRestApi.profile.User;
import com.example.happyapp.usersRestApi.profile.UsersApi;
import com.example.happyapp.validation.Validator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateUserActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private TextView isUpdateSuccessful;
    private Button update_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        username = findViewById(R.id.editTextUsername);
        isUpdateSuccessful = findViewById(R.id.isUpdateSuccessful);
        update_btn = findViewById(R.id.btnUpdate);
        User loggedUser = (User) getIntent().getSerializableExtra("USER_EXTRA");

        firstName.setText(loggedUser.getFirstName());
        lastName.setText(loggedUser.getLastName());
        username.setText(loggedUser.getUsername());

        update_btn.setOnClickListener(view -> {
            if (Validator.isValidUsername(username.getText().toString())) {
                updateUser(new User(
                        LoggedUser.getLoggedUserId(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        username.getText().toString()));
            } else {
                Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void updateUser(User user) {
        Thread t = new Thread(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.7:8089/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsersApi usersApi = retrofit.create(UsersApi.class);
                Call<Message> call = usersApi.updateUser(user);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (!response.isSuccessful()) {
                            isUpdateSuccessful.setText("Code" + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        isUpdateSuccessful.setText(t.getMessage());
                    }
                });
                Intent intent = new Intent(UpdateUserActivity.this, ProfileFragment.class);
                startActivity(intent);
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(UpdateUserActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            }
        });
        t.start();
    }
}