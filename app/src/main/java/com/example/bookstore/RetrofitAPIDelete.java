package com.example.bookstore;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitAPIDelete {
    @DELETE("BookStores/")
    Call<DataModal> deleteData(@Query("id")int id);
}
