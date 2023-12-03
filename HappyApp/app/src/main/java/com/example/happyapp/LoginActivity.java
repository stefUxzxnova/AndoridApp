package com.example.happyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyapp.ui.profile.LoggedUser;
import com.example.happyapp.usersRestApi.profile.UserCredentials;
import com.example.happyapp.usersRestApi.profile.UsersApi;
import com.example.happyapp.usersRestApi.profile.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText username_et;
    private EditText password_et;
    private Button login_btn;
    private Button register_btn;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LoggedUser.getLoggedUserId() != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
        username_et = findViewById(R.id.editTextUsername);
        password_et = findViewById(R.id.editTextPassword);
        login_btn = findViewById(R.id.buttonLogin);
        register_btn = findViewById(R.id.buttonRegister);

        login_btn.setOnClickListener(view -> {
            getLoggedUser(username_et.getText().toString(), password_et.getText().toString());
        });
        register_btn.setOnClickListener(view -> {
            // Create an Intent to start the new activity
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);

        });

        textView = findViewById(R.id.textViewRegister1);

    }

    private void getLoggedUser(String username, String password){
        Thread t = new Thread(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.7:8089/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsersApi usersApi = retrofit.create(UsersApi.class);
                Call<User> call = usersApi.getUserByUsernameAndPassword(new UserCredentials(username, password));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            textView.setText("Unsuccessful login. Try again!");
                            return;
                        }
                        User loggedUser = response.body();
                        LoggedUser.setLoggedUserId(Long.parseLong(loggedUser.getId()));
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Ивайла топ си мойка", Toast.LENGTH_LONG).show());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        textView.setText(t.getMessage());
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