package songming.straing.app.socket;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.CharsetEncoder;

import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.model.ChatReceiverInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.UIHelper;

/**
 * socket消息分发类
 */
public enum  SocketMessageManager {
    INSTANCE;


    // 信息返回
    @Subscribe
    public void onEvent(Events.ReceivedEvent event) {
        SocketMessage msg=event.getMessage();
        switch (msg.getMessageid()){
            //登录失败
            case MessageId.SOCKET_LOGIN_FAILED:
                EventBus.getDefault().post(new Events.StartToLoginEvent());
                break;
            //登陆成功
            case MessageId.SOCKET_LOGIN_SUCCESS:
                EventBus.getDefault().post(new Events.StartToMainEvent());
                EventBus.getDefault().post(new Events.StartHeart());
                EventBus.getDefault().post(new Events.RefreshDataEvent());
                break;
            //登出成功
            case MessageId.SOCKET_LOGOUT_SUCCESS:
                EventBus.getDefault().post(new Events.StartToLoginEvent());
                LocalHost.INSTANCE.setKey("null");
                LocalHost.INSTANCE.setUserName("null");
                LocalHost.INSTANCE.setUserId(0);
                LocalHost.INSTANCE.setUserAvatar("null");
                LocalHost.INSTANCE.setHasLogin(false);
                break;
            //个人聊天消息接收
            case MessageId.SOCKET_F_CHAT_RECEIVE:
                String resolveString=msg.getJsonString().replace("\\","");
                resolveString= resolveString.substring(1, resolveString.length()-1);
                ChatReceiverInfo info= JSONUtil.toObject(resolveString, ChatReceiverInfo.class);
                EventBus.getDefault().post(new Events.PersonChatEvent(info));

                break;
            //群聊天消息接收
            case MessageId.SOCKET_G_CHAT_RECEIVE:
                String receivemsg=msg.getJsonString().replace("\\","");
                receivemsg= receivemsg.substring(1, receivemsg.length()-1);
                ChatReceiverInfo groupInfo= JSONUtil.toObject(receivemsg, ChatReceiverInfo.class);
                EventBus.getDefault().post(new Events.GroupChatEvent(groupInfo));

                break;
            default:
                break;
        }
    }


    public void init(){
        EventBus.getDefault().register(this);
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
