package com.duing.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CapacityTest {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println("capacity: " + buf.capacity());
        for (int i = 0; i < 11; i++) {
            buf.writeByte(i);
        }
        System.out.println("capacity: " + buf.capacity());
        for (int i = 0; i < 65; i++) {
            buf.writeByte(i);
        }
        System.out.println("capacity: " + buf.capacity());
    }
}
