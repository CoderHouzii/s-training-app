package songming.straing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import songming.straing.R;

/**
 * 底部操作栏
 */
public class BottomTabBar extends FrameLayout implements View.OnClickListener{

    private LinearLayout bottomBar;

    private TextView btn_index;
    private TextView btn_friends;
    private TextView btn_mission;
    private TextView btn_message;
    private TextView btn_me;

    public BottomTabBar(Context context) {
        this(context, null);
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bottomBar = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.widget_bottom_tab, this, false);
        addView(bottomBar);

        btn_index= (TextView) bottomBar.findViewById(R.id.btn_index);
        btn_friends= (TextView) bottomBar.findViewById(R.id.btn_friends);
        btn_mission= (TextView) bottomBar.findViewById(R.id.btn_mission);
        btn_message= (TextView) bottomBar.findViewById(R.id.btn_message);
        btn_me= (TextView) bottomBar.findViewById(R.id.btn_me);

        btn_index.setOnClickListener(this);
        btn_friends.setOnClickListener(this);
        btn_mission.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        btn_me.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_index:
                if (mBottomBarClickListener!=null)
                    mBottomBarClickListener.onIndexClick(v);
                break;
            case R.id.btn_friends:
                if (mBottomBarClickListener!=null)
                    mBottomBarClickListener.onFriendClick(v);
                break;
            case R.id.btn_mission:
                if (mBottomBarClickListener!=null)
                    mBottomBarClickListener.onMissionClick(v);
                break;
            case R.id.btn_message:
                if (mBottomBarClickListener!=null)
                    mBottomBarClickListener.onMessageClick(v);
                break;
            case R.id.btn_me:
                if (mBottomBarClickListener!=null)
                    mBottomBarClickListener.onMeClick(v);
                break;
            default:
                break;
        }

    }


    private OnBottomBarClickListener mBottomBarClickListener;

    public OnBottomBarClickListener getOnBottomBarClickListener() {
        return mBottomBarClickListener;
    }

    public void setOnBottomBarClickListener(OnBottomBarClickListener bottomBarClickListener) {
        mBottomBarClickListener = bottomBarClickListener;
    }

    public interface OnBottomBarClickListener{
        void onIndexClick(View v);
        void onFriendClick(View v);
        void onMissionClick(View v);
        void onMessageClick(View v);
        void onMeClick(View v);
    }
}
