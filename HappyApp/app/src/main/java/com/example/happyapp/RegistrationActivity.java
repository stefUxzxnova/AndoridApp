package com.example.happyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyapp.localDatabase.DatabaseHelper;
import com.example.happyapp.ui.profile.LoggedUser;
import com.example.happyapp.usersRestApi.profile.User;
import com.example.happyapp.usersRestApi.profile.UsersApi;
import com.example.happyapp.validation.Validator;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstName_et;
    private EditText lastName_et;
    private EditText username_et;
    private EditText password_et;
    private TextView isRegistrationSuccessful;
    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        databaseHelper = new DatabaseHelper(getApplicationContext());
//        userRepo = new UserRepo(databaseHelper);

        firstName_et = findViewById(R.id.editTextFirstName);
        lastName_et = findViewById(R.id.editTextLastName);
        username_et = findViewById(R.id.editTextUsername);
        password_et = findViewById(R.id.editTextPassword);
        register_btn = findViewById(R.id.buttonRegister);
        isRegistrationSuccessful = findViewById(R.id.isRegistrationSuccessful);

        register_btn.setOnClickListener(view -> {

            if (Validator.isValidUsername(username_et.getText().toString()) && Validator.isValidPassword(password_et.getText().toString())) {
                // Both username and password are valid, proceed with user creation
                createNewUser(new User(
                        firstName_et.getText().toString(),
                        lastName_et.getText().toString(),
                        username_et.getText().toString(),
                        password_et.getText().toString()
                ));
            } else {
                // Display an error message or handle invalid input
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createNewUser(User user) {
        Thread t = new Thread(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.7:8089/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsersApi usersApi = retrofit.create(UsersApi.class);
                Call<User> call = usersApi.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            isRegistrationSuccessful.setText("Code" + response.code());
                            return;
                        }
                        User loggedUser = response.body();
                        LoggedUser.setLoggedUserId(Long.parseLong(loggedUser.getId()));
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Ивайла топ си мойка", Toast.LENGTH_LONG).show());
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<com.example.happyapp.usersRestApi.profile.User> call, Throwable t) {
                        isRegistrationSuccessful.setText(t.getMessage());
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Не стана Ивайла", Toast.LENGTH_LONG).show());
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Много съм зле брат", Toast.LENGTH_LONG).show());

            }
        });
        t.start();
    }

}