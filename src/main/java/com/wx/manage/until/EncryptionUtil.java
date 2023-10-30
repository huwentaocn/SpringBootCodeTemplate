package com.wx.manage.until;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Description 加解密工具类
 * @Date 2023/9/2 14:56 星期六
 * @Author Hu Wentao
 */
public class EncryptionUtil {

    /**
     * 对称加密(DES) (不建议）
     *
     * @param plaintext 明文字符串
     * @param secretKey 密钥 (DES加密算法,key的大小必须是8个字节)
     * @return
     */
    public static String encryptWithDES(String plaintext, String secretKey) throws Exception {

        Cipher cipher = Cipher.getInstance("DES");

        // 指定秘钥规则
        // 第一个参数表示：密钥，key的字节数组
        // 第二个参数表示：算法
        SecretKeySpec sks = new SecretKeySpec(secretKey.getBytes(), "DES");

        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE, sks);

        // 进行加密
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 对称解密(DES) (不建议）
     *
     * @param ciphertext 明文字符串
     * @param secretKey  密钥 (DES加密算法,key的大小必须是8个字节)
     * @return
     * @throws Exception
     */
    public static String decryptWithDES(String ciphertext, String secretKey) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance("DES");
        // 指定密钥规则
        SecretKeySpec sks = new SecretKeySpec(secretKey.getBytes(), "DES");

        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.DECRYPT_MODE, sks);

        //  解密，上面使用的base64编码，下面直接用密文
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 对称加密(AES) (建议）
     *
     * @param plaintext 明文字符串
     * @param secretKey 密钥 (AES加密算法，比较高级，所以key的大小必须是16个字节)
     * @return
     */
    public static String encryptWithAES(String plaintext, String secretKey) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");

        // 指定秘钥规则
        // 第一个参数表示：密钥，key的字节数组
        // 第二个参数表示：算法
        SecretKeySpec sks = new SecretKeySpec(secretKey.getBytes(), "AES");

        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE, sks);

        // 进行加密
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 对称解密(AES) (建议）
     *
     * @param ciphertext 明文字符串
     * @param secretKey  密钥 (AES加密算法，比较高级，所以key的大小必须是16个字节)
     * @return
     * @throws Exception
     */
    public static String decryptWithAES(String ciphertext, String secretKey) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance("AES");
        // 指定密钥规则
        SecretKeySpec sks = new SecretKeySpec(secretKey.getBytes(), "AES");

        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.DECRYPT_MODE, sks);

        //  解密，上面使用的base64编码，下面直接用密文
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 生成非对称加密密钥对(RSA)
     *
     * @return
     * @throws Exception
     */
    public static RSAKeyPair generateRSAKeyPair() throws Exception {

        //  创建密钥对生成器对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //私钥对象
        PrivateKey privateKey = keyPair.getPrivate();
        //公钥对象
        PublicKey publicKey = keyPair.getPublic();

        //构建返回对象
        RSAKeyPair rsaKeyPair = new RSAKeyPair();
        rsaKeyPair.setPrivateKey(Base64.getEncoder().encodeToString((privateKey.getEncoded())));
        rsaKeyPair.setPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        return rsaKeyPair;
    }

