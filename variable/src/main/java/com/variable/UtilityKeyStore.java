package com.variable;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import com.log.Logger;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

/**
 * Created by star on 2017/11/13.
 */

public class UtilityKeyStore {
    private static final String TAG = "KEYSTORE";
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";
    private static final String KEYSTORE_ALIAS = "KEYSTORE_ANDROID";
    private static final String _IV = "_IV";
    private static final String _AESKEY = "_AESKEY";

    private static KeyStore keyStore;
    private static UtilitySharedPreferences utilitySharedPreferences;

    private volatile static UtilityKeyStore u;

    public UtilityKeyStore (){}

    public static UtilityKeyStore getNewInstance(Context context) {
        if(u == null) synchronized (UtilityKeyStore.class) {
            if (u == null) {
                u = new UtilityKeyStore();
                init(context);
            }
        }
        return u;
    }

    private static void init(Context context){
        utilitySharedPreferences = UtilitySharedPreferences.getNewInstance(context);
        try {
            keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);
            keyStore.load(null);

            if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
                utilitySharedPreferences.save(_IV, "");
                genKeyStoreKey(context);
                genAESKey();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plainText) {
        try {
            return encryptAES(plainText);
        } catch (Exception e) {
            Logger.d(Log.getStackTraceString(e));
            return "";
        }
    }

    public String decrypt(String encryptedText) {
        try {
            return decryptAES(encryptedText);
        } catch (Exception e) {
            Logger.d(Log.getStackTraceString(e));
            return "";
        }

    }

    private static void genKeyStoreKey(Context context) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateRSAKey_AboveApi23();
        } else {
            generateRSAKey_BelowApi23(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void generateRSAKey_AboveApi23() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);

        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                .Builder(KEYSTORE_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build();

        keyPairGenerator.initialize(keyGenParameterSpec);
        keyPairGenerator.generateKeyPair();

    }

    private static void generateRSAKey_BelowApi23(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 30);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                .setAlias(KEYSTORE_ALIAS)
                .setSubject(new X500Principal("CN=" + KEYSTORE_ALIAS))
                .setSerialNumber(BigInteger.TEN)
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);

        keyPairGenerator.initialize(spec);
        keyPairGenerator.generateKeyPair();
    }

    private static String encryptRSA(byte[] plainText) throws Exception {
        PublicKey publicKey = keyStore.getCertificate(KEYSTORE_ALIAS).getPublicKey();

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedByte = cipher.doFinal(plainText);
        return Base64.encodeToString(encryptedByte, Base64.DEFAULT);
    }


    private byte[] decryptRSA(String encryptedText) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEYSTORE_ALIAS, null);

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return decryptedBytes;
    }

    private static void genAESKey() throws Exception {
        // Generate AES-Key
        byte[] aesKey = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(aesKey);

        // Generate 12 bytes iv then save to SharedPrefs
        byte[] generated = secureRandom.generateSeed(12);
        String iv = Base64.encodeToString(generated, Base64.DEFAULT);
        utilitySharedPreferences.save(_IV, iv);

        // Encrypt AES-Key with RSA Public Key then save to SharedPrefs
        String encryptAESKey = encryptRSA(aesKey);
        utilitySharedPreferences.save(_AESKEY, encryptAESKey);
    }


    /**
     * AES Encryption
     * @param plainText: A string which needs to be encrypted.
     * @return A base64's string after encrypting.
     */
    private String encryptAES(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getAESKEY(), new IvParameterSpec(getIV()));

        // 加密過後的byte
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // 將byte轉為base64的string編碼
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    private String decryptAES(String encryptedText) throws Exception {
        // 將加密過後的Base64編碼格式 解碼成 byte
        byte[] decodedBytes = Base64.decode(encryptedText.getBytes(), Base64.DEFAULT);

        // 將解碼過後的byte 使用AES解密
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.DECRYPT_MODE, getAESKEY(), new IvParameterSpec(getIV()));

        return new String(cipher.doFinal(decodedBytes));
    }

    private byte[] getIV() {
        String prefIV = utilitySharedPreferences.loadString(_IV, "");
        return Base64.decode(prefIV, Base64.DEFAULT);
    }

    private SecretKeySpec getAESKEY() throws Exception {
        String encryptedKey = utilitySharedPreferences.loadString(_AESKEY, "");
        byte[] aesKey = decryptRSA(encryptedKey);
        return new SecretKeySpec(aesKey, AES_MODE);
    }
}
