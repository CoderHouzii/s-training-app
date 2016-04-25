package songming.straing.app.socket;

import com.socks.library.KLog;
import java.util.Timer;
import java.util.TimerTask;
import org.greenrobot.eventbus.EventBus;
import songming.straing.app.socket.message.PingMessageSend;

/**
 * 心跳
 */
public class SocketHeart {

    private static final String TAG = "SocketHeart";

    private TimerTask task;
    private Timer heartTask;

    private final int HEART_TIME = 20 * 1000;// 每20秒发一次心跳，发送失败就是断线情况，会重联socket服务

    private HeartFailedEvent mHeartFailedEvent;

    public SocketHeart() {
        mHeartFailedEvent = new HeartFailedEvent();
    }

    public void startHeart() {
        if (heartTask != null) return;

        heartTask = new Timer();

        task = new TimerTask() {

            @Override
            public void run() {
                try {
                    heartTask();
                } catch (Throwable e) {

                }
            }
        }; // 1s
        SocketClient.INSTANCE.setLastRecvPacketTime(System.currentTimeMillis());
        heartTask.schedule(task, HEART_TIME, HEART_TIME);
        KLog.d(TAG, "心跳开始");
    }

    public void stopHeart() {
        if (heartTask != null) {
            heartTask.cancel();
            heartTask = null;
            KLog.d(TAG, "心跳停止");
        }
    }

    public boolean isRuning() {
        return heartTask != null;
    }

    private long heartTask() {

        KLog.d(TAG, "heartTask()");

        boolean isSend = false;
        PingMessageSend msg = new PingMessageSend();
        byte[] data = msg.getMessageData();
        isSend = SocketClient.INSTANCE.send(data); // 直接发送

        if (!isSend) {
            KLog.d(TAG, "heart failed");
            EventBus.getDefault().post(new HeartFailedEvent());//心跳失败
        }
        else {
            long currTime = System.currentTimeMillis();
            long distTime = currTime - SocketClient.INSTANCE.getLastRecvPacketTime();

            if (distTime > HEART_TIME + 5000) {
                KLog.d(TAG, "heart timeOut");
                EventBus.getDefault().post(mHeartFailedEvent);//心跳失败
            }
        }

        return 10 * 1000; // 正常返回10秒一次心跳
    }

    public static class HeartFailedEvent {

    }
}
