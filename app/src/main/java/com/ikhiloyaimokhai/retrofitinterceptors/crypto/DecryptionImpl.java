package com.ikhiloyaimokhai.retrofitinterceptors.crypto;

public class DecryptionImpl implements CryptoStrategy {
    @Override
    public String encrypt(String body) {
        return null;
    }

    @Override
    public String decrypt(String data) throws Exception {
        return CryptoUtil.decrypt(data);
    }


}
