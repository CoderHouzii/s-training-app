package songming.straing.ui.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.utils.ToastUtils;
import songming.straing.widget.ProgressDialog;
import songming.straing.widget.ptrwidget.FriendCirclePtrListView;
import songming.straing.widget.ptrwidget.OnLoadMoreRefreshListener;
import songming.straing.widget.ptrwidget.OnPullDownRefreshListener;
import songming.straing.widget.ptrwidget.PullMode;

/**
 * fragment基类（分页）
 */
public abstract class BaseTableFragment<T> extends BaseFragment<T> implements BaseResponseListener {
    protected FriendCirclePtrListView mListView;
    protected List<T> datas = new ArrayList<>();
    protected BaseAdapter mAdapter;

    public void bindListView(int listResId, View headerView, BaseAdapter adapter) {
        this.mAdapter = adapter;
        mListView = getViewById(listResId);
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

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onStop(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        ToastUtils.ToastMessage(mContext, "网络出错。。。。");
        if (mListView != null) {
            mListView.refreshComplete();
            if (mListView.getCurMode() == PullMode.FROM_BOTTOM) {
                mListView.loadmoreCompelete();
            }
        }
    }


    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getRequestType() == 0) {
            if (response.getStatus() == 1) {
                List<T> newDatas = (List<T>) response.getData();
                if (mListView != null && mListView.getCurMode() == PullMode.FROM_START) {
                    datas.clear();
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
                ToastUtils.ToastMessage(mContext, response.getErrorMsg());
            }
        }
    }

    //=============================================================下拉操作/底部加载
    public abstract void onPullDownRefresh();

    public abstract void onLoadMore();
}
