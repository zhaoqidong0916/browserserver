package com.ht.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

/**
 * MD5加密组件
 */
public abstract class ToolMD5 {


    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) {
        return DigestUtils.md5(data);
    }

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static String encodeMD5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * MD5加密
     *
     * @param filePath 待加密文件路径
     * @return String 消息摘要
     * @throws Exception
     */
    public static String encodeMD5HexFile(String filePath) {
        FileInputStream fis = null;
        String hex = null;
        try {
            fis = new FileInputStream(new File(filePath));
            hex = DigestUtils.md5Hex(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hex;
    }

    /**
     * MD5加密
     *
     * @param filePath 待加密文件路径
     * @return String 消息摘要
     * @throws Exception
     */
    public static String encodeSha256HexFile(String filePath) {
        InputStream fis = null;
        String hex = null;
        try {
//			filePath=Utils.pathManipulation(filePath);
            fis = new FileInputStream(new File(filePath));
            hex = DigestUtils.sha256Hex(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hex;
    }

    /**
     * MD5加密
     *
     * @param inputStream 待加密文件流
     * @return String 消息摘要
     * @throws Exception
     */
    public static String encodeSha256HexInputStream(InputStream inputStream) {
        String hex = null;
        try {
            hex = DigestUtils.sha256Hex(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hex;
    }


    /**
     * MD5加密
     *
     * @param bytes 待加密文件byte数组
     * @return String 消息摘要
     * @throws Exception
     */
    public static String encodeSha256HexByte(byte[] bytes) {
        return  DigestUtils.sha256Hex(bytes);

    }



    public static void main(String[] args) {

        String ssss = encodeSha256HexFile("C:\\qq_l_x86_1.0.0.0.rpm");
        System.out.println(ssss);

    }
}
