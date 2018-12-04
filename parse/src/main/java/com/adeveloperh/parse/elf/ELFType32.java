package com.adeveloperh.parse.elf;


import com.adeveloperh.base.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjian
 * @create 2018/12/1
 * @Description 定义的类型信息是从 C  转换过来的，
 */
public class ELFType32 {
    /**
     * unsigned char://1 个字节，java 中 char 2 个字节
     *
     typedef __u32 Elf32_Addr;//4 个字节
     typedef __u16 Elf32_Half;//2 个字节
     typedef __u32 Elf32_Off;
     typedef __s32 Elf32_Sword;
     typedef __u32 Elf32_Word;

     typedef __u64 Elf64_Addr;//8 个字节
     typedef __u16 Elf64_Half;
     typedef __s16 Elf64_SHalf;
     typedef __u64 Elf64_Off;
     typedef __s32 Elf64_Sword;
     typedef __u32 Elf64_Word;
     typedef __u64 Elf64_Xword;
     typedef __s64 Elf64_Sxword;
     */

    /**
     * typedef struct elf32_rel {
     * Elf32_Addr r_offset;
     * Elf32_Word r_info;
     * } Elf32_Rel;
     */

    public static class elf32_rel {
        public byte[] r_offset = new byte[4];
        public byte[] r_info = new byte[4];

        @Override
        public String toString() {
            return "r_offset 的值为：" + Utils.byte2HexString(r_offset) + "\nr_info 的值为：" + Utils.byte2HexString(r_info);
        }
    }

    /**
     * typedef struct elf32_rela{
     * Elf32_Addr r_offset;
     * Elf32_Word r_info;
     * Elf32_Sword r_addend;
     * } Elf32_Rela;
     */
    public static class elf32_rela {
        public byte[] r_offset = new byte[4];
        public byte[] r_info = new byte[4];
        public byte[] r_addend = new byte[4];

        @Override
        public String toString() {
            return "r_offset 的值为：" + Utils.byte2HexString(r_offset) + "\n r_info 的值为：" + Utils.byte2HexString(r_info) + "\n r_addend 的值为：" + Utils.byte2HexString(r_addend);
        }
    }


    /**
     * typedef struct elf32_sym{
     * Elf32_Word st_name;
     * Elf32_Addr st_value;
     * Elf32_Word st_size;
     * unsigned char st_info;
     * unsigned char st_other;
     * Elf32_Half st_shndx;
     * } Elf32_Sym;
     */
    public static class elf32_sym {
        public byte[] st_name = new byte[4];
        public byte[] st_value = new byte[4];
        public byte[] st_size = new byte[4];
        public byte st_info;
        public byte st_other;
        public byte[] st_shndx = new byte[2];

        @Override
        public String toString() {
            return "st_name 的值为：" + Utils.byte2HexString(st_name)
                    + "\n st_value 的值为：" + Utils.byte2HexString(st_value)
                    + "\n st_size 的值为：" + Utils.byte2HexString(st_size)
                    + "\n st_info 的值为：" + Utils.byte2HexString(st_info)
                    + "\n st_other 的值为：" + Utils.byte2HexString(st_other)
                    + "\n st_shndx 的值为：" + Utils.byte2HexString(st_shndx);
        }
    }

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
     * <p>
     * ELF Header 头部信息
     */
    public static class elf32_hdr {
        public byte[] e_ident = new byte[16];
        public byte[] e_type = new byte[2];
        public byte[] e_machine = new byte[2];
        public byte[] e_version = new byte[4];
        public byte[] e_entry = new byte[4];
        public byte[] e_phoff = new byte[4];
        public byte[] e_shoff = new byte[4];
        public byte[] e_flags = new byte[4];
        public byte[] e_ehsize = new byte[2];
        public byte[] e_phentsize = new byte[2];
        public byte[] e_phnum = new byte[2];
        public byte[] e_shentsize = new byte[2];
        public byte[] e_shnum = new byte[2];
        public byte[] e_shstrndx = new byte[2];

        @Override
        public String toString() {
            return "e_ident 的值为：" + Utils.byte2HexString(e_ident)
                    + "\n e_type 的值为：" + Utils.byte2HexString(e_type)
                    + "\n e_machine 的值为：" + Utils.byte2HexString(e_machine)
                    + "\n e_version 的值为：" + Utils.byte2HexString(e_version)
                    + "\n e_entry 的值为：" + Utils.byte2HexString(e_entry)
                    + "\n e_phoff 的值为：" + Utils.byte2HexString(e_phoff)
                    + "\n e_shoff 的值为：" + Utils.byte2HexString(e_shoff)
                    + "\n e_flags 的值为：" + Utils.byte2HexString(e_flags)
                    + "\n e_ehsize 的值为：" + Utils.byte2HexString(e_ehsize)
                    + "\n e_phentsize 的值为：" + Utils.byte2HexString(e_phentsize)
                    + "\n e_phnum 的值为：" + Utils.byte2HexString(e_phnum)
                    + "\n e_shentsize 的值为：" + Utils.byte2HexString(e_shentsize)
                    + "\n e_shnum 的值为：" + Utils.byte2HexString(e_shnum)
                    + "\n e_shstrndx 的值为：" + Utils.byte2HexString(e_shstrndx);
        }

    }

