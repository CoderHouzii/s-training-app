package songming.straing.app.socket;

/**
 * socket消息id
 */
public class MessageId {
    public static final int VERSION=1;

    /**============================================================= 用户登录*/
    // 请求
    public static final int SOCKET_LOGIN=21002;
    // 登陆成功
    public static final int SOCKET_LOGIN_SUCCESS=22002;
    // 登录失败
    public static final int SOCKET_LOGIN_FAILED=23002;

    /**============================================================= 用户注销登录*/
    // 请求
    public static final int SOCKET_LOGOUT=21006;
    // 注销成功
    public static final int SOCKET_LOGOUT_SUCCESS=22006;
    // 注销失败
    public static final int SOCKET_LOGOUT_FAILED=23006;

    /**============================================================= 心跳*/
    // 请求 (连续请求3次无响应视为失败)
    public static final int SOCKET_HEART=21003;
    // 心跳成功
    public static final int SOCKET_HEART_SUCCESS=22003;
    // 心跳失败
    public static final int SOCKET_HEART_FAILED=23003;

    /**============================================================= 好友聊天*/
    // 发送
    public static final int SOCKET_F_CHAT_SEND=21007;
    // 接收
    public static final int SOCKET_F_CHAT_RECEIVE=21008;

    /**============================================================= 群聊天*/
    // 发送
    public static final int SOCKET_G_CHAT_SEND=21009;
    // 接收
    public static final int SOCKET_G_CHAT_RECEIVE=21010;

    /**============================================================= 强制下线*/
    public static final int SOCKET_EXIT=21005;







}
