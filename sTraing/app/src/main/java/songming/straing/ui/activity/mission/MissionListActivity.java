package songming.straing.ui.activity.mission;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.MissionListAdapter;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.MissionListRequest;
import songming.straing.model.MissionInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.TimeUtils;

/**
 * 任务列表
 */
public class MissionListActivity extends BaseActivity {

    private ListView list;
    private long userid;
    private MissionListAdapter mAdapter;
    private List<Object> datas;
    private MissionListRequest mMissionListRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_list);

        userid = getData();
        if (userid == 0) {
            finish();
            return;
        }

        initView();

    }

    private long getData() {
        long id = getIntent().getLongExtra("userid", 0);
        return id;
    }

    private void initView() {
        list = (ListView) findViewById(R.id.list);
        datas = new ArrayList<>();
        mAdapter = new MissionListAdapter(this, datas);
        list.setAdapter(mAdapter);

        initReq();
    }

    private void initReq() {
        mMissionListRequest = new MissionListRequest();
        mMissionListRequest.setOnResponseListener(this);
        mMissionListRequest.userid = LocalHost.INSTANCE.getUserId();
        changeData(mMissionListRequest.loadCache(LocalHost.INSTANCE.getUserId()));
        mAdapter.notifyDataSetChanged();
        mMissionListRequest.execute();

    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            changeData((List<MissionInfo>) response.getData());
            mAdapter.notifyDataSetChanged();
        }
    }

    // 将infos转化为符合adapter的list<object>
    void changeData(List<MissionInfo> infos) {
        if (infos == null || infos.size() <= 0) return;
        if (datas.size() > 0) datas.clear();
        boolean hasAddTitle = false;
        for (int i = 0; i < infos.size(); i++) {
            if (i == 0) {
                datas.add(new Object());
            }
            //如果是今天的任务
            if (!TimeUtils.isLastDay(infos.get(i).createAt)) {
                datas.add(infos.get(i));
            } else {
                //如果不是今天的任务
                //先添加空对象标明这是标题
                if (!hasAddTitle) {
                    datas.add(new Object());
                    hasAddTitle = true;
                }
                datas.add(infos.get(i));
            }
        }

    }
}
