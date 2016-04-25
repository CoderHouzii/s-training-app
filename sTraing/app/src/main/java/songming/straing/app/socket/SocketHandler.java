package songming.straing.app.socket;

import com.socks.library.KLog;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.greenrobot.eventbus.EventBus;
import songming.straing.app.eventbus.Events;

/**
 *
 */
public class SocketHandler extends SimpleChannelInboundHandler<SocketMessage> {
    private static final String TAG = "SocketHandler";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        EventBus.getDefault().post(new Events.ConnectEvent());
        KLog.d(TAG, "成功连接到: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        EventBus.getDefault().post(new Events.DisconnectEvent());
        KLog.d(TAG, "断开了连接：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        KLog.e(TAG, "服务器异常" + ctx.channel().remoteAddress());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, SocketMessage msg) throws Exception {
        KLog.d("socket", "收到消息");
        if (msg != null) {
            KLog.d("socket_message", "msgId = " + msg.getMessageid() + "     msg = " + msg.getJsonString());
            if (msg.getMessageid() == MessageId.SOCKET_HEART_SUCCESS) {
                KLog.d("socket", "心跳正常");
            }
        }
        SocketClient.INSTANCE.setLastRecvPacketTime(System.currentTimeMillis());
        EventBus.getDefault().post(new Events.ReceivedEvent(msg));
    }
}
