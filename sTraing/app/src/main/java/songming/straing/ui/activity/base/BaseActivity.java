package songming.straing.ui.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.R;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.upload.UploadManager;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.app.interfaces.OnUploadProgressListener;
import songming.straing.utils.InputMethodUtils;
import songming.straing.utils.RequestUrlUtils;
import songming.straing.utils.ToastUtils;
import songming.straing.utils.UIHelper;
import songming.straing.widget.ProgressDialog;
import songming.straing.widget.ProgressPopup;
import songming.straing.widget.TitleBar;

/**
 * activity基类
 */
public class BaseActivity extends Activity implements BaseResponseListener {
    private static final String TAG = "BaseActivity";

    protected ProgressDialog mProgressDialog;

    protected TitleBar mTitleBar;
    protected ProgressPopup mProgressPopup;

    private DialogRefreshRunnable mDialogRefreshRunnable;

    private class DialogRefreshRunnable implements Runnable {
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            if (mProgressPopup != null) {
                mProgressPopup.setProgress(num);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mDialogRefreshRunnable = new DialogRefreshRunnable();
    }

    protected void onTitleLeftClick() {
        finish();
    }

    protected void onTitleRightClick(View v) {}

    protected void setTitleText(String text) {
        if (mTitleBar != null) {
            mTitleBar.setTitle(text);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        if (mTitleBar != null) {
            mTitleBar.setOnLeftBtnClickListener(new TitleBar.OnLeftBtnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodUtils.hideInputMethod(BaseActivity.this.getWindow().getDecorView());
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
        mProgressDialog = new ProgressDialog(this);
        mProgressPopup = new ProgressPopup(this);
    }

    @Override
    public void onStart(BaseResponse response) {
        if (response.isShowDialog()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void onStop(BaseResponse response) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(BaseResponse response)   {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (response.getStatus() != 1) {
            ToastUtils.ToastMessage(this, response.getErrorMsg());
        }
    }

    protected UploadManager mUploadManager;

    protected void uploadImg(String fileName) {
        if (TextUtils.isEmpty(fileName)) return;
        if (mUploadManager == null) {
            mUploadManager = UploadManager.create(new BaseResponseListener() {
                @Override
                public void onStart(BaseResponse response) {
                    mProgressPopup.showPopupWindow();
                }

                @Override
                public void onStop(BaseResponse response) {
                    if (mProgressPopup.isShowing()) {
                        mProgressPopup.dismiss();
                    }
                }

                @Override
                public void onFailure(BaseResponse response) {
                    if (mProgressPopup.isShowing()) {
                        mProgressPopup.dismiss();
                    }
                    ToastUtils.ToastMessage(BaseActivity.this, response.getErrorMsg());
                    onUploadFailed(response);
                }

                @Override
                public void onSuccess(BaseResponse response) {
                    if (mProgressPopup.isShowing()) {
                        mProgressPopup.dismiss();
                    }
                    onUploadSuccess(response);
                }
            });
        }
        if (mUploadManager.getOnUploadProgressListener() == null) {
            mUploadManager.setOnUploadProgressListener(new OnUploadProgressListener() {
                @Override
                public void onProgressChange(int num) {
                    //KLog.d("progress",""+num);
                    //mProgressDialog.setProgress(num);
                    mDialogRefreshRunnable.setNum(num);
                    runOnUiThread(mDialogRefreshRunnable);
                }
            });
        }
        mUploadManager.setUrl(new RequestUrlUtils.Builder().setHost(Config.HOST)
                                                           .setPath("/upload")
                                                           .addParam("key", LocalHost.INSTANCE.getKey())
                                                           .addParam("suffix", "jpg")
                                                           .build());
        mUploadManager.setFileName(fileName);
        mUploadManager.build().post();
    }

    public void onUploadSuccess(BaseResponse response) {}

    public void onUploadFailed(BaseResponse response) {}

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


    protected void setClickListener(@NonNull View.OnClickListener listener,View...views){
        for (View view : views) {
            if (view!=null){
                view.setOnClickListener(listener);
            }
        }
    }
}
