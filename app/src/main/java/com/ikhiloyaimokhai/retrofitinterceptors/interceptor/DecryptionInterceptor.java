package com.ikhiloyaimokhai.retrofitinterceptors.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ikhiloyaimokhai.retrofitinterceptors.crypto.CryptoStrategy;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class DecryptionInterceptor implements Interceptor {

    private final CryptoStrategy mDecryptionStrategy;

    public DecryptionInterceptor(CryptoStrategy mDecryptionStrategy) {
        this.mDecryptionStrategy = mDecryptionStrategy;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.isSuccessful()) {
            Response.Builder newResponse = response.newBuilder();
            String contentType = response.header("Content-Type");
            if (TextUtils.isEmpty(contentType)) contentType = "application/json";
//            InputStream cryptedStream = response.body().byteStream();
            String responseStr = response.body().string();


            String decryptedString = null;
            if (mDecryptionStrategy != null) {
                try {
                    decryptedString = mDecryptionStrategy.decrypt(responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Timber.i("Response string => %s", responseStr);
                Timber.i("Decrypted BODY=> %s", decryptedString);
            } else {
                throw new IllegalArgumentException("No decryption strategy!");
            }
            newResponse.body(ResponseBody.create(MediaType.parse(contentType), decryptedString));
            return newResponse.build();
        }
        return response;
    }
}
