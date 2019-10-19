package com.ikhiloyaimokhai.retrofitinterceptors.service;


import com.ikhiloyaimokhai.retrofitinterceptors.model.Book;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by Ikhiloya Imokhai on 2019-10-19.
 */
public class MockService implements ApiService {
    final BehaviorDelegate<ApiService> delegate;


    MockService(BehaviorDelegate<ApiService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<Book> saveBook(Book book) {
        Book newBook = new Book("There was a Country", "Chinua Achebe", "Memoir");

        return delegate
                .returningResponse(newBook).saveBook(book);
    }
}

