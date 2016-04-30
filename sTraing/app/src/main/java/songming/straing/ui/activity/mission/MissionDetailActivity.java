package songming.straing.ui.activity.mission;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.MethodNotSupportedException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import songming.straing.R;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.MissionFinishRequest;
import songming.straing.model.MissionInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.ToastUtils;

/**
 * 任务详情
 */
public class MissionDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String BUNDLE_NAME = "bundle";
    public static final String INFO = "info";

    private ViewHolder vh;
    private MissionInfo missionInfo;


    private static final int MODE_TIME_COUNT = 0x20;
    private static final int MODE_TIME_BREAK = 0x21;
    private static final int MODE_NORMAL = 0x22;


    private static final int TIME_START = 0x10;
    private static final int TIME_STOP = 0x11;


    //当前计时模式，normal为无计时
    private int currentMode = MODE_NORMAL;

    /**
     * 总时间：
     * pos=0:总用时
     * pos=1:总休息
     */
    private int[] times = new int[2];

    private WeakHandler mHandler;

    private MissionFinishRequest missionFinishRequest;


    /**
     * pinner数量:
     * item[]==0取预设值
     * item[]>0取item
     */
    private int pinnerSize = 0;
    //当前进行的pinner
    private int currentPinner;

    private List<Bean> postValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_detail);
        getData();
        mHandler = new WeakHandler(this);
        postValue = new ArrayList<>();
        initView();
        initReq();
        bindData();
    }


    private void getData() {
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_NAME);
        if (bundle == null) finish();
        missionInfo = (MissionInfo) bundle.getSerializable(INFO);
        if (missionInfo == null) finish();
    }

    private void initView() {
        vh = new ViewHolder(getWindow().getDecorView());
        setTitleText(missionInfo.title);

        vh.btn_add.setOnClickListener(this);

        vh.btn_start.setOnClickListener(this);
        vh.btn_break.setOnClickListener(this);
        vh.btn_finish.setOnClickListener(this);
    }

    private void initReq() {
        missionFinishRequest = new MissionFinishRequest();
        missionFinishRequest.setOnResponseListener(this);
    }

    private void bindData() {
        vh.tx_mission_target.setText(missionInfo.content);
        generatePinner(missionInfo);
    }


    /**
     * 生成pinner
     *
     * @param info
     */
    void generatePinner(MissionInfo info) {
        pinnerSize = 0;
        if (info == null) return;
        if (info.items == null || info.items.size() <= 0) pinnerSize = info.presetGroup;
        else pinnerSize = info.items.size();

        if (vh.groups_layout.getChildCount() > 0) {
            vh.groups_layout.removeAllViews();
        }
        for (int i = 0; i < pinnerSize; i++) {
            View pinner = LayoutInflater.from(this).inflate(R.layout.item_group_pinner, vh.groups_layout, false);
            if (pinner != null) {
                ((TextView) pinner.findViewById(R.id.group_count)).setText("第" + (i + 1) + "组");
            }
            vh.groups_layout.addView(pinner);
        }
    }


    void generatePinner() {
        View pinner = LayoutInflater.from(this).inflate(R.layout.item_group_pinner, vh.groups_layout, false);
        if (pinner != null) {
            pinnerSize++;
            ((TextView) pinner.findViewById(R.id.group_count)).setText("第" + (pinnerSize) + "组");
        }
        vh.groups_layout.addView(pinner);
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacks(null);
        }
        cleanTimer();
        super.onDestroy();

    }


    /**
     * 更新计时模式
     *
     * @param mode   计时模式：用时/休息
     * @param action 计时动作：开始/停止
     */
    void updateTimer(int mode, int action) {
        mHandler.setAction(action);
        boolean isLast = false;
        switch (mode) {
            case MODE_TIME_COUNT:
                if (currentMode != MODE_TIME_COUNT) {
                    mHandler.removeMessages(MODE_TIME_BREAK);
                    mHandler.sendMessage(Message.obtain(mHandler, mode));
                    vh.icon.setImageResource(R.drawable.ic_mission_create);
                    if (currentMode != MODE_NORMAL) {
                        //从休息转到开始，意味着下一组了，则当前组的休息时间更新
                        isLast = updatePinnerText(currentPinner);
                    }
                }
                break;
            case MODE_TIME_BREAK:
                if (currentMode != MODE_TIME_BREAK) {
                    mHandler.removeMessages(MODE_TIME_COUNT);
                    mHandler.sendMessage(Message.obtain(mHandler, mode));
                    vh.icon.setImageResource(R.drawable.ic_mission_wait);
                }
                break;
            case MODE_NORMAL:
                mHandler.removeMessages(MODE_TIME_COUNT);
                mHandler.removeMessages(MODE_TIME_BREAK);
                break;
            default:
                break;
        }
        if (!isLast)
            currentMode = mode;
    }

    /**
     * 更新UI
     *
     * @param mode
     */
    public synchronized void updateText(int mode) {
        switch (mode) {
            case MODE_TIME_COUNT:
                times[0]++;
                vh.tx_time.setText("用时:" + times[0] + "s");
                break;
            case MODE_TIME_BREAK:
                times[1]++;
                vh.tx_brerak.setText("休息:" + times[1] + "s");
                break;
            default:
                break;
        }
    }

    /**
     * 更新休息时间
     *
     * @param currentPinner
     */
    boolean updatePinnerText(int currentPinner) {
        boolean isTheLast = false;
        View v = vh.groups_layout.getChildAt(currentPinner);
        if (v != null) {
            TextView breakTime = (TextView) v.findViewById(R.id.break_time);
            if (breakTime != null) {
                breakTime.setText("休息:" + mHandler.getBreakTime() + "s");
                if (currentPinner < pinnerSize) {
                    this.currentPinner++;
                    if (this.currentPinner == pinnerSize) {
                        cleanTimer();
                        ToastUtils.ToastMessage(this, "已经最后一组了哦，恭喜你达到了预期目标");
                        currentMode = MODE_NORMAL;
                        isTheLast = true;
                    }
                }
            }

            Spinner pinner = (Spinner) v.findViewById(R.id.group);
            if (pinner != null) {
                postValue.add(new Bean(pinner.getSelectedItem().toString().trim(), String.valueOf(mHandler.getBreakTime())));
            }

        }
        return isTheLast;
    }

    void cleanTimer() {
        if (mHandler != null) {
            mHandler.removeMessages(MODE_TIME_COUNT);
            mHandler.removeMessages(MODE_TIME_BREAK);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (currentPinner == pinnerSize) {
                    ToastUtils.ToastMessage(this, "最后一组了哦");
                } else {
                    updateTimer(MODE_TIME_COUNT, TIME_START);
                }
                break;
            case R.id.btn_break:
                if (currentPinner == pinnerSize) {
                    ToastUtils.ToastMessage(this, "最后一组了哦");
                } else {
                    updateTimer(MODE_TIME_BREAK, TIME_START);
                }
                break;
            case R.id.btn_add:
                generatePinner();
                break;
            case R.id.btn_finish:
                submit();
                break;
        }

    }

    /**
     * 提交
     */
    private void submit() {
        long trainId= missionInfo.trainingID;
        int time=times[0];

        String items="";
        if (postValue.size()<=0){
            ToastUtils.ToastMessage(this,"您还没开始任何训练哦，开始后再提交");
            return;
        }
        for (int i = 0; i < postValue.size(); i++) {
            if (i!=postValue.size()-1){
                items+=postValue.get(i).result+",";
            }else {
                items+=postValue.get(i).result;
            }
        }

        missionFinishRequest.training_id=trainId;
        missionFinishRequest.consum_time=time;
        missionFinishRequest.training_items=items;
        missionFinishRequest.post(true);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        finish();
    }

    //=============================================================other class
    static class ViewHolder {
        public View rootView;
        public TextView tx_mission_target;
        public ImageView icon;
        public View line_v;
        public TextView tx_time;
        public TextView tx_brerak;
        public LinearLayout groups_layout;
        public Button btn_add;
        public Button btn_break;
        public Button btn_start;
        public Button btn_finish;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tx_mission_target = (TextView) rootView.findViewById(R.id.tx_mission_target);
            this.icon = (ImageView) rootView.findViewById(R.id.icon);
            this.line_v = (View) rootView.findViewById(R.id.line_v);
            this.tx_time = (TextView) rootView.findViewById(R.id.tx_time);
            this.tx_brerak = (TextView) rootView.findViewById(R.id.tx_brerak);
            this.groups_layout = (LinearLayout) rootView.findViewById(R.id.groups_layout);
            this.btn_add = (Button) rootView.findViewById(R.id.btn_add);
            this.btn_break = (Button) rootView.findViewById(R.id.btn_break);
            this.btn_start = (Button) rootView.findViewById(R.id.btn_start);
            this.btn_finish = (Button) rootView.findViewById(R.id.btn_finish);
        }

    }

    /**
     * 静态内部handler，防止内存泄漏
     */
    static class WeakHandler extends Handler {
        private final WeakReference<MissionDetailActivity> mContext;

        private int action = -1;

        private int breakTime = 0;

        public WeakHandler(MissionDetailActivity activity) {
            mContext = new WeakReference<MissionDetailActivity>(activity);
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public int getBreakTime() {
            return breakTime;
        }

        @Override
        public void handleMessage(Message msg) {
            MissionDetailActivity activity = mContext.get();
            if (activity != null) {
                switch (msg.what) {
                    case MODE_TIME_COUNT:
                        if (action == TIME_START) {
                            sendMessageDelayed(Message.obtain(this, MODE_TIME_COUNT), 1000);
                            breakTime = 0;
                            activity.updateText(MODE_TIME_COUNT);
                        }
                        if (action == TIME_STOP) {
                            activity.updateText(MODE_TIME_BREAK);
                        }
                        break;
                    case MODE_TIME_BREAK:
                        if (action == TIME_START) {
                            sendMessageDelayed(Message.obtain(this, MODE_TIME_BREAK), 1000);
                            breakTime++;
                            activity.updateText(MODE_TIME_BREAK);
                        }
                        if (action == TIME_STOP) {
                            activity.updateText(MODE_TIME_COUNT);
                        }
                        break;
                }
            }
        }
    }

    /**
     * 组数-时间
     */
    static class Bean {
        private String result;

        public Bean(String num, String breakTime) {
            result = num + "-" + breakTime;
        }

    }


}
