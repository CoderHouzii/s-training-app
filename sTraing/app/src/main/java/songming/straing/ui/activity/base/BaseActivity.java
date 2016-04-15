package songming.straing.ui.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.R;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.widget.TitleBar;

/**
 * activity基类
 */
public  class BaseActivity extends Activity implements BaseResponseListener {
    private static final String TAG = "BaseActivity";


    protected TitleBar mTitleBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    protected void onTitleLeftClick(){}
    protected void onTitleRightClick(View v){}
    protected void setTitleText(String text){
        if (mTitleBar!=null) {
            mTitleBar.setTitle(text);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mTitleBar= (TitleBar) findViewById(R.id.titlebar);
        if (mTitleBar!=null) {
            mTitleBar.setOnLeftBtnClickListener(new TitleBar.OnLeftBtnClickListener() {
                @Override
                public void onClick(View v) {
                    onTitleLeftClick();
                }
            });
            mTitleBar.setOnRightBtnClickListener(new TitleBar.OnRightBtnClickListener() {
                @Override
                public void onClick(View v) {
                    onTitleRightClick(v);
                }
            });
        }
    }

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
