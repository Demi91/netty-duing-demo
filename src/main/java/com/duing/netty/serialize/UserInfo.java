package com.duing.netty.serialize;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7607690012569645441L;

    private int ID;
    private String name;

    public UserInfo(int ID, String name) {
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
    public byte[] codec() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

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
        UserInfo userInfo = new UserInfo(1, "Andy姐");
        // 原生序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(userInfo);
        oos.flush();
        oos.close();

        byte[] arr = bos.toByteArray();
        System.out.println("JDK序列化后 字节数组的长度：" + arr.length);
        bos.close();

        System.out.println("ByteBuffer转化为字节数组形式的长度："
                + userInfo.codec().length);
    }

}
