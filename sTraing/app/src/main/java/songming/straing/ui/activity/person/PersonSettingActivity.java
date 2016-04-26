package songming.straing.ui.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import songming.straing.R;
import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.PersonDataUpdateRequest;
import songming.straing.app.https.request.PersonDetailRequest;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CircleImageView;

/**
 * 个人资料页
 */
public class PersonSettingActivity extends BaseActivity implements View.OnClickListener{

    public static final int CODE_AVATAR_SETTING=0x11;
    public static final int CODE_NICK_SETTING=0x12;

    private ViewHolder vh;

    private String fileName;

    private PersonDataUpdateRequest mDataUpdateRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        initView();
        initReq();

    }




    private void initView() {
        vh=new ViewHolder(getWindow().getDecorView());
        vh.layout_avatar.setOnClickListener(this);
        vh.layout_nick.setOnClickListener(this);
        vh.layout_signature.setOnClickListener(this);

        vh.avatar.loadImageDefault(LocalHost.INSTANCE.getUserAvatar());
        vh.nick.setText(LocalHost.INSTANCE.getUserName());
        vh.signature.setText(LocalHost.INSTANCE.getUserSignature());

    }
    private void initReq() {
        mDataUpdateRequest=new PersonDataUpdateRequest();
        mDataUpdateRequest.setOnResponseListener(this);
    }
    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        finish();
    }

    @Override
    protected void onTitleRightClick(View v) {
        super.onTitleRightClick(v);
        mDataUpdateRequest.avatar=fileName;
        mDataUpdateRequest.username=vh.nick.getText().toString().trim();
        mDataUpdateRequest.sign_nature=vh.nick.getText().toString().trim();
        mDataUpdateRequest.post(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_avatar:
                UIHelper.startToAvatarSettingActivity(this,CODE_AVATAR_SETTING);
                break;
            case R.id.layout_nick:
                String mPreNick=vh.nick.getText().toString().trim();
                UIHelper.startToNickSettingActivity(this,NickAndSignatureSettingActivity.MODE_NICK,mPreNick,
                        CODE_NICK_SETTING);
                break;
            case R.id.layout_signature:
                String mSignature=vh.signature.getText().toString().trim();
                UIHelper.startToNickSettingActivity(this,NickAndSignatureSettingActivity.MODE_SIGNATURE,mSignature,
                        CODE_NICK_SETTING);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODE_AVATAR_SETTING:
                if (resultCode==110){
                    if (data!=null){
                        fileName=data.getStringExtra("filename");
                        EventBus.getDefault().post(new Events.RefreshAvatarEvent());
                    }
                    vh.avatar.loadImageDefault(LocalHost.INSTANCE.getUserAvatar());
                }
                break;
            case CODE_NICK_SETTING:
                if (resultCode==101){
                    String nick=data.getStringExtra("nick");
                    if (!TextUtils.isEmpty(nick)) {
                        vh.nick.setText(nick);
                    }
                }
                if (resultCode==102){
                    String signature=data.getStringExtra("signature");
                    if (!TextUtils.isEmpty(signature)) {
                        vh.signature.setText(signature);
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus()==1){
            //请求一次用户详情
            if (!LocalHost.INSTANCE.getKey().equals("null")&&LocalHost.INSTANCE.getUserId()!=0) {
                PersonDetailRequest request = new PersonDetailRequest();
                request.setOnResponseListener(new BaseResponseListener() {
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
                        if (response.getStatus() == 1) {
                            UserDetailInfo info = (UserDetailInfo) response.getData();
                            if (info != null) {
                                LocalHost.INSTANCE.setUserAvatar(info.avatar);
                                LocalHost.INSTANCE.setUserId(info.userID);
                                LocalHost.INSTANCE.setUserName(info.username);
                                LocalHost.INSTANCE.setUserSignature(info.signNature);
                                setResult(203);
                                finish();
                            }else {
                                finish();
                            }
                        }
                    }
                });
                request.userid=LocalHost.INSTANCE.getUserId();
                request.post();
            }

        }
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView arrow;
        public CircleImageView avatar;
        public RelativeLayout layout_avatar;
        public TextView nick;
        public RelativeLayout layout_nick;
        public RelativeLayout layout_signature;
        public TextView signature;
        public RelativeLayout layout_tag;
        public RelativeLayout layout_work;
        public RelativeLayout layout_city;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.arrow = (ImageView) rootView.findViewById(R.id.arrow);
            this.avatar = (CircleImageView) rootView.findViewById(R.id.avatar);
            this.layout_avatar = (RelativeLayout) rootView.findViewById(R.id.layout_avatar);
            this.nick = (TextView) rootView.findViewById(R.id.nick);
            this.layout_nick = (RelativeLayout) rootView.findViewById(R.id.layout_nick);
            this.layout_signature = (RelativeLayout) rootView.findViewById(R.id.layout_signature);
            this.signature= (TextView) rootView.findViewById(R.id.signature);
            this.layout_tag = (RelativeLayout) rootView.findViewById(R.id.layout_tag);
            this.layout_work = (RelativeLayout) rootView.findViewById(R.id.layout_work);
            this.layout_city = (RelativeLayout) rootView.findViewById(R.id.layout_city);
        }
    }
}
