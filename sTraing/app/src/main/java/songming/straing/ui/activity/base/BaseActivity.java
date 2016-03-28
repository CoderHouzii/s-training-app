package songming.straing.ui.activity.base;

import android.app.Activity;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.interfaces.BaseResponseListener;

/**
 * activity基类
 */
public  class BaseActivity extends Activity implements BaseResponseListener {
    private static final String TAG = "BaseActivity";

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onStop(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onSuccess(BaseResponse response) {

    }

    @Subscribe
    public void onEventMainThread(Events event) {

    }
    public <V extends View> V getView(int resId) {
        try {
            return (V) findViewById(resId);
        } catch (ClassCastException e) {
            Log.d(TAG, "无法强转");
            e.printStackTrace();
        }
        return null;
    }
}
