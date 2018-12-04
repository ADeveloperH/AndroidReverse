package com.adeveloperh.androidreversestudy.jni.book.xml;

import com.adeveloperh.androidreversestudy.jni.book.Utils;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author huangjian
 * @create 2018/12/2
 * @Description
 */
public class ParseChunkUtils {

    public static StringBuilder xmlSB = new StringBuilder();

    public static int stringChunkOffset;
    private static ArrayList<String> stringContentList;
    private static int resourceChunkOffset;
    private static int nextChunkOffset;

    private static HashMap<String, String> uriPrefixMap = new HashMap<String, String>();
    private static HashMap<String, String> prefixUriMap = new HashMap<String, String>();

    /**
     * 解析 Header 包含两个信息：
     * 1、Magic Number（文件魔数）：4 个字节
     * 2、File Size（文件大小）：4 个字节
     *
     * @param res
     */
    public static void parseXmlHeader(byte[] res) {
        byte[] xmlMagic = Utils.copyBytes(res, 0, 4);
        System.out.println("Magic Number is :" + Utils.byte2HexString(xmlMagic));
        byte[] xmlSize = Utils.copyBytes(res, 4, 4);
        System.out.println("File Size is :" + Utils.byte2HexString(xmlSize));
        xmlSB.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("\n");
        stringChunkOffset = xmlMagic.length + xmlSize.length;
    }

