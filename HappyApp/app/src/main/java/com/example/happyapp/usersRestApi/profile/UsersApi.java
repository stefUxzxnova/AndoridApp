package com.example.happyapp.usersRestApi.profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersApi {
    @GET("api/persons")
    Call<List<User>> getUsers();

    @GET("api/persons/{id}")
    Call<User> getUserById(@Path("id") long id);

    @POST("api/persons/create")
    Call<User> createUser(@Body User user);

    @PUT("api/persons/update")
    Call<Message> updateUser(@Body User user);

    @DELETE("api/persons/delete/{id}")
    Call<Message> deleteUserById(@Path("id") long id);

    @POST("api/login")
    Call<User> getUserByUsernameAndPassword(@Body UserCredentials userCredentials);

}
