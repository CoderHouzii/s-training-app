package songming.straing.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import songming.straing.R;
import songming.straing.ui.fragment.base.BaseFragment;

/**
 * 首页
 */
public class IndexFragment extends BaseFragment{
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_index,container,false);
    }

    @Override
    public void bindData() {

    }
}
