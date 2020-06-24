package com.duing.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.Iterator;

public class TypeTest {

    public static void main(String[] args) {

        ByteBuf buf = Unpooled.copiedBuffer("hello bytebuf", CharsetUtil.UTF_8);
        // 堆内存
        if(buf.hasArray()){
            System.out.println("堆内存缓冲区");
            System.out.println(new String(buf.array()));
        }


        ByteBuf buf1 = Unpooled.buffer();
        ByteBuf buf2 = Unpooled.directBuffer();

        CompositeByteBuf buf3 = Unpooled.compositeBuffer();
        buf3.addComponents(buf1,buf2);

//        buf3.removeComponent(0);

        Iterator<ByteBuf> iterator = buf3.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }
}
