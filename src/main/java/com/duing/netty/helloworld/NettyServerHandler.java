package com.duing.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

// 自定义handler的方式之一
//    继承ChannelInboundHandlerAdapter
//       提供了在不同时期会触发的方法
//    channelActive   channelRead   channelReadComplete
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded done");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved done");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered done");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered done");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive done");
    }

    // ctrl + o 实现父类的方法

    //  active 活跃的意思
    //  通道被启用   刚刚建立连接要使用的方法
    //    业务使用中  往往发送欢迎消息
    //  ChannelHandlerContext  是通道处理器的上下文
    //    可以整合使用过程中所需的参数  比如通道channel 管道pipeline 写数据等等
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 写数据时  可以调用writeAndFlush 直接写入字符串
        // 底层还是封装了  获取通道 - 创建缓冲区 - 写入数据 - 缓冲区写入通道 等流程
        System.out.println("channelActive done");
//        ctx.writeAndFlush("Welcome to Netty Server");
//        super.channelActive(ctx);
    }

    // 数据读取的方法
    // 当客户端发送消息时   读事件触发的方法
    // Object msg  就是传送的消息数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // netty中的缓冲区  叫做ByteBuf  -- 对ByteBuffer的封装
        ByteBuf buf = (ByteBuf) msg;
        // 直接设定编码格式 CharsetUtil.UTF_8   其中CharsetUtil是netty提供的编码格式工具类
        System.out.println("client msg : " + buf.toString(CharsetUtil.UTF_8));
        // 可以定位到客户端的远程地址  通过 通道的remoteAddress方法
        System.out.println("client is from " + ctx.channel().remoteAddress());

        // 释放ByteBuf内存
        ReferenceCountUtil.release(msg);
//        super.channelRead(ctx, msg);
    }

    // 数据读取完成触发的方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 数据的处理还是使用ByteBuf  Unpooled是提供 在ByteBuf和string之间方便转换的工具类
        //  Unpooled的常用方法  copiedBuffer 直接处理String返回ByteBuf
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client , How do you do?",CharsetUtil.UTF_8));
//        super.channelReadComplete(ctx);
    }
}
