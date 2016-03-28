package songming.straing.ui.activity;

import android.os.Bundle;
import songming.straing.R;
import songming.straing.app.FileStore;
import songming.straing.app.cache.CacheManager;
import songming.straing.app.https.base.VolleyManager;
import songming.straing.app.thread.ThreadPoolManager;
import songming.straing.ui.activity.base.BaseActivity;

/**
 * 首页
 * */
public class AppStart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                //Looper.prepare();

                //准备工作
                initApp();

                //Looper.loop();
            }
        });
        // TODO: 2016/3/28 跳转页面

    }


    private void initApp() {
        //10M的volley缓存
        VolleyManager.INSTANCE.initQueue((10 << 10) << 10);
        FileStore.INSTANCE.createFileFolder();
        FileStore.INSTANCE.clearTempFolder();
        CacheManager.INSTANCE.initHttpCache();
    }
}
