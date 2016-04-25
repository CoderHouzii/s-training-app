package songming.straing.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.VolleyManager;
import songming.straing.utils.PreferenceUtils;

public class STraingApp extends Application{
    public static Context appContext;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10 * 1024 * 1024);
        PreferenceUtils.INSTANCE.init(appContext, "sTraining", MODE_PRIVATE);

        initPersonalData();

    }

    private void initPersonalData() {
        String key= (String) PreferenceUtils.INSTANCE.getSharedPreferenceData("key","null");
        long id= (long) PreferenceUtils.INSTANCE.getSharedPreferenceData("userid",0);

        if (key!=null&&!key.equals("null")){
            LocalHost.INSTANCE.setKey(key);
        }
        if (id!=0){
            LocalHost.INSTANCE.setUserId(id);
        }
    }
}
