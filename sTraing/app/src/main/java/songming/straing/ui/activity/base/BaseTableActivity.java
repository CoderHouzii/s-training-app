package songming.straing.ui.activity.base;

import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.utils.ToastUtils;
import songming.straing.widget.ptrwidget.FriendCirclePtrListView;
import songming.straing.widget.ptrwidget.OnLoadMoreRefreshListener;
import songming.straing.widget.ptrwidget.OnPullDownRefreshListener;
import songming.straing.widget.ptrwidget.PullMode;

/**
 * activity(分页)基类
 */
public abstract class BaseTableActivity<T> extends BaseActivity implements BaseResponseListener {

    protected FriendCirclePtrListView mListView;
    protected List<T> datas = new ArrayList<>();
    protected BaseAdapter mAdapter;

    private boolean needReset = false;


    public void bindListView(int listResId, View headerView, BaseAdapter adapter) {
        this.mAdapter = adapter;
        mListView = (FriendCirclePtrListView) findViewById(listResId);
        if (headerView != null) mListView.addHeaderView(headerView);
        mListView.setAdapter(adapter);

        mListView.setOnPullDownRefreshListener(new OnPullDownRefreshListener() {
            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                onPullDownRefresh();
            }
        });
        mListView.setOnLoadMoreRefreshListener(new OnLoadMoreRefreshListener() {
            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                onLoadMore();
            }
        });
    }

    public void bindListView(int listResId, View headerView, boolean canLoadMore, BaseAdapter adapter) {
        this.mAdapter = adapter;
        mListView = (FriendCirclePtrListView) findViewById(listResId);
        if (headerView != null) mListView.addHeaderView(headerView);
        mListView.setAdapter(adapter);

        mListView.setOnPullDownRefreshListener(new OnPullDownRefreshListener() {
            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                onPullDownRefresh();
            }
        });
        if (canLoadMore)
            mListView.setOnLoadMoreRefreshListener(new OnLoadMoreRefreshListener() {
                @Override
                public void onRefreshing(PtrFrameLayout frame) {
                    onLoadMore();
                }
            });
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onStop(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        ToastUtils.ToastMessage(this, "网络出错。。。。");
        if (mListView != null) {
            mListView.refreshComplete();
            if (mListView.getCurMode() == PullMode.FROM_BOTTOM) {
                mListView.loadmoreCompelete();
            }
        }
    }

    public boolean isNeedReset() {
        return needReset;
    }

    public void setNeedReset(boolean needReset) {
        this.needReset = needReset;
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 0) {
            mListView.refreshComplete();
            if (mListView != null && mListView.getCurMode() == PullMode.FROM_BOTTOM) {
                mListView.loadmoreCompelete();
            }
            ToastUtils.ToastMessage(this, response.getErrorMsg());
        }
        if (response.getRequestType() == 0) {
            if (response.getStatus() == 1) {
                List<T> newDatas = (List<T>) response.getData();
                if (mListView != null && mListView.getCurMode() == PullMode.FROM_START || needReset) {
                    datas.clear();
                    needReset = false;
                }
                mListView.setHasMore(response.getHasMore());
                datas.addAll(newDatas);
                if (mListView != null && mListView.getCurMode() == PullMode.FROM_BOTTOM) {
                    mListView.loadmoreCompelete();
                }
                mListView.refreshComplete();
                mAdapter.notifyDataSetChanged();
            } else {
                mListView.refreshComplete();
                if (mListView != null && mListView.getCurMode() == PullMode.FROM_BOTTOM) {
                    mListView.loadmoreCompelete();
                }
                ToastUtils.ToastMessage(this, response.getErrorMsg());
            }
        }
    }

    //=============================================================下拉操作/底部加载
    public abstract void onPullDownRefresh();

    public abstract void onLoadMore();
}
