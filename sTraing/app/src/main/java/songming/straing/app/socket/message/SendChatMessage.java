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
    private String sid;
    private String rid;
    private String create_at;
    private String text;

    public SendChatMessage(String key, long sid, long rid, String text) {
        this.key = key;
        this.sid = String.valueOf(sid);
        this.rid = String.valueOf(rid);
        this.text = text;
        this.create_at= String.valueOf(System.currentTimeMillis());

    }

    @Override
    protected JSONObject content2JsonObj() throws JSONException {
        JSONObject object=new JSONObject();
        object.put("key",key);
        object.put("sid",sid);
        object.put("rid",rid);
        object.put("create_at",create_at);
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
