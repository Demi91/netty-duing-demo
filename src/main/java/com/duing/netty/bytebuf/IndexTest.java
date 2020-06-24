package com.duing.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class IndexTest {

    public static void main(String[] args) {

        ByteBuf buf = Unpooled.buffer();
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("writableBytes: " + buf.writableBytes());

        System.out.println("--------写入hello index");
        buf.writeBytes("hello index".getBytes());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("writableBytes: " + buf.writableBytes());
        System.out.println("readableBytes: " + buf.readableBytes());

        System.out.println("--------读取hello");
        for (int i = 0; i < 5; i++) {
            System.out.print((char)buf.readByte());
        }
        System.out.println();
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("writableBytes: " + buf.writableBytes());
        System.out.println("readableBytes: " + buf.readableBytes());


        System.out.println("--------读取index 并回退");
        buf.markReaderIndex();
//        buf.markWriterIndex();  写同理
        int end = buf.writerIndex();
        for (int i = buf.readerIndex(); i < end; i++) {
            System.out.print((char)buf.readByte());
        }
        System.out.println();
        // 撤回到mark标记的位置
        buf.resetReaderIndex();
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("writableBytes: " + buf.writableBytes());
        System.out.println("readableBytes: " + buf.readableBytes());



        // 回收可废弃空间
        buf.discardReadBytes();
        System.out.println("--------回收可废弃空间");
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("writableBytes: " + buf.writableBytes());
        System.out.println("readableBytes: " + buf.readableBytes());

        buf.clear();
        System.out.println("--------调用clear方法");
        System.out.println("capacity: " + buf.capacity());
        System.out.println("readerIndex: " + buf.readerIndex());
        System.out.println("writerIndex: " + buf.writerIndex());
        System.out.println("writableBytes: " + buf.writableBytes());
        System.out.println("readableBytes: " + buf.readableBytes());

    }
}
