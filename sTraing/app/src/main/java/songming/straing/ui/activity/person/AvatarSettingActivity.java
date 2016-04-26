package songming.straing.ui.activity.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.socks.library.KLog;
import com.yalantis.ucrop.UCrop;
import org.json.JSONObject;
import songming.straing.R;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.CameraUtils;
import songming.straing.utils.ToastUtils;
import songming.straing.widget.CircleImageView;
import songming.straing.widget.PopupPhotoSelected;

/**
 * 头像设置
 */
public class AvatarSettingActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView avatar_preview;
    private Button btn_ok;

    private PopupPhotoSelected mPhotoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_setting);
        initView();
    }

    private void initView() {
        avatar_preview = (CircleImageView) findViewById(R.id.avatar_preview);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        if (!LocalHost.INSTANCE.getUserAvatar().equals("null")) {
            avatar_preview.loadImageDefault(LocalHost.INSTANCE.getUserAvatar());
        }
        mPhotoSelected = new PopupPhotoSelected(this);

        avatar_preview.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar_preview:
                mPhotoSelected.showPopupWindow();
                break;
            case R.id.btn_ok:
                uploadImg(CameraUtils.photoPath);
                break;
        }
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("filename", fileName);
        setResult(110, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相片选择回调
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CameraUtils.FROM_CAMERA:
                case CameraUtils.FROM_ALBUM:
                    Uri sourceUri;
                    if (data != null) {
                        sourceUri = data.getData();
                    }
                    else {
                        sourceUri = CameraUtils.tempPhoto;
                    }
                    if (sourceUri != null) {
                        CameraUtils.crop(this, sourceUri, CameraUtils.AVATAR);
                    }
                    else {
                        ToastUtils.ToastMessage(this, "源照片为空");
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    // 裁剪
                    Uri resultUri = UCrop.getOutput(data);
                    avatar_preview.loadImageDefault(String.valueOf(resultUri));
                    break;
                default:
                    break;
            }
        }
    }

    private String fileName;

    @Override
    public void onUploadSuccess(BaseResponse response) {
        super.onUploadSuccess(response);
        if (response.getStatus() == 1) {
            JSONObject jsonObject = response.getJSONObject();
            if (jsonObject != null) {
                String url = jsonObject.optString("url");
                KLog.d("avatar", url);
                fileName = jsonObject.optString("fileName");
                LocalHost.INSTANCE.setUserAvatar(url);
                avatar_preview.loadImageDefault(url);
                onBackPressed();
            }
        }
        else {
            ToastUtils.ToastMessage(this, response.getErrorMsg());
        }
    }
}
