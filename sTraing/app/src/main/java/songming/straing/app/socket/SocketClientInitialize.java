package songming.straing.app.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 初始化socket handler时，绑定解析器和编码器
 */
public class SocketClientInitialize extends ChannelInitializer<SocketChannel>{


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new SocketMessageDecoder());
        pipeline.addLast("encoder",new SocketMessageEncoder());
        pipeline.addLast("handler", new SocketHandler());
    }
}
