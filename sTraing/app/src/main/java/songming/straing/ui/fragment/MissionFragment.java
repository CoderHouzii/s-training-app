package songming.straing.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import songming.straing.R;
import songming.straing.ui.fragment.base.BaseFragment;

/**
 * 创建任务
 */
public class MissionFragment extends BaseFragment{
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mission,container,false);
    }

    @Override
    public void bindData() {

    }
}
