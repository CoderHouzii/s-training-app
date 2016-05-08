package songming.straing.ui.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import songming.straing.R;
import songming.straing.app.https.request.CircleListRequest;
import songming.straing.model.MomentsInfo;
import songming.straing.moments.base.adapter.CircleBaseAdapter;
import songming.straing.moments.base.adapter.FriendCircleAdapter;
import songming.straing.moments.item.ItemOnlyChar;
import songming.straing.ui.activity.base.BaseTableActivity;
import songming.straing.widget.ptrwidget.FriendCirclePtrListView;

/**
 * 圈
 */
public class CircleActivity extends BaseTableActivity<MomentsInfo> {

    private CircleListRequest circleListRequest;
    private ViewHolder vh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        initView();
        initReq();

    }

    private void initView() {
        vh = new ViewHolder(getWindow().getDecorView());
        CircleBaseAdapter.Builder<MomentsInfo> builder = new CircleBaseAdapter.Builder<MomentsInfo>(datas).addType(0, ItemOnlyChar.class).build();
        bindListView(R.id.list, null, new FriendCircleAdapter(this, builder));
    }

    private void initReq() {
        circleListRequest = new CircleListRequest();
        circleListRequest.setOnResponseListener(this);
        List<MomentsInfo> cache = circleListRequest.loadCache();
        if (cache != null && cache.size() > 0) {
            datas.clear();
            datas.addAll(cache);
            mAdapter.notifyDataSetChanged();
        }
        circleListRequest.execute();
    }

    @Override
    public void onPullDownRefresh() {
        circleListRequest.start=0;
        circleListRequest.execute();

    }

    @Override
    public void onLoadMore() {
        circleListRequest.execute();
    }

    static class ViewHolder {
        public View rootView;
        public FriendCirclePtrListView list;
        public EditText ed_input;
        public TextView btn_send;
        public LinearLayout ll_input;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.list = (FriendCirclePtrListView) rootView.findViewById(R.id.list);
            this.ed_input = (EditText) rootView.findViewById(R.id.ed_input);
            this.btn_send = (TextView) rootView.findViewById(R.id.btn_send);
            this.ll_input = (LinearLayout) rootView.findViewById(R.id.ll_input);
        }

    }
}
