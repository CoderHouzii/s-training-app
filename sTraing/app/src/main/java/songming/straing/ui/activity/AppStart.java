package songming.straing.ui.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.R;
import songming.straing.app.FileStore;
import songming.straing.app.cache.CacheManager;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.VolleyManager;
import songming.straing.app.thread.ThreadPoolManager;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.UIHelper;

/**
 * 首页
 */
public class AppStart extends BaseActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        initView();
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                //Looper.prepare();

                //准备工作
                initApp();

                //Looper.loop();
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    private void startAnima() {
        ScaleAnimation animation = new ScaleAnimation(1f, 1.7f, 1f, 1.7f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(8500);
        animation.setFillAfter(true);
        title.startAnimation(animation);
    }
    private void initApp() {
        //10M的volley缓存
        VolleyManager.INSTANCE.initQueue((10 << 10) << 10);
        FileStore.INSTANCE.createFileFolder();
        FileStore.INSTANCE.clearTempFolder();
        CacheManager.INSTANCE.initHttpCache();
    }

    @Subscribe
    public void onEvent(Events.StartToLoginEvent event) {
        UIHelper.startToLoginActivity(this);
        finish();
    }

    @Subscribe
    public void onEvent(Events.StartToMainEvent event) {
        UIHelper.startToMainActivity(this);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        startAnima();
    }
}
