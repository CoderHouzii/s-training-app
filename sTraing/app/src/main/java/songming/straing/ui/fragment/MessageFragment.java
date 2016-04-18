package songming.straing.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import songming.straing.R;
import songming.straing.ui.fragment.base.BaseFragment;

/**
 * 消息
 */
public class MessageFragment extends BaseFragment{
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_message,container,false);
    }

    @Override
    public void bindData() {

    }

    @Override
    public String getTitle() {
        return "消息列表";
    }
}
