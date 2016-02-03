package com.xmagicj.android.bizunittestdemo;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 通过AES加解密</p>
 * Usage:
 * <pre>
 * String encryptedText = SICrypto.encryptAES(clearText);
 * ...
 * String clearText = SICrypto.decryptAES(encryptedText);
 * </pre>
 * Created by mumu on 15/2/6.
 */
public class Crypto {

    public static String encryptAES(String clearText) throws Exception {
        return encrypt(getKEY(), clearText).trim();
    }

    public static String decryptAES(String encryptedText) throws Exception {
        return decrypt(getKEY(), encryptedText).trim();
    }


    private static String encrypt(String seed, String clearText) throws Exception {
        // byte[] rawKey = getRawKey(seed.getBytes());
        byte[] rawKey = seed.getBytes("utf-8");
        byte[] result = encrypt(rawKey, clearText.getBytes("utf-8"));
        return Base64.encodeToString(result, Base64.DEFAULT);
    }

    private static String decrypt(String seed, String encryptedText) throws Exception {
        // byte[] rawKey = getRawKey(seed.getBytes());
        byte[] rawKey = seed.getBytes("utf-8");
        //byte[] enc = toByte(encryptedText);
        //byte[] enc = toByte(new String(Base64.decode(encryptedText, Base64.DEFAULT), "UTF-8"));
        byte[] enc = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, "UTF-8");
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }


    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("utf-8"));
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("utf-8"));
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    private static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf) {
            appendHex(result, aBuf);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";
    /**
     * KEY
     */
    private static String KEY = "0123456789abcdef";
    /**
     * initialization vector
     * 使用CBC模式，需要一个初始向量iv，可增加加密算法的强度
     */
    private static String IV = "0123456789abcdef";
    /**
     * AES/CBC/PKCS7Padding
     */
    private final static String TRANSFORMATION = "AES/CBC/PKCS7Padding";

    /**
     * @return
     */
    public static String getKEY() {
        return KEY;
    }

    /**
     * @param KEY
     */
    public static void setKEY(String KEY) {
        Crypto.KEY = KEY;
    }

    /**
     * @return
     */
    public static String getIV() {
        return IV;
    }

    /**
     * @param IV
     */
    public static void setIV(String IV) {
        Crypto.IV = IV;
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
