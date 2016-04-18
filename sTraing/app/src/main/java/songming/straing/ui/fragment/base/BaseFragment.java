package songming.straing.ui.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.interfaces.BaseResponseListener;

/**
 * fragment基类
 */
public abstract class BaseFragment<T> extends Fragment implements BaseResponseListener {
    private static final String TAG = "BaseFragment";
    protected View mView;

    protected Activity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = initView(inflater, container);

        bindData();
        return mView;
    }


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
    }

    public <V extends View> V getViewById(int resId) {
        try {
            if (mView != null) {
                return (V) mView.findViewById(resId);
            }
        } catch (ClassCastException e) {
            Log.d(TAG, "无法强转");
            e.printStackTrace();
        }
        return null;
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract void bindData();

    public String getTitle(){
        return "s-Training";
    }

}