    /**
     * Program Header 头部信息
     * <p>
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
     */

    public static class elf32_phdr {
        public byte[] p_type = new byte[4];
        public byte[] p_offset = new byte[4];
        public byte[] p_vaddr = new byte[4];
        public byte[] p_paddr = new byte[4];
        public byte[] p_filesz = new byte[4];
        public byte[] p_memsz = new byte[4];
        public byte[] p_flags = new byte[4];
        public byte[] p_align = new byte[4];

        @Override
        public String toString() {
            return "p_type 的值为：" + Utils.byte2HexString(p_type)
                    + "\n p_offset 的值为：" + Utils.byte2HexString(p_offset)
                    + "\n p_vaddr 的值为：" + Utils.byte2HexString(p_vaddr)
                    + "\n p_paddr 的值为：" + Utils.byte2HexString(p_paddr)
                    + "\n p_filesz 的值为：" + Utils.byte2HexString(p_filesz)
                    + "\n p_memsz 的值为：" + Utils.byte2HexString(p_memsz)
                    + "\n p_flags 的值为：" + Utils.byte2HexString(p_flags)
                    + "\n p_align 的值为：" + Utils.byte2HexString(p_align);
        }
    }


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
    public static class elf32_shdr {
        public byte[] sh_name = new byte[4];
        public byte[] sh_type = new byte[4];
        public byte[] sh_flags = new byte[4];
        public byte[] sh_addr = new byte[4];
        public byte[] sh_offset = new byte[4];
        public byte[] sh_size = new byte[4];
        public byte[] sh_link = new byte[4];
        public byte[] sh_info = new byte[4];
        public byte[] sh_addralign = new byte[4];
        public byte[] sh_entsize = new byte[4];

        @Override
        public String toString() {
            return "sh_name 的值为：" + Utils.byte2HexString(sh_name)
                    + "\n sh_type 的值为：" + Utils.byte2HexString(sh_type)
                    + "\n sh_flags 的值为：" + Utils.byte2HexString(sh_flags)
                    + "\n sh_addr 的值为：" + Utils.byte2HexString(sh_addr)
                    + "\n sh_offset 的值为：" + Utils.byte2HexString(sh_offset)
                    + "\n sh_size 的值为：" + Utils.byte2HexString(sh_size)
                    + "\n sh_link 的值为：" + Utils.byte2HexString(sh_link)
                    + "\n sh_info 的值为：" + Utils.byte2HexString(sh_info)
                    + "\n sh_addralign 的值为：" + Utils.byte2HexString(sh_addralign)
                    + "\n sh_entsize 的值为：" + Utils.byte2HexString(sh_entsize);
        }
    }

    /**
     * typedef struct elf32_note {
     * Elf32_Word n_namesz;
     * Elf32_Word n_descsz;
     * Elf32_Word n_type;
     * } Elf32_Nhdr;
     */
    public static class elf32_note {
        public byte[] n_namesz = new byte[4];
        public byte[] n_descsz = new byte[4];
        public byte[] n_type = new byte[4];

        @Override
        public String toString() {
            return "n_namesz 的值为：" + Utils.byte2HexString(n_namesz)
                    + "\n n_descsz 的值为：" + Utils.byte2HexString(n_descsz)
                    + "\n n_type 的值为：" + Utils.byte2HexString(n_type);
        }
    }


    public elf32_hdr hdr;// ELF 头部信息
    public List<elf32_phdr> phdrList = new ArrayList<>();//Program Header 有多个
    public List<elf32_shdr> shdrList = new ArrayList<>();//Section Header 有多个

    public ELFType32() {
        hdr = new elf32_hdr();
    }

    public void printPhdrList() {
        for (int i = 0; i < phdrList.size(); i++) {
            System.out.println();
            System.out.println("The " + (i + 1) + " Program Header:");
            System.out.println(phdrList.get(i).toString());
            System.out.println();
        }
    }

    public void printShdrList() {
        for (int i = 0; i < shdrList.size(); i++) {
            System.out.println();
            System.out.println("The " + (i + 1) + " Section Header:");
            System.out.println(shdrList.get(i).toString());
            System.out.println();
        }
    }
}
