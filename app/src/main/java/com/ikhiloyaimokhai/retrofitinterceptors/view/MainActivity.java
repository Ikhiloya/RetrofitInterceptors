package com.ikhiloyaimokhai.retrofitinterceptors.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.ikhiloyaimokhai.retrofitinterceptors.App;
import com.ikhiloyaimokhai.retrofitinterceptors.R;
import com.ikhiloyaimokhai.retrofitinterceptors.crypto.CryptoUtil;
import com.ikhiloyaimokhai.retrofitinterceptors.service.ApiService;
import com.ikhiloyaimokhai.retrofitinterceptors.model.Book;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.ikhiloyaimokhai.retrofitinterceptors.crypto.CryptoUtil.encrypt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = findViewById(R.id.saveBook);


        ApiService bookService = App.get().getBookService();
        Book book = new Book("There was a Country", "Chinua Achebe", "Memoir");


        saveButton.setOnClickListener(view ->
                bookService.saveBook(book).
                        enqueue(new Callback<Book>() {
                            @Override
                            public void onResponse(Call<Book> call, Response<Book> response) {
                                Timber.i(response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<Book> call, Throwable t) {

                            }
                        }));

    }
}
