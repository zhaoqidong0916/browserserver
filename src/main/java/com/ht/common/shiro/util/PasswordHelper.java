package com.ht.common.shiro.util;

import com.ht.common.entity.AuthUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author yakun.shi
 * @date 2020/3/5 11:28
 * @desc 密码加密解密
 */
public class PasswordHelper {
    private static String algorithmName = "md5";
    private static int hashIterations = 1024;
    private static String common = "browser";

    /**
     * 生成MD5密码
     * @param user
     * @return
     */
    public static String encryptPassword(AuthUser user) {
        String salt = user.getLoginName() + common + user.getPassword();
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(salt), hashIterations).toHex();
        return newPassword;
    }

    /**
     * 生成MD5密码
     * @param loginName 登录名
     * @param password 密码
     * @return
     */
    public static String encryptPassword(String loginName, String password) {
        String salt = loginName + common + password;
        String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(salt), hashIterations).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        String admin = encryptPassword("admin", "123456");
        System.out.println(admin);
    }
}
