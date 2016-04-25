package songming.straing.app.socket;

/**
 * socket动作监听
 */
public interface OnSockeActionListener {
    void onConnect();
    void onDisconnect();
    void onReceived(byte[] data);
}
