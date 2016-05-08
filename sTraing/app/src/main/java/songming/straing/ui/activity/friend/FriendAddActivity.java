package songming.straing.ui.activity.friend;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import songming.straing.R;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.FriendAddRequest;
import songming.straing.app.https.request.PersonDetailRequest;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CircleImageView;
import songming.straing.widget.sortlist.ClearEditText;

/**
 * 添加好友
 */
public class FriendAddActivity extends BaseActivity {

    private ClearEditText filter_edit;
    private CircleImageView avatar;
    private ImageView ic_add;
    private TextView nick;
    private TextView desc;
    private LinearLayout desc_layout;
    private RelativeLayout item_root;

    private PersonDetailRequest personDetailRequest;
    private Button search;

    private FriendAddRequest friendAddRequest;

    private long userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        initView();
        initReq();
    }

    private void initReq() {
        personDetailRequest = new PersonDetailRequest();
        personDetailRequest.setRequestType(10);
        personDetailRequest.setOnResponseListener(this);

        friendAddRequest = new FriendAddRequest();
        friendAddRequest.setRequestType(11);
        friendAddRequest.setOnResponseListener(this);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            switch (response.getRequestType()) {
                case 10:
                    filter_edit.setText("");
                    updateView((UserDetailInfo) response.getData());
                    break;
                case 11:
                    finish();
                    break;
            }
        }
    }

    private void updateView(UserDetailInfo info) {
        if (info == null) {
            item_root.setVisibility(View.GONE);
            return;
        } else {
            userid = info.userID;
            item_root.setVisibility(View.VISIBLE);
            avatar.loadImageDefault(info.avatar);
            nick.setText(info.username);
            desc.setText(info.signNature);
        }
    }

    private void initView() {
        filter_edit = (ClearEditText) findViewById(R.id.filter_edit);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        ic_add = (ImageView) findViewById(R.id.ic_add);
        nick = (TextView) findViewById(R.id.nick);
        desc = (TextView) findViewById(R.id.desc);
        desc_layout = (LinearLayout) findViewById(R.id.desc_layout);
        item_root = (RelativeLayout) findViewById(R.id.item_root);


        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid != 0) {
                    friendAddRequest.target_id = userid;
                    friendAddRequest.execute(true);
                }

            }
        });
        search = (Button) findViewById(R.id.seartc);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.startToPersonIndexActivity(FriendAddActivity.this, userid);
            }
        });
    }

    private void submit() {
        // validate
        String edit = filter_edit.getText().toString().trim();
        if (TextUtils.isEmpty(edit)) {
            Toast.makeText(this, "请输入ID", Toast.LENGTH_SHORT).show();
            return;
        }

        long userid = Long.parseLong(edit);

        personDetailRequest.userid = userid;
        personDetailRequest.execute();

    }

}
