package com.example.bookstore;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("BookStores")
    Call<DataModal> createPost(@Body DataModal dataModal);

}
