package songming.straing.ui.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import java.util.LinkedList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.R;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.PersonDetailRequest;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.app.socket.SocketClient;
import songming.straing.app.socket.SocketService;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.ui.activity.login.LoginActivity;
import songming.straing.ui.fragment.FriendsFragment;
import songming.straing.ui.fragment.IndexFragment;
import songming.straing.ui.fragment.MeFragment;
import songming.straing.ui.fragment.MessageFragment;
import songming.straing.ui.fragment.MissionFragment;
import songming.straing.ui.fragment.base.BaseFragment;
import songming.straing.utils.ToastUtils;
import songming.straing.utils.UIHelper;
import songming.straing.widget.BottomTabBar;
import songming.straing.widget.TitleBar;

/**
 * 主页
 */
public class MainActivity extends FragmentActivity implements BottomTabBar.OnBottomBarClickListener {
    private FragmentManager mFragmentManager;

    private SparseArray<BaseFragment> mFragments;

    private TitleBar titlebar;
    private BottomTabBar bottom_bar;

    private int currentPos;

    private static final int FRAG_INDEX=0x10;
    private static final int FRAG_FRIENDS=0x11;
    private static final int FRAG_MISSION=0x12;
    private static final int FRAG_MESSAGE=0x13;
    private static final int FRAG_ME=0x14;

    private BaseFragment currentFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initReq();
        initView();
        initFragments();
        transateFragment(FRAG_INDEX);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    private void initView() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        titlebar.setLeftButtonVisible(View.INVISIBLE);
        bottom_bar = (BottomTabBar) findViewById(R.id.bottom_bar);

        bottom_bar.setOnBottomBarClickListener(this);
    }

    private void initReq() {
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
                            EventBus.getDefault().post(new Events.RefreshDataEvent());
                        }
                    }
                }
            });
            request.userid=LocalHost.INSTANCE.getUserId();
            request.post();
        }
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mFragments = new SparseArray<>();
        mFragments.put(FRAG_INDEX, (BaseFragment) mFragmentManager.findFragmentById(R.id.fragment_index));
        mFragments.put(FRAG_FRIENDS, (BaseFragment) mFragmentManager.findFragmentById(R.id.fragment_friend));
        mFragments.put(FRAG_MISSION, (BaseFragment) mFragmentManager.findFragmentById(R.id.fragment_mission));
        mFragments.put(FRAG_MESSAGE, (BaseFragment) mFragmentManager.findFragmentById(R.id.fragment_message));
        mFragments.put(FRAG_ME, (BaseFragment) mFragmentManager.findFragmentById(R.id.fragment_me));

    }

    @Override
    public void onIndexClick(View v) {
        transateFragment(FRAG_INDEX);
    }

    @Override
    public void onFriendClick(View v) {
        transateFragment(FRAG_FRIENDS);
    }

    @Override
    public void onMissionClick(View v) {
        transateFragment(FRAG_MISSION);
    }

    @Override
    public void onMessageClick(View v) {
        transateFragment(FRAG_MESSAGE);
    }

    @Override
    public void onMeClick(View v) {
        transateFragment(FRAG_ME);
    }

    void transateFragment(int value) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        currentPos = value;
        for (int i = 0; i < mFragments.size(); i++) {
            transaction.hide(mFragments.valueAt(i));
        }
        currentFrag=mFragments.get(value);
        if (currentFrag!=null){
            titlebar.setTitle(currentFrag.getTitle());
        }else {
            titlebar.setTitle("s-Training");
        }
        transaction.show(mFragments.get(value)).commitAllowingStateLoss();
        if (value==FRAG_FRIENDS){
            titlebar.setRightButtonVisibility(View.VISIBLE);
            titlebar.setRightButtonText("添加好友");
            titlebar.setOnRightBtnClickListener(onRightBtnClickListener);
        }else {
            titlebar.setOnRightBtnClickListener(null);
            titlebar.setRightButtonText("");
            titlebar.setRightButtonVisibility(View.GONE);
        }
    }

    private TitleBar.OnRightBtnClickListener onRightBtnClickListener=new TitleBar.OnRightBtnClickListener() {
        @Override
        public void onClick(View v) {
            UIHelper.startToAddFriendActivity(MainActivity.this);

        }
    };

    private long exittime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if ((System.currentTimeMillis() - exittime) > 2000) {
                    ToastUtils.ToastMessage(this, "再点一次退出");
                    exittime = System.currentTimeMillis();
                }
                else {
                    moveTaskToBack(false);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**解决fragment的onActivityResult不回调的问题*/
        if (currentFrag!=null){
            currentFrag.onActivityResult(requestCode,resultCode,data);
        }else {
            for (int i = 0; i < mFragments.size(); i++) {
                mFragments.valueAt(i).onActivityResult(requestCode,resultCode,data);
            }
        }
    }

    @Subscribe
    public void onEvent(Events.ChangeToFragment event) {
        if (event!=null){
            transateFragment(event.getFragIndex());
        }
    }

    @Subscribe
    public void onEventMainThread(Events.StartToLoginEvent event) {
        UIHelper.startToLoginActivity(this);
        SocketService.CallService(this, Config.SocketIntent.Types.DISCONNECT);
        finish();
    }
}
