package com.ht.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @program: ht-common-admin
 * @description: 文件字符串工具
 * @author: yaKun.shi
 * @create: 2019-07-29 14:27
 **/
public class HandleUtils {

    private static final String[] videos
            = {"mp4", "MP4", "avi", "mpeg", "wmv", "MPEG", "MPG", "mpg", "WMV"};
    private static final String[] photos
            = {"jpg", "png", "PNG", "JPG", "jpeg", "JPEG"};

    private static final String[] banners
            = {"mp4", "MP4", "jpg", "png", "PNG", "JPG", "jpeg", "JPEG", "avi", "MPEG", "mpeg", "MPG", "mpg", "WMV", "wmv"};

    private static final String[] plugin
            = {"exe"};

    private static final String[] cer
            = {"cer"};

    private static final String[] browser
            = {"cer", "exe", "deb", "rpm"};


    /**
     * 是否为视频格式
     *
     * @param name
     * @return
     */
    public static boolean isVideo(String name) {
        return handle(name, videos);
    }


    /**
     * 是否为升级
     *
     * @param name
     * @return
     */
    public static boolean isBrowser(String name) {
        return handle(name, browser);
    }


    /**
     * 是否为证书
     *
     * @param name
     * @return
     */
    public static boolean isCer(String name) {
        return handle(name, cer);
    }

    /**
     * 是否为图片
     *
     * @param name
     * @return
     */
    public static boolean isPhoto(String name) {
        return handle(name, photos);
    }

    /**
     * 是否为插件
     *
     * @param name
     * @return
     */
    public static boolean isPlugin(String name) {
        return handle(name, plugin);
    }

    /**
     * 是否为背景图
     *
     * @param name
     * @return
     */
    public static boolean isBanners(String name) {
        return handle(name, banners);
    }


    public static String getRandomName(String name, String uuid) {
        return split(name, uuid);
    }

    public static String getRandomName(String name) {
        return split(name, null);
    }


    /**
     * 判断是否为符合规定的格式
     *
     * @param name
     * @param banner
     * @return
     */
    private static boolean handle(String name, String[] banner) {
        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException();
        }
        try {
            String[] split = StringUtils.split(name, ".");
            String suffix = split[split.length - 1];
            List<String> list = Arrays.asList(banner);
            if (!list.contains(suffix)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String split(String name, String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            uuid = UUIDUtil.getUUID();
        }
        StringBuilder builder = new StringBuilder(uuid);
        String[] split = StringUtils.split(name, ".");
        return builder.append(".").append(split[split.length - 1]).toString();
    }

    private static List<String> PATTRN = new ArrayList<>();
    private static Map<String, Integer> MAPS = new HashMap<>(16);

    private static Map<Integer, String> PLATFORM_TYPE = new HashMap<>(16);

    static {
        PATTRN.add("w_x86_exe");
        PATTRN.add("l_x86_deb");
        PATTRN.add("l_x86_rpm");
        PATTRN.add("l_arm_deb");
        PATTRN.add("l_arm_rpm");
        PATTRN.add("l_mips_deb");
        PATTRN.add("l_mips_rpm");


        MAPS.put("w_x86_exe", 1);
        MAPS.put("l_x86_deb", 2);
        MAPS.put("l_x86_rpm", 3);
        MAPS.put("l_arm_deb", 4);
        MAPS.put("l_arm_rpm", 5);
        MAPS.put("l_mips_deb", 6);
        MAPS.put("l_mips_rpm", 7);

        PLATFORM_TYPE.put(1, "windows");
        PLATFORM_TYPE.put(2, "兆芯");
        PLATFORM_TYPE.put(3, "兆芯");
        PLATFORM_TYPE.put(4, "arm");
        PLATFORM_TYPE.put(5, "arm");
        PLATFORM_TYPE.put(6, "龙芯");
        PLATFORM_TYPE.put(7, "龙芯");
    }


    /**
     * 解析安装包格式
     *
     * @param name
     * @return
     */
    public static boolean handlePackage(String name) {

        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException();
        }
        try {
            //name 格式为  aaa_w_x86_1.0.0.1.exe  aaa后面的都是不可变的
            //根据_截取字符串
            String[] split = StringUtils.split(name, "_");
            int length = split.length;
            //如果长度小于4说明格式错误
            if (length < 4) {
                return false;
            }
            String[] split1 = StringUtils.split(name, ".");
            //获取操作系统标识  l ,w
            String platform = split[length - 3];
            //获取内核架构标识  x86 arm mips
            String framework = split[length - 2];
            //获取后缀名
            String suffixName = split1[split1.length - 1];
            StringBuilder builder = new StringBuilder();
            String s = builder.append(platform).append("_").append(framework).append("_").append(suffixName).toString();
            if (!PATTRN.contains(s)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Integer getType(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException();
        }
        String[] split1 = StringUtils.split(name, ".");
        //获取文件后缀名
        String suffixName = split1[split1.length - 1];

        String[] split = StringUtils.split(name, "_");
        int length = split.length;
        //获取操作系统标识  l ,w
        String platform = split[length - 3];
        //获取内核架构标识  x86 arm mips
        String framework = split[length - 2];
        String key = new StringBuilder().append(platform).append("_").append(framework).append("_").append(suffixName).toString();
        //标识   1:win exe,2:兆芯deb,3:兆芯rpm,4:arm deb,5:arm rpm,6:龙芯deb,7:龙芯rpm
        Integer type = MAPS.get(key);
        return type;
    }

    /**
     * 获取控件安装包类型
     *
     * @param name
     * @return
     */
    public static Integer getPluginOsType(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException();
        }
        Integer type;
        String[] split1 = StringUtils.split(name, ".");
        //获取后缀名
        String suffixName = split1[split1.length - 1];
        if (suffixName.equals("exe")) {
            //windows
            type = 1;
        } else if (suffixName.equals("deb")) {
            //兆芯 deb 版本
            if (name.contains("x86") || name.contains("x86_64") || name.contains("amd64")) {
                type = 2;
            }
            //arm deb版本
            else if (name.contains("arm") || name.contains("arm6464") || name.contains("aarch64")) {
                type = 4;
            }
            //龙芯 deb 版本
            else if (name.contains("mips") || name.contains("mips64") || name.contains("mips64el") || name.contains("loogson")) {
                type = 6;
            } else {
                type = -1;
            }
        } else if (suffixName.equals("rpm")) {
            //兆芯 rpm
            if (name.contains("x86") || name.contains("x86_64") || name.contains("amd64")) {
                type = 3;
            }
            //arm rpm版本
            else if (name.contains("arm") || name.contains("arm6464") || name.contains("aarch64")) {
                type = 5;
            }
            //龙芯 rpm 版本
            else if (name.contains("mips") || name.contains("mips64") || name.contains("mips64el") || name.contains("loogson")) {
                type = 7;
            } else {
                type = -1;
            }
        } else {
            type = -1;
        }
        return type;
    }



    public static String getVersion(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException();
        }
        String[] split = StringUtils.split(name, "_");
        int length = split.length;

        //获取版本
        String s = split[length - 1];
        int i = StringUtils.lastIndexOf(s, ".");
        String version = StringUtils.substring(s, 0, i);
        return version;
    }


    public static String getTypeName(Integer type) {
        String s = PLATFORM_TYPE.get(type);
        return s;
    }


}
