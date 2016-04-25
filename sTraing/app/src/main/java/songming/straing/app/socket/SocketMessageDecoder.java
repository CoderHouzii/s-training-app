package songming.straing.app.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * 解析器
 */
public class SocketMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < SendSocketMessage.HEAD_LENGHT) {
            return;
        }
        SocketMessage msg=new SocketMessage();
       /* //标记当前的readIndex的位置
        in.markReaderIndex();

        //读取消息长度
        int packLength = in.readInt();
        //长度小于0，关闭连接
        if (packLength<0){
            ctx.close();
        }
        //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex.
        //这个配合markReaderIndex使用，把readIndex重置到mark的地方
        if (in.readableBytes() < packLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] decoded = new byte[packLength];
        in.readBytes(decoded);
*/
        //标记当前的readIndex的位置
        in.markReaderIndex();

        int contentlength=in.readInt();
        int messageid=in.readInt();
        int version=in.readInt();

        if (in.readableBytes()<contentlength){
            in.resetReaderIndex();
            return;
        }
        byte[] content=new byte[contentlength];
        in.readBytes(content);

        msg.setMessageid(messageid);
        msg.setVersion(version);
        msg.setJsonString(new String(content));

        out.add(msg);
    }

}
