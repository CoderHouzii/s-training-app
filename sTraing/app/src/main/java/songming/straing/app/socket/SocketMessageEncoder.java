package songming.straing.app.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * socket编码器
 */
public class SocketMessageEncoder extends MessageToByteEncoder<byte[]>{
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
        out.writeBytes(msg);
    }
}
