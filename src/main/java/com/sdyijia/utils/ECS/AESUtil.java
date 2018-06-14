package com.sdyijia.utils.ECS;

import com.sdyijia.utils.tool.ToolStr;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 编码工具类
 * 1.AES加密
 * 2.AES加密为base 64 code
 * 3.AES解密
 * 4.将base 64 code AES解密
 */
public class AESUtil {
    
    /** 默认AES加密密钥 */
    private static final String KEY = "AsD`@0,o30[$f.->42";
    
//    public static void main(String[] args) throws Exception {
//        String content = "我爱你";
//        System.out.println("加密前：" + content);
//
//        String key = "123456";
//        System.out.println("加密密钥和解密密钥：" + key);
//        
//        String encrypt = aesEncrypt(content, key);
//        System.out.println("加密后：" + encrypt);
//        
//        String decrypt = aesDecrypt(encrypt, key);
//        System.out.println("解密后：" + decrypt);
//    }
    
    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return new BASE64Encoder().encode(bytes);
    }
    
    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return ToolStr.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }
    
    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        
        return cipher.doFinal(content.getBytes("utf-8"));
    }
    
    /**
     * AES加密
     * @param content 待加密的内容
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(KEY.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        
        return cipher.doFinal(content.getBytes("utf-8"));
    }
    
    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncryptEx(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }
    
    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     */
    public static String aesEncrypt(String content, String encryptKey) {
        try {
            return base64Encode(aesEncryptToBytes(content, encryptKey));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncryptEx(String content) throws Exception {
        return base64Encode(aesEncryptToBytes(content, KEY));
    }
    
    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @return 加密后的base 64 code
     */
    public static String aesEncrypt(String content) {
        try {
            return base64Encode(aesEncryptToBytes(content, KEY));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        
        return new String(decryptBytes);
    }
    
    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(KEY.getBytes()));
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        
        return new String(decryptBytes);
    }
    
    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecryptEx(String encryptStr, String decryptKey) throws Exception {
        return ToolStr.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }
    
    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) {
        try {
            return ToolStr.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecryptEx(String encryptStr) throws Exception {
        return ToolStr.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), KEY);
    }
    
    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string
     */
    public static String aesDecrypt(String encryptStr) {
        try {
            return ToolStr.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
}
