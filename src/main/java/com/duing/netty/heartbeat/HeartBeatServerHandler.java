package com.duing.netty.heartbeat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class HeartBeatServerHandler extends SimpleChannelInboundHandler<String> {

    private int times = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("clent data: " + msg);
        if ("I am alive".equals(msg)) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("over", CharsetUtil.UTF_8));
        }
    }


    // 处理心跳检测事件的方法
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;

        String eventDesc = null;
        switch (event.state()) {
            case READER_IDLE:
                eventDesc = "读空闲";
                break;
            case WRITER_IDLE:
                eventDesc = "写空闲";
                break;
            case ALL_IDLE:
                eventDesc = "读写空闲";
                break;
        }

        System.out.println(ctx.channel().remoteAddress() + " 发生超时事件 -- " + eventDesc);
        times++;

        if (times > 3) {
            System.out.println("空闲次数超过3次  关闭连接");
            ctx.writeAndFlush("you are out");
            ctx.channel().close();
        }

//        super.userEventTriggered(ctx, evt);
    }
}
