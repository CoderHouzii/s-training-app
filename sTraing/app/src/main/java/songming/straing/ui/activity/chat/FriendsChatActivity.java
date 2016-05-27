package songming.straing.ui.activity.chat;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import songming.straing.R;
import songming.straing.app.adapter.ChatAdapter;
import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.PersonDetailRequest;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.app.socket.SendSocketMessage;
import songming.straing.app.socket.SocketService;
import songming.straing.app.socket.message.SendChatMessage;
import songming.straing.model.ChatInfo;
import songming.straing.model.ChatReceiverInfo;
import songming.straing.model.GroupChatInfo;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;

/**
 * 个人聊天
 */
public class FriendsChatActivity extends BaseActivity implements View.OnClickListener {

    private ListView list;
    private EditText ed_input;
    private TextView btn_send;
    private LinearLayout ll_input;
    private long friendId;
    private String friendName;

    private ChatAdapter mChatAdapter;
    private List<Pair<Integer, ChatInfo>> datas;

    public static final int MODE_FRIEND = 201;
    public static final int MODE_GROUP = 202;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_FRIEND, MODE_GROUP})
    public @interface ChatMode {
    }

    private int mode = MODE_FRIEND;

    private int personCount;
    private long groupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_chat);
        getdata();
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void getdata() {
        mode = getIntent().getIntExtra("mode", MODE_FRIEND);
        if (mode == MODE_FRIEND) {
            friendId = getIntent().getLongExtra("id", 0);
            if (friendId == 0) {
                finish();
                return;
            }
            friendName = getIntent().getStringExtra("name");
            if (!TextUtils.isEmpty(friendName)) {
                setTitleText("正在与" + friendName + "聊天");
            } else {
                setTitleText("正在聊天");
            }
        } else if (mode == MODE_GROUP) {
            personCount =  getIntent().getIntExtra("memberscount",0);
            groupid=getIntent().getLongExtra("groupid",0);
            if (groupid==0){
                finish();
                return;
            }

            setTitleText("群聊" + String.format(Locale.getDefault(), "(%d 人)", personCount));
        }
    }


    private void initView() {
        list = (ListView) findViewById(R.id.list);
        ed_input = (EditText) findViewById(R.id.ed_input);
        btn_send = (TextView) findViewById(R.id.btn_send);
        ll_input = (LinearLayout) findViewById(R.id.ll_input);
        ll_input.setVisibility(View.VISIBLE);

        btn_send.setOnClickListener(this);
    }

    private void initData() {
        datas = new ArrayList<>();
        mChatAdapter = new ChatAdapter(this, datas);
        list.setAdapter(mChatAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String input = ed_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "不能输入空内容哦", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (mode){
            case MODE_FRIEND:
                SocketService.CallServiceSend(this, new SendChatMessage(LocalHost.INSTANCE.getKey(), LocalHost.INSTANCE.getUserId(), friendId, input).getMessageData());
                break;
            case MODE_GROUP:
                SocketService.CallServiceSend(this, new SendChatMessage(LocalHost.INSTANCE.getKey(), LocalHost.INSTANCE.getUserId(), groupid, input).getMessageData());
                break;

        }
        refreshData(input, LocalHost.INSTANCE.getUserId());
        ed_input.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(final Events.PersonChatEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ChatReceiverInfo info = event.getChatReceiverInfo();
                if (info != null) {
                    getOtherAvatar(info.text, Long.parseLong(info.sid));
                }
            }
        });
    }


    @Subscribe
    public void onEvent(final Events.GroupChatEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ChatReceiverInfo info = event.getChatReceiverInfo();
                refreshGroupData(info);
            }
        });

    }


    String otherAvatar;

    private void getOtherAvatar(final String content, final long receiveId) {
        if (TextUtils.isEmpty(otherAvatar)) {
            PersonDetailRequest detailRequest = new PersonDetailRequest();
            detailRequest.userid = friendId;
            detailRequest.setOnResponseListener(new BaseResponseListener() {
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
                        otherAvatar = ((UserDetailInfo) response.getData()).avatar;
                        refreshData(content, receiveId);
                    }
                }
            });
            detailRequest.execute();
        } else
            refreshData(content, receiveId);
    }

    private void refreshData(String content, long receiveId) {
        Pair<Integer, ChatInfo> infoPair;
        if (receiveId == LocalHost.INSTANCE.getUserId()) {
            infoPair = new Pair<>(ChatAdapter.RIGHT, new ChatInfo(LocalHost.INSTANCE.getUserAvatar(), content,receiveId));
        } else {
            infoPair = new Pair<>(ChatAdapter.LEFT, new ChatInfo(otherAvatar, content,receiveId));
        }

        datas.add(infoPair);
        mChatAdapter.notifyDataSetChanged();
    }

    private void refreshGroupData(ChatReceiverInfo info) {
        if (info == null) return;
        Pair<Integer, ChatInfo> infoPair;
        if (Long.parseLong(info.rid.trim()) == LocalHost.INSTANCE.getUserId()) {
            infoPair = new Pair<>(ChatAdapter.RIGHT, new ChatInfo(LocalHost.INSTANCE.getUserAvatar(), info.text,Long.parseLong(info.rid.trim())));
        } else {
            infoPair = new Pair<>(ChatAdapter.LEFT, new ChatInfo(info.avatar, info.text,Long.parseLong(info.rid.trim())));
        }

        datas.add(infoPair);
        mChatAdapter.notifyDataSetChanged();
    }


}
