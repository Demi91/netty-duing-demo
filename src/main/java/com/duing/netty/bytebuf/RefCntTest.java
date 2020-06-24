package com.duing.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class RefCntTest {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);

        System.out.println(buf);

        // 引用计数的值
        System.out.println(buf.refCnt());
        // 保持的意思  计数+1
        buf.retain();
        System.out.println(buf.refCnt());
        // 释放的意思  计数-1
        buf.release();
        System.out.println(buf.refCnt());

        buf.release();
        System.out.println(buf);
    }
}
