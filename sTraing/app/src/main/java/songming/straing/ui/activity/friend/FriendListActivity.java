package songming.straing.ui.activity.friend;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.base.MBaseAdapter;
import songming.straing.app.adapter.base.viewholder.MViewHolder;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.FriendListRequest;
import songming.straing.app.https.request.GroupChatCreateRequest;
import songming.straing.model.GroupChatInfo;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CircleImageView;

/**
 * 我的好友列表
 */
public class FriendListActivity extends BaseActivity {

    private ListView list;
    private FriendListRequest friendListRequest;
    private List<UserDetailInfo> userList;

    private GroupChatCreateRequest chatCreateRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        userList = new ArrayList<>();
        initView();
        initReq();
    }

    FriendSelectedAdapter adapter;

    private void initView() {
        list = (ListView) findViewById(R.id.list);
        adapter = new FriendSelectedAdapter(this, userList);
        list.setAdapter(adapter);
    }

    private void initReq() {
        friendListRequest = new FriendListRequest();
        friendListRequest.setOnResponseListener(this);
        friendListRequest.setRequestType(0x11);
        friendListRequest.execute(true);

        chatCreateRequest=new GroupChatCreateRequest();
        chatCreateRequest.setOnResponseListener(this);
        chatCreateRequest.setRequestType(0x12);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            switch (response.getRequestType()){
                case 0x11:
                    userList.clear();
                    userList.addAll((Collection<? extends UserDetailInfo>) response.getData());
                    adapter.notifyDataSetChanged();
                    break;
                case 0x12:
                    UIHelper.startToGroupChatActivity(this, ((GroupChatInfo) response.getData()).memberCount,((GroupChatInfo) response.getData()).groupID);
                    finish();
                    break;
            }

        }

    }

    @Override
    protected void onTitleRightClick(View v) {
        chatCreateRequest.member_ids=adapter.getSelectedList();
        chatCreateRequest.post(true);
    }

    static class FriendSelectedAdapter extends MBaseAdapter<UserDetailInfo, FriendSelectedAdapter.ViewHolder> {

        public FriendSelectedAdapter(Context context, List<UserDetailInfo> datas) {
            super(context, datas);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_friend_select;
        }

        @Override
        public ViewHolder initViewHolder() {
            return new ViewHolder();
        }

        @Override
        public void onBindView(final int position, final UserDetailInfo data, final ViewHolder holder) {
            holder.avatar.loadImageDefault(data.avatar);
            holder.nick.setText(data.username);
            holder.selected.setVisibility(data.hasSelected ? View.VISIBLE : View.INVISIBLE);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.selected.setVisibility(data.hasSelected ? View.INVISIBLE : View.VISIBLE);
                    if (holder.selected.getVisibility() == View.VISIBLE) {
                        data.hasSelected = true;
                    } else {
                        data.hasSelected = false;
                    }
                }
            });

        }

        public String getSelectedList() {
            String result = LocalHost.INSTANCE.getUserId()+",";
            for (UserDetailInfo data : datas) {
                if (data.hasSelected) {
                    result += String.valueOf(data.userID) + ",";
                }
            }
            return result;
        }

        static class ViewHolder implements MViewHolder {
            public ImageView selected;
            public CircleImageView avatar;
            public TextView nick;
            public View rootView;

            @Override
            public void onInFlate(View v) {
                this.rootView = v;
                selected = (ImageView) v.findViewById(R.id.iv_selected);
                avatar = (CircleImageView) v.findViewById(R.id.avatar);
                nick = (TextView) v.findViewById(R.id.nick);
            }
        }
    }
}