    /**
     * 解析 String Chunk 信息,构成如下：
     * 1、ChunkType：StringChunk的类型，固定 4 个字节：0x001C0001
     * 2、ChunkSize：StringChunk的大小，4 个字节
     * 3、StringCount：StringChunk中字符串的个数，4 个字节
     * 4、StyleCount：StringChunk中样式的个数，4 个字节，但是在实际解析过程中，这个值一直是0x00000000
     * 5、Unknown：位置区域，4 个字节，在解析的过程中，这里需要略过四个字节
     * 6、StringPoolOffset：字符串池的偏移值，4 个字节，这个偏移值是相对于StringChunk的头部位置
     * 7、StylePoolOffset：样式池的偏移值，4 个字节，这里没有Style,所以这个字段可忽略
     * 8、StringOffsets：每个字符串的偏移值，所以他的大小应该是：StringCount * 4 个字节
     * 9、SytleOffsets：每个样式的偏移值，所以他的大小应该是 SytleCount * 4 个字节
     *
     * @param res
     */
    public static void parseStringChunk(byte[] res) {
        int start = stringChunkOffset;
        int count = 0;
        byte[] chunkType = Utils.copyBytes(res, start = start + count, count = 4);
        System.out.println("Chunk Type :" + Utils.byte2HexString(chunkType));
        byte[] chunkSizeByte = Utils.copyBytes(res, start = start + count, count = 4);
        int chunkSize = Utils.byteArr2Int(chunkSizeByte);
        System.out.println("Chunk Size :" + chunkSize);
        byte[] stringCountByte = Utils.copyBytes(res, start = start + count, count = 4);
        //String Count
        int stringCount = Utils.byteArr2Int(stringCountByte);
        System.out.println("String Count :" + stringCount);
        byte[] styleCount = Utils.copyBytes(res, start = start + count, count = 4);
        System.out.println("Style Count :" + Utils.byteArr2Int(styleCount));
        byte[] unknown = Utils.copyBytes(res, start = start + count, count = 4);
        System.out.println("UnKnow :" + Utils.byte2HexString(unknown));

        stringContentList = new ArrayList<String>(stringCount);
        //String Offset 相对于String Chunk的起始位置 8 = Magic Number(4 字节) + File Size(4 字节)
        byte[] stringPoolOffset = Utils.copyBytes(res, start + count, 4);

        int stringContentStart = 8 + Utils.byteArr2Int(stringPoolOffset);
        System.out.println("String Chunk Start:" + stringContentStart);

        byte[] stringChunkByte = Utils.copyBytes(res, stringContentStart, chunkSize);

        /**
         * 在解析字符串的时候有个问题，就是编码：UTF-8 和 UTF-16,如果是 UTF-8 的话是以 00 结尾的，如果是 UTF-16 的话以 00 00 结尾的
         * 此处代码是用来解析 AndroidManifest.xml 文件的
         * 这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符 00
         */
//        byte[] firstStringSizeByte = Utils.copyBytes(stringChunkByte, 0, 2);
//        // Utils.byteArr2Int(firstStringSizeByte) 代表字符串的长度，字符串中一个字符占用 2 个字节
//        int firstStringSize = Utils.byteArr2Int(firstStringSizeByte) * 2;
//        System.out.println("String Chunk Size：" + firstStringSize);
//        byte[] firstStringContentByte = Utils.copyBytes(stringChunkByte, 2, firstStringSize + 2);
//        String firstStringContent = new String(firstStringContentByte);
//        firstStringContent = Utils.filterStringNull(firstStringContent);
//        stringContentList.add(firstStringContent);
//        System.out.println("str :" + firstStringContent);
//
//        int endStringIndex = 2 + firstStringSize + 2;
//        while (stringContentList.size() < stringCount) {
//            int stringSize = Utils.byteArr2Int(Utils.copyBytes(stringChunkByte, endStringIndex, 2)) * 2;
//            String string = new String(Utils.copyBytes(stringChunkByte, endStringIndex + 2, stringSize + 2));
//            string = Utils.filterStringNull(string);
//            System.out.println("str :" + string);
//            stringContentList.add(string);
//            endStringIndex += (2 + stringSize + 2);
//        }


        /**
         * 此处的代码是用来解析资源文件xml的
         */
        int stringStart = 0;
        int index = 0;
        while (index < stringCount) {
            byte[] stringSizeByte = Utils.copyBytes(stringChunkByte, stringStart, 2);
            int stringSize = (stringSizeByte[1] & 0x7F);
            if (stringSize != 0) {
                //这里注意是UTF-8编码的
                String val = "";
                try {
                    val = new String(Utils.copyBytes(stringChunkByte, stringStart + 2, stringSize), "utf-8");
                } catch (Exception e) {
                    System.out.println("string encode error:" + e.toString());
                }
                stringContentList.add(val);
            } else {
                stringContentList.add("");
            }
            stringStart += (stringSize + 3);
            index++;
        }
        for (String str : stringContentList) {
            System.out.println("str:" + str);
        }

        resourceChunkOffset = stringChunkOffset + chunkSize;
    }

    public static void parseResourceChunk(byte[] resource) {
        byte[] chunkTypeByte = Utils.copyBytes(resource, resourceChunkOffset, 4);
        System.out.println("chunkType :" + Utils.byte2HexString(chunkTypeByte));
        byte[] chunkSizeByte = Utils.copyBytes(resource, resourceChunkOffset + 4, 4);
        int chunkSize = Utils.byteArr2Int(chunkSizeByte);
        System.out.println("chunk size :" + chunkSize);

        //这里需要注意的是 chunkSize 是包含了 chunkType 和 chunkSize 这两个字节的，所以需要剔除
        byte[] resourceIdByte = Utils.copyBytes(resource, resourceChunkOffset + 8, chunkSize - 8);
        ArrayList<Integer> resourceIdList = new ArrayList<Integer>(resourceIdByte.length / 4);
        for (int i = 0; i < resourceIdByte.length; i += 4) {
            byte[] resByte = Utils.copyBytes(resourceIdByte, i, 4);
            int resourceId = Utils.byteArr2Int(resByte);
            System.out.println("id :" + resourceId + " , hex :" + Utils.byte2HexString(resByte));
            resourceIdList.add(resourceId);
        }
        nextChunkOffset = resourceChunkOffset + chunkSize;
    }

