package me.limeice.common.function.algorithm.security;


import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * <p>AES 算法 对称加密，密码学中的高级加密标准
 * <p>警告：
 * <p>	1.AES-128-CBC-NoPadding需要的加密的字符串长度必须是16的整倍数
 * <p>	2.AES-128 decrypt函数解密的字符串或byte[]的长度必须满足16的倍数
 *
 * <p>示例：
 * <pre>{@code
 *      AES128 aes=new AES128(AES128.ECB|AES128.PKCS5Padding);
 *      //加密
 *      String s= aes.encryptBase64("src_Lime", "password");
 *      //解密
 *      String str = aes.decryptBase64(s, "password");
 * }</pre>
 *
 * @author Lime
 * <p>2018.03.16
 */
public final class AES128 extends AES128Base {

    public AES128(int aesType) {
        super(aesType);
    }

    /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg      加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到密文
     * @throws NoSuchPaddingException             see {@link NoSuchPaddingException}
     * @throws InvalidAlgorithmParameterException see {@link InvalidAlgorithmParameterException}
     * @throws NoSuchAlgorithmException           see {@link NoSuchAlgorithmException}
     * @throws IllegalBlockSizeException          see {@link IllegalBlockSizeException}
     * @throws BadPaddingException                see {@link BadPaddingException}
     * @throws InvalidKeyException                see {@link InvalidKeyException}
     */

    @NonNull
    public byte[] encrypt(@NonNull String msg, @NonNull byte[] keyBytes) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            return encrypt(msg.getBytes(BYTE_TYPE), keyBytes);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("UTF-8 code does not support" + ex.getMessage());
        }
    }


    /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg      加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到密文
     * @throws NoSuchPaddingException             see {@link NoSuchPaddingException}
     * @throws InvalidAlgorithmParameterException see {@link InvalidAlgorithmParameterException}
     * @throws NoSuchAlgorithmException           see {@link NoSuchAlgorithmException}
     * @throws IllegalBlockSizeException          see {@link IllegalBlockSizeException}
     * @throws BadPaddingException                see {@link BadPaddingException}
     * @throws InvalidKeyException                see {@link InvalidKeyException}
     */
    @NonNull
    public String encryptBase64(@NonNull String msg, @NonNull byte[] keyBytes) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            return Base64.encodeToString(encrypt(msg.getBytes(BYTE_TYPE), keyBytes), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("UTF-8 code does not support" + ex.getMessage());
        }
    }

    /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg  加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param sKey 解密密钥，经过一次MD5
     * @return 得到密文
     * @throws NoSuchPaddingException             see {@link NoSuchPaddingException}
     * @throws InvalidAlgorithmParameterException see {@link InvalidAlgorithmParameterException}
     * @throws NoSuchAlgorithmException           see {@link NoSuchAlgorithmException}
     * @throws IllegalBlockSizeException          see {@link IllegalBlockSizeException}
     * @throws BadPaddingException                see {@link BadPaddingException}
     * @throws InvalidKeyException                see {@link InvalidKeyException}
     */
    @NonNull
    public String encryptBase64(@NonNull String msg, @NonNull String sKey) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return encryptBase64(msg, Hash.md5ToBytes(sKey));
    }

    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg      需要解密的数据，数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到明文
     * @throws NoSuchPaddingException             see {@link NoSuchPaddingException}
     * @throws InvalidAlgorithmParameterException see {@link InvalidAlgorithmParameterException}
     * @throws NoSuchAlgorithmException           see {@link NoSuchAlgorithmException}
     * @throws IllegalBlockSizeException          see {@link IllegalBlockSizeException}
     * @throws BadPaddingException                see {@link BadPaddingException}
     * @throws InvalidKeyException                see {@link InvalidKeyException}
     */
    @NonNull
    public byte[] decrypt(@NonNull String msg, @NonNull byte[] keyBytes) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            return decrypt(msg.getBytes(BYTE_TYPE), keyBytes);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("UTF-8 code does not support" + ex.getMessage());
        }
    }


    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg      需要解密的数据，数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到明文
     * @throws NoSuchPaddingException             see {@link NoSuchPaddingException}
     * @throws InvalidAlgorithmParameterException see {@link InvalidAlgorithmParameterException}
     * @throws NoSuchAlgorithmException           see {@link NoSuchAlgorithmException}
     * @throws IllegalBlockSizeException          see {@link IllegalBlockSizeException}
     * @throws BadPaddingException                see {@link BadPaddingException}
     * @throws InvalidKeyException                see {@link InvalidKeyException}
     */
    @NonNull
    public String decryptBase64(@NonNull String msg, @NonNull byte[] keyBytes) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            return new String(decrypt(Base64.decode(msg, Base64.DEFAULT), keyBytes), BYTE_TYPE);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("UTF-8 code does not support" + ex.getMessage());
        }
    }

    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg  需要解密的数据，数据长度必须为16的倍数！
     * @param sKey 解密密钥，经过一次MD5
     * @return 得到明文
     * @throws NoSuchPaddingException             see {@link NoSuchPaddingException}
     * @throws InvalidAlgorithmParameterException see {@link InvalidAlgorithmParameterException}
     * @throws NoSuchAlgorithmException           see {@link NoSuchAlgorithmException}
     * @throws IllegalBlockSizeException          see {@link IllegalBlockSizeException}
     * @throws BadPaddingException                see {@link BadPaddingException}
     * @throws InvalidKeyException                see {@link InvalidKeyException}
     */
    @NonNull
    public String decryptBase64(@NonNull String msg, @NonNull String sKey) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return decryptBase64(msg, Hash.md5ToBytes(sKey));
    }
}
