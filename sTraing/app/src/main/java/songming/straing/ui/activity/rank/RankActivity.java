package songming.straing.ui.activity.rank;

import android.os.Bundle;

import songming.straing.R;
import songming.straing.app.adapter.RankAdapter;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.RankListRequest;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.activity.base.BaseTableActivity;

/**
 * 排行榜
 */
public class RankActivity extends BaseTableActivity<UserDetailInfo> {
    private RankListRequest mListRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        initView();
        initReq();
    }

    private void initReq() {
        mListRequest = new RankListRequest();
        mListRequest.setOnResponseListener(this);
        mListRequest.execute();
    }


    private void initView() {
        bindListView(R.id.list, null, false,new RankAdapter(this, datas));
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
    }

    @Override
    public void onPullDownRefresh() {
        mListRequest.execute();

    }

    @Override
    public void onLoadMore() {
    }
}