    public static void parseXmlContent(byte[] resource) {
        while (!isEnd(resource.length)) {
            byte[] chunkTagByte = Utils.copyBytes(resource, nextChunkOffset, 4);
            byte[] chunkSizeByte = Utils.copyBytes(resource, nextChunkOffset + 4, 4);
            int chunkTag = Utils.byteArr2Int(chunkTagByte);
            int chunkSize = Utils.byteArr2Int(chunkSizeByte);
            System.out.println("chunk tag:" + Utils.byte2HexString(chunkTagByte));
            switch (chunkTag) {
                case ChunkMagicNumber.CHUNK_STARTNS:
                    System.out.println("parse start namespace");
                    parseStartNamespaceChunk(Utils.copyBytes(resource, nextChunkOffset, chunkSize));
                    break;
                case ChunkMagicNumber.CHUNK_STARTTAG:
                    System.out.println("parse start tag");
                    parseStartTagChunk(Utils.copyBytes(resource, nextChunkOffset, chunkSize));
                    break;
                case ChunkMagicNumber.CHUNK_ENDTAG:
                    System.out.println("parse end tag");
                    parseEndTagChunk(Utils.copyBytes(resource, nextChunkOffset, chunkSize));
                    break;
                case ChunkMagicNumber.CHUNK_ENDNS:
                    System.out.println("parse end namespace");
                    parseEndNamespaceChunk(Utils.copyBytes(resource, nextChunkOffset, chunkSize));
                    break;
            }
            System.out.println("=========================================================================");
            nextChunkOffset += chunkSize;
        }

        System.out.println("parse xml:\n" + xmlSB.toString());
    }


    public static boolean isEnd(int totalLen) {
        return nextChunkOffset >= totalLen;
    }

