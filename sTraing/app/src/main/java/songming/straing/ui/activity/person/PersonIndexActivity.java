package songming.straing.ui.activity.person;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import songming.straing.R;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.PersonDetailRequest;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CircleImageView;

/**
 * 个人主页
 */
public class PersonIndexActivity extends BaseActivity implements View.OnClickListener{

    private ViewHolder vh;

    private long userid;
    private PersonDetailRequest mPersonDetailRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_index);
        userid=getIntent().getLongExtra("userid",0);
        if (userid==0)finish();
        vh=new ViewHolder(getWindow().getDecorView());
        initReq();

    }

    private void initReq() {
        mPersonDetailRequest=new PersonDetailRequest();
        mPersonDetailRequest.setOnResponseListener(this);
        mPersonDetailRequest.userid=userid;

        UserDetailInfo info=mPersonDetailRequest.loadCache(userid);
        updateView(info);

        mPersonDetailRequest.execute();
    }

    private void updateView(UserDetailInfo info) {
        if (info==null)return;

        vh.avatar.loadImageDefault(info.avatar);
        vh.person_id.setText("ID:"+info.userID+" | "+info.username);
        vh.level.setText(""+info.rank);
        vh.signature.setText(info.signNature);

        bindEvent(info.canEdit==1);
    }

    private void bindEvent(boolean canedit) {
        setClickListener(this,vh.practise,vh.circle,vh.article);
        if (canedit){
            setClickListener(this,vh.avatar,vh.signature);
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus()==200){
            updateView((UserDetailInfo) response.getData());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avatar:
                break;
            case R.id.practise:
                break;
            case R.id.circle:
                if (userid==LocalHost.INSTANCE.getUserId()){
                    UIHelper.startToCircleActivity(this);
                }else {
                    UIHelper.startToOtherCircleActivity(this,userid);
                }
                break;
            case R.id.article:
                UIHelper.startToArticleListActivity(this,userid);
                break;
            case R.id.signature:
                break;
        }

    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        finish();
    }

    @Override
    protected void onTitleRightClick(View v) {
        super.onTitleRightClick(v);
    }

    static class ViewHolder {
        public View rootView;
        public CircleImageView avatar;
        public TextView person_id;
        public TextView practise;
        public TextView circle;
        public TextView article;
        public TextView level;
        public TextView city;
        public TextView work;
        public TextView signature;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.avatar = (CircleImageView) rootView.findViewById(R.id.avatar);
            this.person_id = (TextView) rootView.findViewById(R.id.person_id);
            this.practise = (TextView) rootView.findViewById(R.id.practise);
            this.circle = (TextView) rootView.findViewById(R.id.circle);
            this.article = (TextView) rootView.findViewById(R.id.article);
            this.level = (TextView) rootView.findViewById(R.id.level);
            this.city = (TextView) rootView.findViewById(R.id.city);
            this.work = (TextView) rootView.findViewById(R.id.work);
            this.signature = (TextView) rootView.findViewById(R.id.signature);
        }
    }
}
