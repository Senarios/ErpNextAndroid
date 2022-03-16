package com.example.erpnext.utils;

import android.util.Base64;

import com.example.erpnext.R;
import com.example.erpnext.app.BaseClass;
import com.example.erpnext.app.MainApp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption extends BaseClass {


    public static final String K = MainApp.getAppContext().getString(R.string.key);
    public static final String S = MainApp.getAppContext().getString(R.string.salt);
    public static final byte[] IV = new byte[16];

    private final Builder mBuilder;

    private Encryption(Builder builder) {
        mBuilder = builder;
    }

    public static Encryption getDefault() {
        try {
            return Builder.getDefaultBuilder(K, S, IV).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encrypt(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (data == null) return null;
        SecretKey secretKey = getSecretKey(hashTheKey(mBuilder.getKey()));
        byte[] dataBytes = data.getBytes(mBuilder.getCharsetName());
        Cipher cipher = Cipher.getInstance(mBuilder.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, mBuilder.getIvParameterSpec(), mBuilder.getSecureRandom());
        return Base64.encodeToString(cipher.doFinal(dataBytes), mBuilder.getBase64Mode());
    }

    public String encryptOrNull(String data) {
        try {
            return encrypt(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void encryptAsync(final String data, final Callback callback) {
        if (callback == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String encrypt = encrypt(data);
                    if (encrypt == null) {
                        callback.onError(new Exception("null returned, it normally occurs when you send a null data"));
                    }
                    callback.onSuccess(encrypt);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public String decrypt(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (data == null) return null;
        byte[] dataBytes = Base64.decode(data, mBuilder.getBase64Mode());
        SecretKey secretKey = getSecretKey(hashTheKey(mBuilder.getKey()));
        Cipher cipher = Cipher.getInstance(mBuilder.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, mBuilder.getIvParameterSpec(), mBuilder.getSecureRandom());
        byte[] dataBytesDecrypted = (cipher.doFinal(dataBytes));
        return new String(dataBytesDecrypted);
    }

    public String decryptOrNull(String data) {
        try {
            return decrypt(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void decryptAsync(final String data, final Callback callback) {
        if (callback == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String decrypt = decrypt(data);
                    if (decrypt == null) {
                        callback.onError(new Exception("null returned, it normally occurs when you send a null data"));
                    }
                    callback.onSuccess(decrypt);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    private SecretKey getSecretKey(char[] key) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(mBuilder.getSecretKeyType());
        KeySpec spec = new PBEKeySpec(key, mBuilder.getSalt().getBytes(mBuilder.getCharsetName()), mBuilder.getIterationCount(), mBuilder.getKeyLength());
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), mBuilder.getKeyAlgorithm());
    }

    private char[] hashTheKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(mBuilder.getDigestAlgorithm());
        messageDigest.update(key.getBytes(mBuilder.getCharsetName()));
        return Base64.encodeToString(messageDigest.digest(), Base64.NO_PADDING).toCharArray();
    }

    public interface Callback {

        void onSuccess(String result);

        void onError(Exception exception);

    }

    public static class Builder {

        private byte[] mIv;
        private int mKeyLength;
        private int mBase64Mode;
        private int mIterationCount;
        private String mSalt;
        private String mKey;
        private String mAlgorithm;
        private String mKeyAlgorithm;
        private String mCharsetName;
        private String mSecretKeyType;
        private String mDigestAlgorithm;
        private String mSecureRandomAlgorithm;
        private SecureRandom mSecureRandom;
        private IvParameterSpec mIvParameterSpec;

        public static Builder getDefaultBuilder(String key, String salt, byte[] iv) {
            return new Builder()
                    .setIv(iv)
                    .setKey(key)
                    .setSalt(salt)
                    .setKeyLength(128)
                    .setKeyAlgorithm("AES")
                    .setCharsetName("UTF8")
                    .setIterationCount(1)
                    .setDigestAlgorithm("SHA1")
                    .setBase64Mode(Base64.DEFAULT)
                    .setAlgorithm("AES/CBC/PKCS5Padding")
                    .setSecureRandomAlgorithm("SHA1PRNG")
                    .setSecretKeyType("PBKDF2WithHmacSHA1");
        }

        public Encryption build() throws NoSuchAlgorithmException {
            setSecureRandom(SecureRandom.getInstance(getSecureRandomAlgorithm()));
            setIvParameterSpec(new IvParameterSpec(getIv()));
            return new Encryption(this);
        }

        private String getCharsetName() {
            return mCharsetName;
        }

        public Builder setCharsetName(String charsetName) {
            mCharsetName = charsetName;
            return this;
        }

        private String getAlgorithm() {
            return mAlgorithm;
        }

        public Builder setAlgorithm(String algorithm) {
            mAlgorithm = algorithm;
            return this;
        }

        private String getKeyAlgorithm() {
            return mKeyAlgorithm;
        }

        public Builder setKeyAlgorithm(String keyAlgorithm) {
            mKeyAlgorithm = keyAlgorithm;
            return this;
        }

        private int getBase64Mode() {
            return mBase64Mode;
        }

        public Builder setBase64Mode(int base64Mode) {
            mBase64Mode = base64Mode;
            return this;
        }

        private String getSecretKeyType() {
            return mSecretKeyType;
        }

        public Builder setSecretKeyType(String secretKeyType) {
            mSecretKeyType = secretKeyType;
            return this;
        }

        private String getSalt() {
            return mSalt;
        }

        public Builder setSalt(String salt) {
            mSalt = salt;
            return this;
        }

        private String getKey() {
            return mKey;
        }

        public Builder setKey(String key) {
            mKey = key;
            return this;
        }

        private int getKeyLength() {
            return mKeyLength;
        }

        public Builder setKeyLength(int keyLength) {
            mKeyLength = keyLength;
            return this;
        }

        private int getIterationCount() {
            return mIterationCount;
        }

        public Builder setIterationCount(int iterationCount) {
            mIterationCount = iterationCount;
            return this;
        }

        private String getSecureRandomAlgorithm() {
            return mSecureRandomAlgorithm;
        }

        public Builder setSecureRandomAlgorithm(String secureRandomAlgorithm) {
            mSecureRandomAlgorithm = secureRandomAlgorithm;
            return this;
        }

        private byte[] getIv() {
            return mIv;
        }

        public Builder setIv(byte[] iv) {
            mIv = iv;
            return this;
        }

        private SecureRandom getSecureRandom() {
            return mSecureRandom;
        }

        public Builder setSecureRandom(SecureRandom secureRandom) {
            mSecureRandom = secureRandom;
            return this;
        }

        private IvParameterSpec getIvParameterSpec() {
            return mIvParameterSpec;
        }

        public Builder setIvParameterSpec(IvParameterSpec ivParameterSpec) {
            mIvParameterSpec = ivParameterSpec;
            return this;
        }

        private String getDigestAlgorithm() {
            return mDigestAlgorithm;
        }

        public Builder setDigestAlgorithm(String digestAlgorithm) {
            mDigestAlgorithm = digestAlgorithm;
            return this;
        }

    }

}