    /**
     * 解析 StartTag Chunk
     *
     * @param byteSrc
     */
    public static void parseStartTagChunk(byte[] byteSrc) {
        //解析 ChunkTag
        byte[] chunkTagByte = Utils.copyBytes(byteSrc, 0, 4);
        System.out.println(Utils.byte2HexString(chunkTagByte));

        //解析ChunkSize
        byte[] chunkSizeByte = Utils.copyBytes(byteSrc, 4, 4);
        int chunkSize = Utils.byteArr2Int(chunkSizeByte);
        System.out.println("chunk size:" + chunkSize);

        //解析行号
        byte[] lineNumberByte = Utils.copyBytes(byteSrc, 8, 4);
        int lineNumber = Utils.byteArr2Int(lineNumberByte);
        System.out.println("line number:" + lineNumber);

        //解析prefix
        byte[] prefixByte = Utils.copyBytes(byteSrc, 8, 4);
        int prefixIndex = Utils.byteArr2Int(prefixByte);
        //这里可能会返回-1，如果返回-1的话，那就是说没有prefix
        if (prefixIndex != -1 && prefixIndex < stringContentList.size()) {
            System.out.println("prefix:" + prefixIndex);
            System.out.println("prefix str:" + stringContentList.get(prefixIndex));
        } else {
            System.out.println("prefix null");
        }

        //解析Uri
        byte[] uriByte = Utils.copyBytes(byteSrc, 16, 4);
        int uriIndex = Utils.byteArr2Int(uriByte);
        if (uriIndex != -1 && prefixIndex < stringContentList.size()) {
            System.out.println("uri:" + uriIndex);
            System.out.println("uri str:" + stringContentList.get(uriIndex));
        } else {
            System.out.println("uri null");
        }

        //解析TagName
        byte[] tagNameByte = Utils.copyBytes(byteSrc, 20, 4);
        System.out.println(Utils.byte2HexString(tagNameByte));
        int tagNameIndex = Utils.byteArr2Int(tagNameByte);
        String tagName = stringContentList.get(tagNameIndex);
        if (tagNameIndex != -1) {
            System.out.println("tag name index:" + tagNameIndex);
            System.out.println("tag name str:" + tagName);
        } else {
            System.out.println("tag name null");
        }

        //解析属性个数(这里需要过滤四个字节:14001400)
        byte[] attrCountByte = Utils.copyBytes(byteSrc, 28, 4);
        int attrCount = Utils.byteArr2Int(attrCountByte);
        System.out.println("attr count:" + attrCount);

        //解析属性
        //这里需要注意的是每个属性单元都是由五个元素组成，每个元素占用四个字节：namespaceuri, name, valuestring, type, data
        //在获取到type值的时候需要右移24位
        ArrayList<AttributeData> attrList = new ArrayList<AttributeData>(attrCount);
        for (int i = 0; i < attrCount; i++) {
            Integer[] values = new Integer[5];
            AttributeData attrData = new AttributeData();
            for (int j = 0; j < 5; j++) {
                int value = Utils.byteArr2Int(Utils.copyBytes(byteSrc, 36 + i * 20 + j * 4, 4));
                switch (j) {
                    case 0:
                        attrData.nameSpaceUri = value;
                        break;
                    case 1:
                        attrData.name = value;
                        break;
                    case 2:
                        attrData.valueString = value;
                        break;
                    case 3:
                        value = (value >> 24);
                        attrData.type = value;
                        break;
                    case 4:
                        attrData.data = value;
                        break;
                }
                values[j] = value;
            }
            attrList.add(attrData);
        }

        for (int i = 0; i < attrCount; i++) {
            if (attrList.get(i).nameSpaceUri != -1) {
                System.out.println("nameSpaceUri:" + stringContentList.get(attrList.get(i).nameSpaceUri));
            } else {
                System.out.println("nameSpaceUri == null");
            }
            if (attrList.get(i).name != -1) {
                System.out.println("name:" + stringContentList.get(attrList.get(i).name));
            } else {
                System.out.println("name == null");
            }
            if (attrList.get(i).valueString != -1) {
                System.out.println("valueString:" + stringContentList.get(attrList.get(i).valueString));
            } else {
                System.out.println("valueString == null");
            }
            System.out.println("type:" + AttributeType.getAttrType(attrList.get(i).type));
            System.out.println("data:" + AttributeType.getAttributeData(attrList.get(i)));
        }

        //这里开始构造xml结构
        xmlSB.append(createStartTagXml(tagName, attrList));

    }

    public static void parseEndTagChunk(byte[] byteSrc) {
        byte[] chunkTagByte = Utils.copyBytes(byteSrc, 0, 4);
        System.out.println(Utils.byte2HexString(chunkTagByte));
        byte[] chunkSizeByte = Utils.copyBytes(byteSrc, 4, 4);
        int chunkSize = Utils.byteArr2Int(chunkSizeByte);
        System.out.println("chunk size:" + chunkSize);

        byte[] lineNumberByte = Utils.copyBytes(byteSrc, 8, 4);
        int lineNumber = Utils.byteArr2Int(lineNumberByte);
        System.out.println("line number:" + lineNumber);

        byte[] prefixByte = Utils.copyBytes(byteSrc, 8, 4);
        int prefixIndex = Utils.byteArr2Int(prefixByte);

        if (prefixIndex != -1 && prefixIndex < stringContentList.size()) {
            System.out.println("prefix:" + prefixIndex);
            System.out.println("prefix str:" + stringContentList.get(prefixIndex));
        } else {
            System.out.println("prefix null");
        }

        byte[] uriByte = Utils.copyBytes(byteSrc, 16, 4);
        int uriIndex = Utils.byteArr2Int(uriByte);
        if (uriIndex != -1 && prefixIndex < stringContentList.size()) {
            System.out.println("uri:" + uriIndex);
            System.out.println("uri str:" + stringContentList.get(uriIndex));
        } else {
            System.out.println("uri null");
        }

        byte[] tagNameByte = Utils.copyBytes(byteSrc, 20, 4);
        System.out.println(Utils.byte2HexString(tagNameByte));
        int tagNameIndex = Utils.byteArr2Int(tagNameByte);
        String tagName = stringContentList.get(tagNameIndex);
        if (tagNameIndex != -1) {
            System.out.println("tag name index:" + tagNameIndex);
            System.out.println("tag name str:" + tagName);
        } else {
            System.out.println("tag name null");
        }

        xmlSB.append(createEndTagXml(tagName));
    }


