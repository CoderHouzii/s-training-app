package songming.straing.app.socket.message;

import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.socket.MessageId;
import songming.straing.app.socket.SendSocketMessage;

/**
 * 注销
 */
public class LogoutMessageSend extends SendSocketMessage {


    @Override
    protected JSONObject content2JsonObj() throws JSONException {
        JSONObject object=new JSONObject();
        return object;
    }

    @Override
    protected int setMessageId() {
        return MessageId.SOCKET_LOGOUT;
    }

    @Override
    protected int setVersion() {
        return MessageId.VERSION;
    }
}
