package com.example.happyapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happyapp.LoginActivity;
import com.example.happyapp.MainActivity;
import com.example.happyapp.R;
import com.example.happyapp.UpdateUserActivity;
import com.example.happyapp.databinding.FragmentProfileBinding;
import com.example.happyapp.usersRestApi.profile.Message;
import com.example.happyapp.usersRestApi.profile.User;
import com.example.happyapp.usersRestApi.profile.UsersApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private TextView firstName_tv;
    private TextView lastName_tv;
    private TextView username_tv;
    private TextView createdOn_tv;
    private TextView getLoggedUser_tv;

    private User loggedUser;
    private Button update_btn;
    private Button delete_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firstName_tv = root.findViewById(R.id.textViewFirstName);
        lastName_tv = root.findViewById(R.id.textViewLastName);
        username_tv = root.findViewById(R.id.textViewUsername);
        createdOn_tv = root.findViewById(R.id.textViewCreatedOn);
        getLoggedUser_tv = root.findViewById(R.id.textViewGetLoggedUser);

        update_btn = root.findViewById(R.id.btnUpdate);
        delete_btn = root.findViewById(R.id.btnDelete);

        update_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateUserActivity.class);
            intent.putExtra("USER_EXTRA", loggedUser);
            startActivity(intent);
        });
        delete_btn.setOnClickListener(v -> {
            deleteUser(LoggedUser.getLoggedUserId());
        });
        getLoggedUser(LoggedUser.getLoggedUserId());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void deleteUser(Long id) {
        Thread t = new Thread(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.100.5:8089/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsersApi usersApi = retrofit.create(UsersApi.class);
                Call<Message> call = usersApi.deleteUserById(id);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Response Body", response.raw().toString());
                            LoggedUser.setLoggedUserId(0);
                            requireActivity().runOnUiThread(() -> {
                                    Toast.makeText(requireContext(), "Успешно изтриване", Toast.LENGTH_LONG).show();
                            });
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        LoggedUser.setLoggedUserId(0);
                        requireActivity().runOnUiThread(
                                () -> {
                                    Toast.makeText(requireContext(), "изтриване", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                        );
                    }
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(
                        () -> {
                            Toast.makeText(requireContext(), "изтриване", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                );
            }
        });
        t.start();
    }

    private void getLoggedUser(Long id) {
        Thread t = new Thread(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.7:8089/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsersApi usersApi = retrofit.create(UsersApi.class);
                Call<User> call = usersApi.getUserById(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            getLoggedUser_tv.setText("Code" + response.code());
                            return;
                        }
                        loggedUser = response.body();
                        firstName_tv.append(" " + loggedUser.getFirstName());
                        lastName_tv.append(" " + loggedUser.getLastName());
                        username_tv.append(" " + loggedUser.getUsername());
                        createdOn_tv.append(" " + loggedUser.getCreatedOn());
                        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Ивайла топ си мойка", Toast.LENGTH_LONG).show());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        getLoggedUser_tv.setText(t.getMessage());
                        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Не стана Ивайла", Toast.LENGTH_LONG).show());
                    }
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Много съм зле брат", Toast.LENGTH_LONG).show());

            }
        });
        t.start();
    }
}