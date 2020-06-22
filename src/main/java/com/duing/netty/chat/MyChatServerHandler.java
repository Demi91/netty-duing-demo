package com.duing.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Date;
import java.util.Iterator;

// 处理器的另一种实现方式
// SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter的子类
//  在父类的基础上  封装了channelRead0方法  本质上也是处理 读数据逻辑的方法
public class MyChatServerHandler extends SimpleChannelInboundHandler {

    // 当多个通道传入handler  使用通道组的管理方式
    // GlobalEventExecutor是一个全局事件执行器   单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 刚刚建立连接时  第一个被执行的方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("[服务器] - " + ctx.channel().remoteAddress() + "连接成功\r\n");
        channelGroup.add(ctx.channel());
//        super.handlerAdded(ctx);
    }


    // 当连接被移除或者被断开  最后会执行的方法
    //  此处会自动将channel从channel组中移除
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("[服务器] - " + ctx.channel().remoteAddress() + "断开连接");
//        super.handlerRemoved(ctx);
    }


    // 读数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 广播给其他客户端
        Channel selfChannel = ctx.channel();
        Iterator<Channel> iterator = channelGroup.iterator();
        while (iterator.hasNext()) {
            Channel channel = iterator.next();

            // 广播给其他人
            if (selfChannel != channel) {
                channel.writeAndFlush("[服务器] - " + selfChannel.remoteAddress()
                        + "发送消息：" + msg + "\n");
                continue;
            }

            // 如果是本通道  返回一个回答
            String answer;
            if (((String) msg).length() == 0) {
                answer = "Please say something \r\n";
            } else {
                answer = "Did you say " + msg + " ?\r\n";
            }
            channel.writeAndFlush(answer);

        }

    }


    // 连接成功  此时通道是活跃的
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to server\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
//        super.channelActive(ctx);
    }


    // 通道不活跃的方法  处理用户下线等业务逻辑
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线了");
//        super.channelInactive(ctx);
    }


    // 通用异常处理方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭上下文 即通道
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
