package songming.straing.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.socks.library.KLog;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.VolleyManager;
import songming.straing.app.socket.SocketMessageManager;
import songming.straing.app.socket.SocketService;
import songming.straing.utils.PreferenceUtils;

public class STraingApp extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10 * 1024 * 1024);
        PreferenceUtils.INSTANCE.init(appContext, "sTraining", MODE_PRIVATE);
        KLog.init(true);
        SocketMessageManager.INSTANCE.init();
        initPersonalData();

        //socket服务
        SocketService.startService(appContext);
    }

    private void initPersonalData() {
        String key = (String) PreferenceUtils.INSTANCE.getSharedPreferenceData("key", "null");
        long id = (long) PreferenceUtils.INSTANCE.getSharedPreferenceData("userid", 0l);
        boolean hasLogin = (int) PreferenceUtils.INSTANCE.getSharedPreferenceData("haslogin", 0) == 1;
        String avatar = (String) PreferenceUtils.INSTANCE.getSharedPreferenceData("avatar", "null");

        LocalHost.INSTANCE.setKey(key);
        LocalHost.INSTANCE.setUserId(id);
        LocalHost.INSTANCE.setHasLogin(hasLogin);
        LocalHost.INSTANCE.setUserAvatar(avatar);
    }
}
