package com.adeveloperh.androidreversestudy.jni.book.elf;

import com.adeveloperh.androidreversestudy.jni.book.Utils;

/**
 * @author huangjian
 * @create 2018/12/1
 * @Description so 相关结构字段信息参考：D:\\SDK\\ndk-bundle\\platforms\\android-21\\arch-x86_64\\usr\\include\\linux
 */
public class ParseSo {

    public static ELFType32 elfType32 = new ELFType32();

    public static void main(String[] args) {
        byte[] fileByteArrays = Utils.readFile("D:\\Repositories\\AndroidReverseStudy\\jniLibs\\armeabi-v7a\\libhelloJNI.so");
        parseSo(fileByteArrays);

    }


    private static void parseSo(byte[] fileByteArrays) {

        System.out.println("============================================================== 开始解析 ELF Header =================================================================== ");
        parseELFHeader(fileByteArrays);
        System.out.println("============================================================== 完成解析 ELF Header =================================================================== ");


        System.out.println("============================================================== 开始解析 Program Header =================================================================== ");
        int ph_off = Utils.byteArr2Int(elfType32.hdr.e_phoff);
        System.out.println("Program Header offset:" + ph_off);
        System.out.println("Program Header offset hex :" + Integer.toHexString(ph_off));
        int ph_num = Utils.byteArr2Int(elfType32.hdr.e_phnum);
        System.out.println("Program Header count:" + ph_num);
        parseProgramHeaderList(fileByteArrays, ph_off, ph_num);
        elfType32.printPhdrList();
        System.out.println("============================================================== 完成解析 Program Header =================================================================== ");


        System.out.println("============================================================== 开始解析 Section Header =================================================================== ");
        int sh_off = Utils.byteArr2Int(elfType32.hdr.e_shoff);
        System.out.println("Section Header offset:" + sh_off);
        System.out.println("Section Header offset hex :" + Integer.toHexString(sh_off));
        int sh_count = Utils.byteArr2Int(elfType32.hdr.e_shnum);
        System.out.println("Section Header count:" + sh_count);
        parseSectionHeaderList(fileByteArrays, sh_off, sh_count);
        elfType32.printShdrList();
        System.out.println("============================================================== 完成解析 Section Header =================================================================== ");
    }


