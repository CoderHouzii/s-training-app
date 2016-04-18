package songming.straing.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import songming.straing.R;
import songming.straing.ui.fragment.base.BaseFragment;
import songming.straing.utils.ToastUtils;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CircleImageView;

/**
 * 个人
 */
public class MeFragment extends BaseFragment implements View.OnClickListener{

    //个人资料设置的requestCode
    private static final int CODE_PERSON_SETTING=0x10;


    private ViewHolder vh;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void bindData() {
        vh=new ViewHolder(mView);
        vh.layout_person_setting.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_person_setting:
                //个人资料页
                UIHelper.startToPersonSettingActivity(mContext,CODE_PERSON_SETTING);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CODE_PERSON_SETTING) ToastUtils.ToastMessage(mContext,"success");
    }

    @Override
    public String getTitle() {
        return "个人主页";
    }

    public static class ViewHolder {
        public View rootView;
        public CircleImageView avatar;
        public TextView nick;
        public TextView level;
        public ImageView arrow;
        public TextView signature;
        public RelativeLayout layout_person_setting;
        public LinearLayout layout_circle;
        public LinearLayout layout_article;
        public LinearLayout layout_setting;
        public Button btn_exit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.avatar = (CircleImageView) rootView.findViewById(R.id.avatar);
            this.nick = (TextView) rootView.findViewById(R.id.nick);
            this.level = (TextView) rootView.findViewById(R.id.level);
            this.arrow = (ImageView) rootView.findViewById(R.id.arrow);
            this.signature = (TextView) rootView.findViewById(R.id.signature);
            this.layout_person_setting = (RelativeLayout) rootView.findViewById(R.id.layout_person_setting);
            this.layout_circle = (LinearLayout) rootView.findViewById(R.id.layout_circle);
            this.layout_article = (LinearLayout) rootView.findViewById(R.id.layout_article);
            this.layout_setting = (LinearLayout) rootView.findViewById(R.id.layout_setting);
            this.btn_exit = (Button) rootView.findViewById(R.id.btn_exit);
        }
    }
}
