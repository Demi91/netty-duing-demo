package com.duing.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CopyTest {

    public static void main(String[] args) {

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes("hello bytebuf copy".getBytes());
        System.out.println("----------初始化bytebuf");
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());

        // 浅复制 / 浅拷贝
        ByteBuf newBuf = buf.duplicate();
        System.out.println("----------duplicate newBuf");
        System.out.println("newBuf capacity: " + newBuf.capacity());
        System.out.println("newBuf readerIndex: " + newBuf.readerIndex());
        System.out.println("newBuf writerIndex: " + newBuf.writerIndex());

        newBuf.writeBytes(" from newBuf".getBytes());
        System.out.println("----------duplicate add data");
        System.out.println("newBuf writerIndex: " + newBuf.writerIndex());

        // 如果读取大小 超过写索引  会报错IndexOutOfBoundsException
        buf.writerIndex(30);
        for (int i = 0; i < 13; i++) {
            System.out.print((char)buf.readByte());
        }
        System.out.println();
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());

        // 部分浅拷贝  切片的意思
        // 拷贝的区间是 readerIndex - writerIndex之间的区域
        // 只可读  不可写
        // 切片的容量就是原可读区域的大小 writerIndex - readerIndex的值
        ByteBuf sliceBuf = buf.slice();
        // 写数据会报错  IndexOutOfBoundsException
//        sliceBuf.writeBytes(" sth".getBytes());
        System.out.println("-----------sliceBuf");
        System.out.println("capacity: " + sliceBuf.capacity());
        System.out.println("readerIndex: " + sliceBuf.readerIndex());
        System.out.println("writerIndex: " + sliceBuf.writerIndex());

        // 深复制
        ByteBuf copyBuf = buf.copy();
        System.out.println("-----------copyBuf");
        System.out.println("capacity: " + copyBuf.capacity());
        System.out.println("readerIndex: " + copyBuf.readerIndex());
        System.out.println("writerIndex: " + copyBuf.writerIndex());

        copyBuf.writeBytes(" from copyBuf".getBytes());
        copyBuf.writerIndex(43);
        for (int i = copyBuf.readerIndex(); i < 43; i++) {
            System.out.print((char)copyBuf.readByte());
        }
        System.out.println();

        System.out.println("-----------原buf");
        buf.writerIndex(43);
        for (int i = buf.readerIndex(); i < 43; i++) {
            System.out.print((char)buf.readByte());
        }


    }
}