    /**
     * 非对称加密 (RSA)
     *
     * @param plainText 明文
     * @param secretKey 密钥
     * @return
     * @throws Exception
     */
    public static String encryptWithRSA(String plainText, String secretKey) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance("RSA");

        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        /**
         * 兼容公钥和私钥：
         *      1、假设是公钥
         *      2、若是公钥，不进catch，直接拿公钥
         *      3、若是私钥，进catch，拿私钥
         */
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        try {
            // 构建密钥规范 进行Base64解码
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(secretKey));
            publicKey = keyFactory.generatePublic(spec);
        } catch (Exception e) {
            // 构建密钥规范 进行Base64解码
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(secretKey));
            privateKey = keyFactory.generatePrivate(spec);
        }

        Key key = publicKey != null ? publicKey : privateKey;

        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 对密文进行解密，不需要使用base64，因为原文不会乱码
        byte[] bytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 非对称解密 (RSA)
     *
     * @param ciphertext
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decryptWithRSA(String ciphertext, String secretKey) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance("RSA");
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        /**
         * 兼容公钥和私钥：
         *      1、假设是公钥
         *      2、若是公钥，不进catch，直接拿公钥
         *      3、若是私钥，进catch，拿私钥
         */
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        try {
            // 构建密钥规范 进行Base64解码
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(secretKey));
            publicKey = keyFactory.generatePublic(spec);
        } catch (Exception e) {
            // 构建密钥规范 进行Base64解码
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(secretKey));
            privateKey = keyFactory.generatePrivate(spec);
        }
        Key key = publicKey != null ? publicKey : privateKey;

        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.DECRYPT_MODE, key);

        //  解密，上面使用的base64编码，下面直接用密文
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }


    /**
     * Base64 编码
     *
     * @param plainText 明文
     * @return
     */
    public static String encodeBase64(String plainText) {
        byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(plainBytes);
    }

    /**
     * Base64 解码
     *
     * @param base64Text 密文
     * @return
     */
    public static String decodeBase64(String base64Text) {
        byte[] base64Bytes = Base64.getDecoder().decode(base64Text);
        return new String(base64Bytes, StandardCharsets.UTF_8);
    }

    /**
     * md5加密
     *
     * @param plainText 明文
     * @return
     */
    public static String encryptMD5(String plainText) {
        try {
            char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] bytes = plainText.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    /**
     * 随机生成AES秘钥
     */
    public static String generateAESKey() throws Exception {

        KeyGenerator kg = KeyGenerator.getInstance("AES");

        //要生成多少位，只需要修改这里即可128, 192或256
        kg.init(128);
        SecretKey sk = kg.generateKey();
        byte[] bytes = sk.getEncoded();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String plainText = "你好！";
        String desKey = "wenxiang";
//        String aesKey = "1234567812345678";

        System.out.println("原文：" + plainText);

        System.out.println("=======================================");
        System.out.println("MD5密文：" + encryptMD5(plainText));

        System.out.println("=======================================");
        String encryptWithDES = encryptWithDES(plainText, desKey);
        System.out.println("DES加密密文：" + encryptWithDES);

        System.out.println("=======================================");
        String decryptWithDES = decryptWithDES(encryptWithDES, desKey);
        System.out.println("DES解密明文：" + decryptWithDES);

        System.out.println("=======================================");
        String aesKey = generateAESKey();
        System.out.println("AES加密密钥：" + generateAESKey());

        System.out.println("=======================================");
        String encryptWithAES = encryptWithAES(plainText, aesKey);
        System.out.println("AES加密密文：" + encryptWithAES);

        System.out.println("=======================================");
        String decryptWithAES = decryptWithAES(encryptWithAES, aesKey);
        System.out.println("AES解密明文：" + decryptWithAES);

        System.out.println("=======================================");
        RSAKeyPair rsaKeyPair = generateRSAKeyPair();
        String privateKey = rsaKeyPair.getPrivateKey();
        System.out.println("非对称加密-私钥：" + privateKey);
        String publicKey = rsaKeyPair.getPublicKey();
        System.out.println("非对称加密-公钥：" + publicKey);

        System.out.println("=======================================");
        String encryptWithRSAPrivateKey = encryptWithRSA(plainText, privateKey);
        System.out.println("RSA私钥加密密文：" + encryptWithRSAPrivateKey);
        String decryptWithRSAPublicKey = decryptWithRSA(encryptWithRSAPrivateKey, publicKey);
        System.out.println("RSA公钥解密明文：" + decryptWithRSAPublicKey);

        System.out.println("=======================================");
        String encryptWithRSAPublicKey = encryptWithRSA(plainText, publicKey);
        System.out.println("RSA公钥加密密文：" + encryptWithRSAPublicKey);
        String decryptWithRSAPrivateKey = decryptWithRSA(encryptWithRSAPublicKey, privateKey);
        System.out.println("RSA私钥解密明文：" + decryptWithRSAPrivateKey);
    }
}


