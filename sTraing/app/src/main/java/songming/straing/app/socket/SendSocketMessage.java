package songming.straing.app.socket;

import com.socks.library.KLog;
import java.nio.ByteBuffer;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * socket消息类（用于构建发送的包）
 */
public abstract class SendSocketMessage {
    //包头
    //内容长度（4位）＋协议号（4位）＋版本号（4位）(左边为包头)/+内容（json）
    public static final int HEAD_LENGHT = 12;

    //nt(4)	21003
    private int messageID;
    //int(4)	0
    private int version;
    //String	{}
    private String packContent;

    private int contentLength;


    //因发送为jsonobj所以由子类完成json obj的定制
    private JSONObject mJSONObject;

    protected abstract JSONObject content2JsonObj() throws JSONException;

    ByteBuffer mByteBuffer;
    public byte[] getMessageData(){
        byte[] packet = null;
        try {
            JSONObject jsonObj = this.content2JsonObj();

            packContent = jsonObj.toString();
            byte[] content = packContent.getBytes();

            contentLength = content.length;

            mByteBuffer=ByteBuffer.allocate(HEAD_LENGHT+contentLength);

            messageID=setMessageId();
            version=setVersion();

            mByteBuffer.putInt(contentLength);
            mByteBuffer.putInt(messageID);
            mByteBuffer.putInt(version);
            mByteBuffer.put(content);

            packet=mByteBuffer.array();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
    }


    protected abstract int setMessageId();
    protected abstract int setVersion();
}
