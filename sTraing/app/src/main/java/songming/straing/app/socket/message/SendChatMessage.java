package songming.straing.app.socket.message;

import org.json.JSONException;
import org.json.JSONObject;

import songming.straing.app.socket.MessageId;
import songming.straing.app.socket.SendSocketMessage;

/**
 * 个人聊天消息发送
 */
public class SendChatMessage extends SendSocketMessage{
    private String key;
    private long sid;
    private long rid;
    private long create_at;
    private String text;

    public SendChatMessage(String key, long sid, long rid, String text) {
        this.key = key;
        this.sid = sid;
        this.rid = rid;
        this.text = text;
    }

    @Override
    protected JSONObject content2JsonObj() throws JSONException {
        JSONObject object=new JSONObject();
        object.put("key",key);
        object.put("sid",sid);
        object.put("rid",rid);
        object.put("text",text);
        return object;
    }

    @Override
    protected int setMessageId() {
        return MessageId.SOCKET_F_CHAT_SEND;
    }

    @Override
    protected int setVersion() {
        return MessageId.VERSION;
    }
}
