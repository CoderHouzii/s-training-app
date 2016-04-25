package songming.straing.app;

import android.app.Application;
import android.content.Context;
import songming.straing.app.https.base.VolleyManager;
import songming.straing.utils.PreferenceUtils;

public class STraingApp extends Application{
    public static Context appContext;

    public static final String HOST="can not show";

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10 * 1024 * 1024);
        PreferenceUtils.INSTANCE.init(appContext, "sTraining", MODE_PRIVATE);

    }
}
