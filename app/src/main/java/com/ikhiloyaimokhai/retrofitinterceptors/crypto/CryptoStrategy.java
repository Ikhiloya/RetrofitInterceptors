package com.ikhiloyaimokhai.retrofitinterceptors.crypto;


public interface CryptoStrategy {
    String encrypt(String body) throws Exception;

    String decrypt(String data) throws Exception;

}
