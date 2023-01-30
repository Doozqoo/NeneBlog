package com.nene.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class IdsUtils {

    private static final String KEY_AES = "AES";
    private static final String KEY_SECRET = "12345678901234561234567890123456";

    public static String encryptNumber(Long number) throws Exception {
        String src = String.format("%d%013d", 0, number);
        return encrypt(src);
    }

    public static Long decryptLong(String src) throws Exception {
        String val = decrypt(src);
        return Long.valueOf(val);
    }

    public static Integer decryptInt(String src) throws Exception {
        String val = decrypt(src);
        return Integer.valueOf(val);
    }

    private static String encrypt(String src) throws Exception {
        byte[] raw = KEY_SECRET.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byte2hex(encrypted);
    }

    private static String decrypt(String src) throws Exception {
        byte[] raw = KEY_SECRET.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encrypted1 = hex2byte(src);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    private static byte[] hex2byte(String strHex) {
        if (strHex == null) {
            return null;
        }
        int l = strHex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strHex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("========:" + encryptNumber(2l));
    }

}