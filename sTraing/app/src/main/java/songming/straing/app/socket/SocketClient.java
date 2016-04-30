package songming.straing.app.socket;

import com.socks.library.KLog;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.greenrobot.eventbus.EventBus;
import songming.straing.app.STraingApp;
import songming.straing.app.config.Config;
import songming.straing.utils.NetworkUtils;

/**
 * socket，单例，全局使用
 * 使用netty框架
 */
public enum SocketClient {
    INSTANCE;
    private static final String TAG = "SocketClient";

    private static final int PORT = 7400;

    private Channel mChannel = null;
    // 线程组
    private EventLoopGroup group = null;

    private Bootstrap configureBootstrap() {

        Bootstrap boot = new Bootstrap();
        group = new NioEventLoopGroup();
        boot.group(group)
            .channel(NioSocketChannel.class)
            .remoteAddress(Config.SOCKET_HOST, PORT)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new SocketClientInitialize());
        return boot;
    }

    public synchronized boolean isConnected() {
        if (!NetworkUtils.isNetworkConnected(STraingApp.appContext)) {
            KLog.d("网络没有连接");
            return false;
        }
        if (mChannel != null && mChannel.isOpen()) {
            return true;
        }
        if (mChannel == null) {
            KLog.e(TAG, "channel == null");
        }
        else if (!mChannel.isOpen()) {
            KLog.e(TAG, "channel is not open");
        }
        return false;
    }

    public synchronized boolean send(byte[] data) {
        if (isConnected()) {
            mChannel.writeAndFlush(data);
            return true;
        }
        return false;
    }

    public synchronized boolean connect() {
        if (!NetworkUtils.isNetworkConnected(STraingApp.appContext)) // 网络断开的情况
        {
            return false;
        }

        try {
            KLog.d(TAG, "开始连接到服务器");
            ChannelFuture channelFuture = configureBootstrap().connect().sync();
            if (channelFuture != null && channelFuture.isSuccess()) {
                mChannel = channelFuture.channel();
                KLog.d(TAG, "成功连接到服务器");
            }
        } catch (Exception e) {
            e.printStackTrace();
            KLog.e(TAG, "连接失败，打印的异常为>>>>>>>  " + e.toString());
            return false;
        }

        return true;
    }

    public synchronized void disconnect() {
        KLog.d("socket","手动disconnect");
        try {
            if (mChannel != null) {
                mChannel.close();
                mChannel = null;
            }
            if (group != null) {
                group.shutdownGracefully().sync();
                group = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long lastRecvPacketTime = 0; // 最后收包时间

    public long getLastRecvPacketTime() {
        return lastRecvPacketTime;
    }

    public void setLastRecvPacketTime(long lastRecvPacketTime) {
        this.lastRecvPacketTime = lastRecvPacketTime;
    }

}