    public static void parseStartNamespaceChunk(byte[] byteSrc) {
        byte[] chunkTagByte = Utils.copyBytes(byteSrc, 0, 4);
        System.out.println(Utils.byte2HexString(chunkTagByte));
        byte[] chunkSizeByte = Utils.copyBytes(byteSrc, 4, 4);
        int chunkSize = Utils.byteArr2Int(chunkSizeByte);
        System.out.println("chunk size:" + chunkSize);

        byte[] lineNumberByte = Utils.copyBytes(byteSrc, 8, 4);
        int lineNumber = Utils.byteArr2Int(lineNumberByte);
        System.out.println("line number:" + lineNumber);

        byte[] prefixByte = Utils.copyBytes(byteSrc, 16, 4);
        int prefixIndex = Utils.byteArr2Int(prefixByte);
        String prefix = stringContentList.get(prefixIndex);
        System.out.println("prefix:" + prefixIndex);
        System.out.println("prefix str:" + prefix);

        byte[] uriByte = Utils.copyBytes(byteSrc, 20, 4);
        int uriIndex = Utils.byteArr2Int(uriByte);
        String uri = stringContentList.get(uriIndex);
        System.out.println("uri:" + uriIndex);
        System.out.println("uri str:" + uri);

        uriPrefixMap.put(uri, prefix);
        prefixUriMap.put(prefix, uri);
    }

    public static void parseEndNamespaceChunk(byte[] byteSrc) {

    }

    private static String createStartTagXml(String tagName, List<AttributeData> attrList) {
        StringBuilder tagSb = new StringBuilder();
        if ("manifest".equals(tagName)) {
            tagSb.append("<manifest xmls:");
            StringBuilder prefixSb = new StringBuilder();
            for (String key : prefixUriMap.keySet()) {
                prefixSb.append(key + ":\"" + prefixUriMap.get(key) + "\"");
                prefixSb.append("\n");
            }
            tagSb.append(prefixSb.toString());
        } else {
            tagSb.append("<" + tagName);
        }

        if (attrList.size() == 0) {
            tagSb.append(">\n");
        } else {
            tagSb.append("\n");
            for (int i = 0; i < attrList.size(); i++) {
                AttributeData attr = attrList.get(i);
                String prefixName = uriPrefixMap.get(attr.getNameSpaceUri());
                if (prefixName == null) {
                    prefixName = "";
                }
                tagSb.append("    ");
                tagSb.append(prefixName + (prefixName.length() > 0 ? ":" : "") + attr.getName() + "=");
                tagSb.append("\"" + AttributeType.getAttributeData(attr) + "\"");
                if (i == (attrList.size() - 1)) {
                    tagSb.append(">");
                }
                tagSb.append("\n");
            }
        }

        return tagSb.toString();
    }

    private static String createEndTagXml(String tagName) {
        return "</" + tagName + ">\n";
    }

    public static String getStringContent(int index) {
        return stringContentList.get(index);
    }

    public static void writeFormatXmlToFile() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("D:\\Repositories\\AndroidReverseStudy\\app\\src\\main\\test\\AndroidManifest_format.xml");
            fw.write(xmlSB.toString());
        } catch (Exception e) {
            System.out.println("write format xml file error:" + e.toString());
        } finally {
            try {
                fw.close();
            } catch (Exception e) {
                System.out.println("close file error:" + e.toString());
            }
        }
    }
}
