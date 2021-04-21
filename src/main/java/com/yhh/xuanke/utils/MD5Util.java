package com.yhh.xuanke.utils;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * MD5 加密工具类
 *
 * @author Long
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String SALT = "yhhbysl";

    /**
     * 第一次MD5加密，用于网络传输
     */
    public static String inputPassToFormPass(String inputPass) {
        //避免在网络传输被截取然后反推出密码，所以在md5加密前先打乱密码
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 第二次MD5加密，用于存储到数据库
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 合并
     */
    public static String inputPassToDBPass(String input, String saltDB) {
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(formPassToDBPass("yhh666", "yhhbysl"));
    }

}
