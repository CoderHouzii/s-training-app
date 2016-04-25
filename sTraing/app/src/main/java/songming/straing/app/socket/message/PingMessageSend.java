package songming.straing.app.socket.message;

import org.json.JSONObject;
import songming.straing.app.socket.MessageId;
import songming.straing.app.socket.SendSocketMessage;

/**
 * 心跳空数据包
 */
public class PingMessageSend extends SendSocketMessage {
    @Override
    protected JSONObject content2JsonObj() {
        return new JSONObject();
    }

    @Override
    protected int setMessageId() {
        return MessageId.SOCKET_HEART;
    }

    @Override
    protected int setVersion() {
        return MessageId.VERSION;
    }
}
