package songming.straing.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.base.MBaseAdapter;
import songming.straing.app.adapter.base.viewholder.MViewHolder;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.model.UserDetailInfo;
import songming.straing.widget.CircleImageView;
import songming.straing.widget.SuperImageView;

/**
 * 排行榜adapter
 */
public class RankAdapter extends MBaseAdapter<UserDetailInfo, RankAdapter.ViewHolder> {


    public RankAdapter(Context context, List<UserDetailInfo> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_rank;
    }

    @Override
    public ViewHolder initViewHolder() {
        return new ViewHolder();
    }

    @Override
    public void onBindView(int position, UserDetailInfo data, ViewHolder holder) {
        holder.avatar.loadImageDefault(data.avatar);
        holder.username.setText(data.username);
        holder.score.setText("上周积分:"+data.score);

    }

    static class ViewHolder implements MViewHolder {
        public CircleImageView avatar;
        public TextView username;
        public TextView score;

        @Override
        public void onInFlate(View v) {
            avatar= (CircleImageView) v.findViewById(R.id.avatar);
            username= (TextView) v.findViewById(R.id.username);
            score= (TextView) v.findViewById(R.id.score);
        }
    }
}
