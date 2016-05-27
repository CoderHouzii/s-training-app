package songming.straing.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.MissionListAdapter;
import songming.straing.app.config.LocalHost;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.MissionListRequest;
import songming.straing.model.MissionInfo;
import songming.straing.ui.fragment.base.BaseFragment;
import songming.straing.utils.TimeUtils;
import songming.straing.utils.UIHelper;

/**
 * 首页
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener {
    private TextView article;
    private TextView rank;
    private ListView list;


    private List<Object> datas;
    private MissionListAdapter mAdapter;

    private MissionListRequest mMissionListRequest;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void bindData() {
        article= (TextView) getViewById(R.id.article);
        rank= (TextView) getViewById(R.id.rank);
        list= (ListView) getViewById(R.id.list);

        article.setOnClickListener(this);
        rank.setOnClickListener(this);


        datas=new ArrayList<>();
        mAdapter=new MissionListAdapter(mContext,datas);
        list.setAdapter(mAdapter);

        initReq();

    }

    private void initReq() {
        mMissionListRequest=new MissionListRequest();
        mMissionListRequest.setOnResponseListener(this);
        mMissionListRequest.userid=LocalHost.INSTANCE.getUserId();
        changeData(mMissionListRequest.loadCache(LocalHost.INSTANCE.getUserId()));
        mAdapter.notifyDataSetChanged();
        mMissionListRequest.execute();

    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus()==1){
            changeData((List<MissionInfo>) response.getData());
            mAdapter.notifyDataSetChanged();
        }
    }

    // 将infos转化为符合adapter的list<object>
    void changeData(List<MissionInfo> infos){
        if (infos==null||infos.size()<=0)return;
        if (datas.size()>0)datas.clear();
        boolean hasAddTitle=false;
        for (int i = 0; i < infos.size(); i++) {
            if (i==0){
                datas.add(new Object());
            }
            //如果是今天的任务
            if (!TimeUtils.isLastDay(infos.get(i).createAt)){
                datas.add(infos.get(i));
            }else {
                //如果不是今天的任务
                //先添加空对象标明这是标题
                if (!hasAddTitle) {
                    datas.add(new Object());
                    hasAddTitle=true;
                }
                datas.add(infos.get(i));
            }
        }

    }

    @Subscribe
    public void onEventMainThread(Events.RefreshMissionDetail event) {
        mMissionListRequest.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rank:
                UIHelper.startToRankListActivity(mContext);
                break;
            case R.id.article:
                UIHelper.startToArticleListActivity(mContext);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==150){
            mMissionListRequest.execute();
        }
    }
}
