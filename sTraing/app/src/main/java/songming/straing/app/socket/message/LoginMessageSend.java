package songming.straing.app.socket.message;

import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.socket.MessageId;
import songming.straing.app.socket.SendSocketMessage;

/**
 * 登录包
 */
public class LoginMessageSend extends SendSocketMessage {
    public String key;


    @Override
    protected JSONObject content2JsonObj() throws JSONException {
        JSONObject object=new JSONObject();
        object.put("key",key);
        return object;
    }

    @Override
    protected int setMessageId() {
        return MessageId.SOCKET_LOGIN;
    }

    @Override
    protected int setVersion() {
        return MessageId.VERSION;
    }
}
