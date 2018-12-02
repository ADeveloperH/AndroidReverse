package com.adeveloperh.androidreversestudy.jni.book.elf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author huangjian
 * @create 2018/12/1
 * @Description
 */
public class Utils {


    /**
     * 输出 byte 数组的 16 进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2HexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i]);
            if (hex.length() < 2) {
                stringBuilder.append("0").append(hex);
            } else {
                stringBuilder.append(hex);
            }
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static String byte2HexString(byte par) {
        String hex = Integer.toHexString(par);
        if (hex.length() < 2) {
            return "0" + hex;
        } else {
            return hex;
        }
    }

    public static byte[] readFile(String path) {
        File file = new File(path);
        if (file != null && file.exists()) {
            FileInputStream fileInputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int size = 0;
                while ((size = fileInputStream.read(temp)) != -1) {
                    byteArrayOutputStream.write(temp, 0, size);
                }
                return byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                System.out.println("Utils.readFile: 解析 so 文件异常：" + e.getLocalizedMessage());
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e) {
                        System.out.println("Utils.readFile: close fileInputStream exception:" + e.getLocalizedMessage());
                    }

                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e) {
                        System.out.println("Utils.readFile: close byteArrayOutputStream exception:" + e.getLocalizedMessage());
                    }
                }
            }
        }
        throw new RuntimeException("找不到该文件：" + path);
    }

    public static byte[] copyBytes(byte[] res, int start, int count) {
        if (res != null && res.length >= start + count) {
            byte[] result = new byte[count];
            for (int i = start; i < start + count; i++) {
                result[i - start] = res[i];
            }
            return result;
        }
        return null;
    }

    public static int byte2Int(byte res) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return res & 0xFF;
    }


    /**
     * int 类型 4 字节
     *
     * @param res
     * @return
     */
    public static int byteArr2Int(byte[] res) {
//        return res[3] & 0xFF
//                | (res[2] & 0xFF) << 8
//                | (res[1] & 0xFF) << 16
//                | (res[0] & 0xFF) << 24;
        if (res.length > 4) {
            res = copyBytes(res, 0, 4);
        }
        int result = 0;
        for (int i = 0; i < res.length; i++) {
            result = result | (res[i] & 0xFF) << 8 * i;
        }
        return result;
    }
}
