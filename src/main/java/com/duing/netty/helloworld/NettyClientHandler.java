package com.duing.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("channelActive done");
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server,I am client", CharsetUtil.UTF_8));
//        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead done");
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("server msg:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("server address: " + ctx.channel().remoteAddress());
//        super.channelRead(ctx, msg);
    }
}
