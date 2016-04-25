package songming.straing.app.eventbus;

import songming.straing.app.socket.SocketMessage;

/**
 * eventbus专属类
 */
public final class Events {

    //============================================================= socket相关↓↓↓

    /**
     * 连接事件
     */
    public static class ConnectEvent{

    }

    /**
     * 断开连接事件
     */
    public static class DisconnectEvent{

    }

    /**
     * 网络变化
     */
    public static class NetWorkChangeEvent{

    }

    /**
     * 开始心跳
     */
    public static class StartHeart{

    }

    /**
     * socket包接收事件
     */
    public static class ReceivedEvent{
        private SocketMessage mMessage;

        public ReceivedEvent(SocketMessage message) {
            mMessage = message;
        }

        public SocketMessage getMessage() {
            return mMessage;
        }

        public void setMessage(SocketMessage message) {
            mMessage = message;
        }
    }

    /**
     * 跳到主页
     */
    public static class StartToMainEvent{

    }

    /**
     * 跳到登录
     */
    public static class StartToLoginEvent{

    }

}
