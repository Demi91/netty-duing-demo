package com.duing.protobuf;

public class ProtobufTest {


    public static void main(String[] args) throws Exception {
        // 建造者模式 创建对象
        PersonModel.Person.Builder builder = PersonModel.Person.newBuilder();

        builder.setId(3);
        builder.setName("Andy姐");

        PersonModel.Person person = builder.build();
        System.out.println(person);

        System.out.println("=====person bytes:");
        for(byte b : person.toByteArray()){
            System.out.print(b);
        }
        System.out.println();

        System.out.println("====================");

        byte[] byteArr = person.toByteArray();
        // 使用parseFrom方法  反向构造对象
        PersonModel.Person  person2 = PersonModel.Person.parseFrom(byteArr);
        System.out.println(person2.getId());
        System.out.println(person2.getName());

    }
}
