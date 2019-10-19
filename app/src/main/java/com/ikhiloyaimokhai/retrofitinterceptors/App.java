package com.ikhiloyaimokhai.retrofitinterceptors;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ikhiloyaimokhai.retrofitinterceptors.crypto.DecryptionImpl;
import com.ikhiloyaimokhai.retrofitinterceptors.crypto.EncryptionImpl;
import com.ikhiloyaimokhai.retrofitinterceptors.interceptor.DecryptionInterceptor;
import com.ikhiloyaimokhai.retrofitinterceptors.interceptor.EncryptionInterceptor;
import com.ikhiloyaimokhai.retrofitinterceptors.service.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
//    private static final String BASE_URL = "https://dfe508a1-3a22-4025-9d39-c5f40582ea41.mock.pstmn.io/";
    private static final String BASE_URL = "https://66d252d7-61d1-44e0-be70-1f77477ac86c.mock.pstmn.io";

    private static App INSTANCE;


    private ApiService apiService;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        //Gson Builder
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Timber.plant(new Timber.DebugTree());

        // HttpLoggingInterceptor
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        /**
         * injection of interceptors to handle encryption and decryption
         */

        //Encryption Interceptor
        EncryptionInterceptor encryptionInterceptor = new EncryptionInterceptor(new EncryptionImpl());
        //Decryption Interceptor
        DecryptionInterceptor decryptionInterceptor = new DecryptionInterceptor(new DecryptionImpl());


        // OkHttpClient. Be conscious with the order
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(encryptionInterceptor)
                .addInterceptor(decryptionInterceptor)
                .build();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //ApiService
        apiService = retrofit.create(ApiService.class);


        /**
         * RETROFIT MOCK WEB SERVER
         NetworkBehavior behavior = NetworkBehavior.create();
         behavior.setDelay(2, TimeUnit.SECONDS);
         behavior.setVariancePercent(40);
         behavior.setFailurePercent(2);


         MockRetrofit mock = new MockRetrofit.Builder(retrofit)
         .networkBehavior(behavior)
         .build();
         BehaviorDelegate<ApiService> delegate =
         mock.create(ApiService.class);

         apiService = new MockService(delegate); **/
    }


    public ApiService getBookService() {
        return apiService;
    }


}