    /**
     * 解析 ELF 头部信息（顺序不能改变）
     * 这里包含下边的 Programe Header / Section Header 等的偏移值
     *
     * @param fileByteArrays
     */
    private static void parseELFHeader(byte[] fileByteArrays) {
        /**
         * #define EI_NIDENT 16
         * typedef struct elf32_hdr{
         * unsigned char e_ident[EI_NIDENT];//C 中 char 占用 1 个字节
         * Elf32_Half e_type;
         * Elf32_Half e_machine;
         * Elf32_Word e_version;
         * Elf32_Addr e_entry;
         * Elf32_Off e_phoff;
         * Elf32_Off e_shoff;
         * Elf32_Word e_flags;
         * Elf32_Half e_ehsize;
         * Elf32_Half e_phentsize;
         * Elf32_Half e_phnum;
         * Elf32_Half e_shentsize;
         * Elf32_Half e_shnum;
         * Elf32_Half e_shstrndx;
         * } Elf32_Ehdr;
         *
         * ELF Header 头部信息
         */
        int start = 0;
        int count = 0;
        elfType32.hdr.e_ident = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_ident.length);
        elfType32.hdr.e_type = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_type.length);
        elfType32.hdr.e_machine = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_machine.length);
        elfType32.hdr.e_version = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_version.length);
        elfType32.hdr.e_entry = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_entry.length);
        elfType32.hdr.e_phoff = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_phoff.length);
        elfType32.hdr.e_shoff = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_shoff.length);
        elfType32.hdr.e_flags = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_flags.length);
        elfType32.hdr.e_ehsize = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_ehsize.length);
        elfType32.hdr.e_phentsize = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_phentsize.length);
        elfType32.hdr.e_phnum = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_phnum.length);
        elfType32.hdr.e_shentsize = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_shentsize.length);
        elfType32.hdr.e_shnum = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_shnum.length);
        elfType32.hdr.e_shstrndx = Utils.copyBytes(fileByteArrays, start = start + count, count = elfType32.hdr.e_shstrndx.length);

        System.out.println(elfType32.hdr.toString());
    }

    private static void parseProgramHeaderList(byte[] fileByteArrays, int ph_off, int ph_count) {
        /**
         * Program Header 头部信息（有 ph_count 个）
         * 16 进制中 1 位 = 4 bit ，2 位 = 8 bit = 1 byte
         */
        int header_size = 32;//一个 Program Header 所有字段占用 32 字节。16 进制中 64 位 = 64 / 2 = 32 byte
        byte[] des = new byte[header_size];
        for (int i = 0; i < ph_count; i++) {
            System.arraycopy(fileByteArrays, i * header_size + ph_off, des, 0, header_size);
            elfType32.phdrList.add(parseProgramHeader(des));
        }
    }

    /**
     * 解析 Program Header 信息
     *
     * @param des
     * @return
     */
    private static ELFType32.elf32_phdr parseProgramHeader(byte[] des) {
        /**
         * typedef struct elf32_phdr{
         * Elf32_Word p_type;
         * Elf32_Off p_offset;
         * Elf32_Addr p_vaddr;
         * Elf32_Addr p_paddr;
         * Elf32_Word p_filesz;
         * Elf32_Word p_memsz;
         * Elf32_Word p_flags;
         * Elf32_Word p_align;
         * } Elf32_Phdr;
         * */
        ELFType32.elf32_phdr phdr = new ELFType32.elf32_phdr();
        int start = 0;
        int count = 0;
        phdr.p_type = Utils.copyBytes(des, start = start + count, count = phdr.p_type.length);
        phdr.p_offset = Utils.copyBytes(des, start = start + count, count = phdr.p_offset.length);
        phdr.p_vaddr = Utils.copyBytes(des, start = start + count, count = phdr.p_vaddr.length);
        phdr.p_paddr = Utils.copyBytes(des, start = start + count, count = phdr.p_paddr.length);
        phdr.p_filesz = Utils.copyBytes(des, start = start + count, count = phdr.p_filesz.length);
        phdr.p_memsz = Utils.copyBytes(des, start = start + count, count = phdr.p_memsz.length);
        phdr.p_flags = Utils.copyBytes(des, start = start + count, count = phdr.p_flags.length);
        phdr.p_align = Utils.copyBytes(des, start = start + count, count = phdr.p_align.length);
        return phdr;
    }

    private static void parseSectionHeaderList(byte[] fileByteArrays, int sh_off, int sh_count) {
        /**
         * Section Header 头部信息（有 sh_count 个）
         */
        int header_size = 40;//一个 Section Header 所有字段占用 40 字节。16 进制中 80 位 = 80 / 2 = 40 byte
        byte[] des = new byte[header_size];
        for (int i = 0; i < sh_count; i++) {
            System.arraycopy(fileByteArrays, i * header_size + sh_off, des, 0, header_size);
            elfType32.shdrList.add(parseSectionHeader(des));
        }
    }

    /**
     * 解析 Section Header 头部信息
     * @param des
     * @return
     */
    private static ELFType32.elf32_shdr parseSectionHeader(byte[] des) {
        /**
         * typedef struct elf32_shdr {
         * Elf32_Word sh_name;
         * Elf32_Word sh_type;
         * Elf32_Word sh_flags;
         * Elf32_Addr sh_addr;
         * Elf32_Off sh_offset;
         * Elf32_Word sh_size;
         * Elf32_Word sh_link;
         * Elf32_Word sh_info;
         * Elf32_Word sh_addralign;
         * Elf32_Word sh_entsize;
         * } Elf32_Shdr;
         */
        ELFType32.elf32_shdr shdr = new ELFType32.elf32_shdr();
        int start = 0;
        int count = 0;
        shdr.sh_name = Utils.copyBytes(des, start = start + count, count = shdr.sh_name.length);
        shdr.sh_type = Utils.copyBytes(des, start = start + count, count = shdr.sh_type.length);
        shdr.sh_flags = Utils.copyBytes(des, start = start + count, count = shdr.sh_flags.length);
        shdr.sh_addr = Utils.copyBytes(des, start = start + count, count = shdr.sh_addr.length);
        shdr.sh_offset = Utils.copyBytes(des, start = start + count, count = shdr.sh_offset.length);
        shdr.sh_size = Utils.copyBytes(des, start = start + count, count = shdr.sh_size.length);
        shdr.sh_link = Utils.copyBytes(des, start = start + count, count = shdr.sh_link.length);
        shdr.sh_info = Utils.copyBytes(des, start = start + count, count = shdr.sh_info.length);
        shdr.sh_addralign = Utils.copyBytes(des, start = start + count, count = shdr.sh_addralign.length);
        shdr.sh_entsize = Utils.copyBytes(des, start = start + count, count = shdr.sh_entsize.length);

        return shdr;
    }
}
