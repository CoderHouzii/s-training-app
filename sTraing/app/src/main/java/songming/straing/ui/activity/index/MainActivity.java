package songming.straing.ui.activity.index;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import java.util.LinkedList;
import songming.straing.R;
import songming.straing.ui.activity.base.BaseActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        initFragments();
        transateFragment(FRAG_INDEX);
    }

    private void initView() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        bottom_bar = (BottomTabBar) findViewById(R.id.bottom_bar);

        bottom_bar.setOnBottomBarClickListener(this);
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
        transaction.show(mFragments.get(value)).commitAllowingStateLoss();
    }

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
}