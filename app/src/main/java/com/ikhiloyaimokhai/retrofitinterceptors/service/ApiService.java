package com.ikhiloyaimokhai.retrofitinterceptors.service;

import com.ikhiloyaimokhai.retrofitinterceptors.model.Book;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ikhiloya Imokhai on 2019-10-19.
 */

public interface ApiService {

    @POST("book")
    Call<Book> saveBook(@Body Book book);
}
