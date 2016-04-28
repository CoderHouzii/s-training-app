package songming.straing.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import songming.straing.R;
import songming.straing.app.eventbus.Events;
import songming.straing.model.MissionInfo;
import songming.straing.ui.fragment.base.BaseFragment;

/**
 * 首页
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener {
    private TextView article;
    private TextView rank;
    private ListView list;

    private List<Object> datas=new ArrayList<>();

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

    }

    @Subscribe
    public void onEventMainThread(Events.RefreshMissionDetail event) {

    }

    @Override
    public void onClick(View v) {

    }
}
