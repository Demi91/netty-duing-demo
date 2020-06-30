package com.duing.netty.serialize;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo2 implements Serializable {

    private static final long serialVersionUID = -253661239306911857L;

    private int ID;
    private String name;

    public UserInfo2(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 根据buffer缓冲区  模拟的序列化操作  获得字节数组的结果
    public byte[] codec(ByteBuffer buffer) {
        buffer.clear();

        // 存储name数据
        byte[] value = this.name.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        // 存储ID数据
        buffer.putInt(this.ID);
        // 写完成
        buffer.flip();
        value = null;
        // 读取并存储到result中
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);

        return result;
    }


    public static void main(String[] args) throws Exception {
        UserInfo2 userInfo2 = new UserInfo2(1, "Andy姐");
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        long startTime = System.currentTimeMillis();
        // 循环1000000次
        // 原生序列化
        for (int i = 0; i < 1000000; i++) {
            bos =  new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(userInfo2);
            oos.flush();
            oos.close();

            byte[] arr = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JDK序列化 耗时：" + (endTime - startTime) + "ms");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            userInfo2.codec(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("ByteBuffer转化 耗时：" + (endTime - startTime) + "ms");
    }

}
