package songming.straing.app.socket;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.app.eventbus.Events;
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
