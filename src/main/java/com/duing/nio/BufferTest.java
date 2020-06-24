package com.duing.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class BufferTest {

    public static void main(String[] args) {

//        CharBuffer buffer = CharBuffer.allocate(8);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        System.out.println("capacity:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());
        System.out.println("position:" + buffer.position());

//        buffer.put('y');
//        buffer.put('u');
        buffer.put("yu".getBytes());
        System.out.println("=========存入y&u");
        System.out.println("position:" + buffer.position());


        buffer.flip();
        System.out.println("=========调用flip");
        System.out.println("limit:" + buffer.limit());
        System.out.println("position:" + buffer.position());


        System.out.println("=========读取数据");
        // 不传参  代表获取第一个   传参代表指定索引位置
        // 当传参有索引时  position不变化
        System.out.println(buffer.get());
        System.out.println("position:" + buffer.position());

        // 标记  存储当前的位置position
        buffer.mark();

        System.out.println(buffer.get());
        System.out.println("position:" + buffer.position());

        // 回退  退回到mark记录的位置
        buffer.reset();
        System.out.println("=========调用reset");
        System.out.println("position:" + buffer.position());


//        System.out.println(buffer.get(1));
//        System.out.println("position:" + buffer.position());

        // buffer的遍历方式
//        while (buffer.hasRemaining()){
//            System.out.println(buffer.get());
//        }


        // clear清空的是索引位置  对象依然存在
        buffer.clear();
        System.out.println("=========调用clear");
        System.out.println("limit:" + buffer.limit());
        System.out.println("position:" + buffer.position());




    }
}
