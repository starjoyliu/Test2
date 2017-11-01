package com.variable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityBase64 {
    public static final String CHARSETS = "UTF-8";
    private final int DEFAULT_COMPRESS_RATIO = 50;

    private volatile static UtilityBase64 u;
    public UtilityBase64(){}

    public static UtilityBase64 getNewInstance() {
        if(u == null) {
            synchronized (UtilityBase64.class) {
                if(u == null) {
                    u = new UtilityBase64();
                }
            }
        }
        return u;
    }

    /**
     * Bitmap to Base64
     * {@value DEFAULT_COMPRESS_RATIO}
     * @param bitmap
     * @return
     */
    public String bitmapToBase64(Bitmap bitmap){
        return bitmapToBase64(bitmap, DEFAULT_COMPRESS_RATIO);
    }

    /**
     * Bitmap to Base64
     * @param bitmap
     * @param compressRatio 30表示壓縮70%, 70表示壓縮30%, 100為不壓縮, 預設{@value DEFAULT_COMPRESS_RATIO}
     * @return
     */
    public String bitmapToBase64(Bitmap bitmap, int compressRatio){
        if(bitmap==null){
            return null;
        }
        ByteArrayOutputStream b = null;
        byte[] byteArray = null;
        try {
            b = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressRatio, b);
            byteArray = b.toByteArray();
        }finally {
            try {
                if(b != null){
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bitmap!=null){
                bitmap.recycle();
            }
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    /**
     * Base64 to Bitmap
     * @param base64
     * @return
     */
    public Bitmap base64ToBitmap(String base64){
        return base64ToBitmap(base64, DEFAULT_COMPRESS_RATIO);
    }

    /**
     * Base64 to Bitmap
     * @param base64
     * @param compressRatio 30表示壓縮70%, 70表示壓縮30%, 100為不壓縮, 預設{@value DEFAULT_COMPRESS_RATIO}
     * @return
     */
    public Bitmap base64ToBitmap(String base64, int compressRatio){
        if(base64==null || base64.length()==0){
            return null;
        }
        byte[] decodedByte = Base64.decode(base64.getBytes(), Base64.NO_WRAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        ByteArrayOutputStream b = null;
        try {
            b = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressRatio, b);
        }finally {
            try {
                if(b != null){
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public byte[] base64ToByteArray(String base64){
        if(base64==null || base64.length()==0){
            return null;
        }
        return Base64.decode(base64.getBytes(), Base64.NO_WRAP);
    }

    /**
     * Base64編碼
     * @param p
     * @return
     */
    public String base64Encode(String p){
        if(p==null){
            return null;
        }
        byte[] data = null;
        try {
            data = p.getBytes(CHARSETS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data==null?null:Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * 解碼Base64
     * @param base64
     * @return
     */
    public String base64Decode(String base64){
        if(base64==null){
            return null;
        }
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        try {
            return new String(data, CHARSETS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
