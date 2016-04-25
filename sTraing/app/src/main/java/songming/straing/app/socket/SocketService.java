package songming.straing.app.socket;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.app.config.Config.SocketIntent;
import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.app.socket.message.LoginMessageSend;
import songming.straing.app.thread.ThreadPoolManager;

/**
 * socket服务
 */
public class SocketService extends Service {

    private SocketHeart heartManager = null;

    public SocketService() {
        heartManager = new SocketHeart();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);

        int type = intent.getIntExtra(SocketIntent.TYPE, -1);

        switch (type) {
            case SocketIntent.Types.CONNECT:
                connect();
                break;
            case SocketIntent.Types.DISCONNECT:
                disconnect();
                break;
            case SocketIntent.Types.SEND:
                send(intent.getByteArrayExtra(SocketIntent.PACKET));
                break;
            default:
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void send(final byte[] data) {
        ThreadPoolManager.execute(new Runnable() {

            @Override
            public void run() {
                SocketClient.INSTANCE.send(data);
            }
        });
    }

    private void connect() {
        heartManager.stopHeart();
        ThreadPoolManager.execute(connecntRunnable);
    }

    private Runnable connecntRunnable = new Runnable() {
        @Override
        public void run() {
            if (heartManager.isRuning()) {
                heartManager.stopHeart();
            }
            boolean isConn = false;

            if (SocketClient.INSTANCE.isConnected()) SocketClient.INSTANCE.disconnect();

            isConn = SocketClient.INSTANCE.connect();

            if (isConn) {
                KLog.i("socket", "连接成功");
            }
            else {
                KLog.d("socket", "连接失败，5秒后重试");
                SystemClock.sleep(5000);
                connect();
            }
        }
    };

    private void disconnect() {
        heartManager.stopHeart();
        ThreadPoolManager.execute(new Runnable() {

            @Override
            public void run() {
                // 本地自动断开socket连接
                SocketClient.INSTANCE.disconnect();
            }
        });
    }

    private static Intent newIntnet(Context context) {
        Intent intent = new Intent(context, SocketService.class);
        return intent;
    }

    public static void startService(Context context) {
        Intent intent = newIntnet(context);
        context.startService(intent);
    }

    public static void CallService(Context context, int type) {
        Intent intent = newIntnet(context);
        intent.putExtra(SocketIntent.TYPE, type);
        context.startService(intent);
    }

    public static void CallServiceSend(Context context, byte[] packet) {
        Intent intent = newIntnet(context);
        intent.putExtra(SocketIntent.TYPE, SocketIntent.Types.SEND);
        intent.putExtra(SocketIntent.PACKET, packet);
        context.startService(intent);
    }

    public static void stopService(Context context) {
        Intent intent = newIntnet(context);
        context.stopService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Subscribe
    //网络改变
    public void onEvent(final Events.NetWorkChangeEvent event) {
        connect();
    }

    @Subscribe
    // 网络连接成功
    public void onEvent(Events.ConnectEvent event) {

        sendLoginPacket();
        KLog.i("socket", "连接成功");
    }

    @Subscribe
    // 网络断开连接
    public void onEvent(Events.DisconnectEvent event) {
        KLog.i("socket", "断开连接");

        if (heartManager.isRuning())    //非手动断开连接的情况就立即自动重连
        {
            connect();
        }
    }

    @Subscribe
    // 开始心跳
    public void onEvent(Events.StartHeart event) {
        heartManager.startHeart();
    }

    // 发送登陆包
    private void sendLoginPacket() {
        if (!LocalHost.INSTANCE.getKey().equals("null")) {
            LoginMessageSend msg = new LoginMessageSend();
            msg.key = LocalHost.INSTANCE.getKey();
            send(msg.getMessageData());
            KLog.d("socket", "正在自动登录");
        }
    }

    @Subscribe
    // 心跳失败
    public void onEvent(SocketHeart.HeartFailedEvent evt) {
        // 重新连接并登陆
        connect();
    }
}

