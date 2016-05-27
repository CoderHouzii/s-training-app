package songming.straing.app.socket.message;

import org.json.JSONException;
import org.json.JSONObject;

import songming.straing.app.socket.MessageId;
import songming.straing.app.socket.SendSocketMessage;

/**
 * 群聊天消息发送
 */
public class SendGroupChatMessage extends SendSocketMessage{
    private String key;
    private String sid;
    private String rgid;
    private String create_at;
    private String text;

    public SendGroupChatMessage(String key, long sid, long rgid, String text) {
        this.key = key;
        this.sid = String.valueOf(sid);
        this.rgid = String.valueOf(rgid);
        this.text = text;
        this.create_at= String.valueOf(System.currentTimeMillis());

    }

    @Override
    protected JSONObject content2JsonObj() throws JSONException {
        JSONObject object=new JSONObject();
        object.put("key",key);
        object.put("sid",sid);
        object.put("rgid",rgid);
        object.put("create_at",create_at);
        object.put("text",text);
        return object;
    }

    @Override
    protected int setMessageId() {
        return MessageId.SOCKET_G_CHAT_SEND;
    }

    @Override
    protected int setVersion() {
        return MessageId.VERSION;
    }
}
