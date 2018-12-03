package com.adeveloperh.androidreversestudy.jni.book.xml;

import com.adeveloperh.androidreversestudy.jni.book.Utils;

/**
 * @author huangjian
 * @create 2018/12/2
 * @Description 可以参考：https://blog.csdn.net/jiangwei0910410003/article/details/50568487
 */
public class ParseAndroidManifest {
    public static void main(String[] args) {
        byte[] resource = Utils.readFile("D:\\Repositories\\AndroidReverseStudy\\app\\src\\main\\test\\AndroidManifest.xml");

        System.out.println("============================================================== 开始解析 Header =================================================================== ");
        ParseChunkUtils.parseXmlHeader(resource);
        System.out.println("============================================================== 完成解析 Header =================================================================== ");
        System.out.println("============================================================== 开始解析 String Chunk =================================================================== ");
        ParseChunkUtils.parseStringChunk(resource);
        System.out.println("============================================================== 完成解析 String Chunk =================================================================== ");
        System.out.println("============================================================== 开始解析 ResourceId Chunk =================================================================== ");
        ParseChunkUtils.parseResourceChunk(resource);
        System.out.println("============================================================== 完成解析 ResourceId Chunk =================================================================== ");
        System.out.println("============================================================== 开始解析 XMLContent Chunk =================================================================== ");
        ParseChunkUtils.parseXmlContent(resource);
        System.out.println("============================================================== 完成解析 XMLContent Chunk =================================================================== ");

        ParseChunkUtils.writeFormatXmlToFile();
    }
}
