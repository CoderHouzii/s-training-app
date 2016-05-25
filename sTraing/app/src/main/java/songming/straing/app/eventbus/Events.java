package songming.straing.app.eventbus;

import songming.straing.app.socket.SocketMessage;
import songming.straing.model.ChatReceiverInfo;

/**
 * eventbus专属类
 */
public final class Events {

    //============================================================= socket相关↓↓↓

    /**
     * 连接事件
     */
    public static class ConnectEvent {

    }

    /**
     * 断开连接事件
     */
    public static class DisconnectEvent {

    }

    /**
     * 网络变化
     */
    public static class NetWorkChangeEvent {

    }

    /**
     * 开始心跳
     */
    public static class StartHeart {

    }

    /**
     * socket包接收事件
     */
    public static class ReceivedEvent {
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
    public static class StartToMainEvent {

    }

    /**
     * 跳到登录
     */
    public static class StartToLoginEvent {

    }

    /**
     * 刷新头像
     */
    public static class RefreshAvatarEvent {

    }

    /**
     * 通知更新数据
     */
    public static class RefreshDataEvent {

    }

    /**
     * 跳转到fragment
     */
    public static class ChangeToFragment {
        public static final int FRAG_INDEX = 0x10;
        public static final int FRAG_FRIENDS = 0x11;
        public static final int FRAG_MISSION = 0x12;
        public static final int FRAG_MESSAGE = 0x13;
        public static final int FRAG_ME = 0x14;

        private int fragIndex;

        public ChangeToFragment(int fragIndex) {
            this.fragIndex = fragIndex;
        }

        public int getFragIndex() {
            return fragIndex;
        }

        public void setFragIndex(int fragIndex) {
            this.fragIndex = fragIndex;
        }
    }

    /**
     * 更新首页的数据
     */
    public static class RefreshMissionDetail {
    }


    /**
     * 个人聊天
     */
    public static class PersonChatEvent {

        private ChatReceiverInfo chatReceiverInfo;

        public ChatReceiverInfo getChatReceiverInfo() {
            return chatReceiverInfo;
        }

        public void setChatReceiverInfo(ChatReceiverInfo chatReceiverInfo) {
            this.chatReceiverInfo = chatReceiverInfo;
        }

        public PersonChatEvent(ChatReceiverInfo chatReceiverInfo) {
            this.chatReceiverInfo = chatReceiverInfo;
        }
    }

    /**
     * 群组聊天
     */
    public static class GroupChatEvent {

        private ChatReceiverInfo chatReceiverInfo;

        public ChatReceiverInfo getChatReceiverInfo() {
            return chatReceiverInfo;
        }

        public void setChatReceiverInfo(ChatReceiverInfo chatReceiverInfo) {
            this.chatReceiverInfo = chatReceiverInfo;
        }

        public GroupChatEvent(ChatReceiverInfo chatReceiverInfo) {
            this.chatReceiverInfo = chatReceiverInfo;
        }
    }
    /**
     * 更新好友的数据
     */
    public static class RefreshFriendEvent {
    }
}
