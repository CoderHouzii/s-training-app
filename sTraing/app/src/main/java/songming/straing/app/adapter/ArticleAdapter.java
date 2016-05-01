package songming.straing.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.base.MBaseAdapter;
import songming.straing.app.adapter.base.viewholder.MViewHolder;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.widget.SuperImageView;

/**
 * 文章adapter
 */
public class ArticleAdapter extends MBaseAdapter<ArticleDetailInfo, ArticleAdapter.ViewHolder> {




    public ArticleAdapter(Context context, List<ArticleDetailInfo> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_article;
    }

    @Override
    public ViewHolder initViewHolder() {
        return new ViewHolder();
    }

    @Override
    public void onBindView(int position, ArticleDetailInfo data, ViewHolder holder) {
        holder.article_avatar.loadImageDefault(data.user.avatar);
        holder.title.setText(data.title);
        holder.content.setText(data.content);

    }

    static class ViewHolder implements MViewHolder {
        public SuperImageView article_avatar;
        public TextView title;
        public TextView content;

        @Override
        public void onInFlate(View v) {
            article_avatar= (SuperImageView) v.findViewById(R.id.article_avatar);
            title= (TextView) v.findViewById(R.id.title);
            content= (TextView) v.findViewById(R.id.content);
        }
    }
}
