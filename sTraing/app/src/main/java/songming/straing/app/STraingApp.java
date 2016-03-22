package songming.straing.app;

import android.app.Application;
import android.content.Context;
import songming.straing.app.https.base.VolleyManager;

public class STraingApp extends Application{
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10 * 1024 * 1024);
    }
}
