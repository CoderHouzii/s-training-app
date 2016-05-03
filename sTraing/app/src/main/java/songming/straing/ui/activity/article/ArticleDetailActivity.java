package songming.straing.ui.activity.article;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import songming.straing.R;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.ArticleDetailRequest;
import songming.straing.app.https.request.ArticlePraiseActionRequest;
import songming.straing.app.https.request.CreateArticleRequest;
import songming.straing.model.ArticleCommentInfo;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.TimeUtils;
import songming.straing.widget.commentwidget.CommentWidget;

/**
 * 文章详情
 */
public class ArticleDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG_ARTICLE_ID = "article_id";

    private ViewHolder vh;
    private ArticleDetailRequest mArticleDetailRequest;
    private ArticlePraiseActionRequest mPraiseRequest;

    private long articleID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        getData();
        vh = new ViewHolder(getWindow().getDecorView());
        setClickListener(this, vh.read_good, vh.read_bad);
        initReq();
    }

    private void getData() {
        articleID = getIntent().getLongExtra(TAG_ARTICLE_ID, 0);
        if (articleID == 0) finish();
    }

    private void initReq() {
        mArticleDetailRequest = new ArticleDetailRequest();
        mArticleDetailRequest.article_id = articleID;
        mArticleDetailRequest.setRequestType(20);
        mArticleDetailRequest.setOnResponseListener(this);
        mArticleDetailRequest.execute(true);

        mPraiseRequest = new ArticlePraiseActionRequest();
        mPraiseRequest.article_id = articleID;
        mPraiseRequest.setRequestType(10);
        mPraiseRequest.setOnResponseListener(this);
    }


    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            switch (response.getRequestType()) {
                case 20:
                    upgradeArticleView((ArticleDetailInfo) response.getData());
                    break;
                case 10:
                    mArticleDetailRequest.execute();
                    break;
            }
        }
    }

    private void upgradeArticleView(ArticleDetailInfo data) {
        if (data == null) return;
        String nick = data.user.username;
        String time = TimeUtils.longToString(TimeUtils.yyyy_MM_dd, data.createAt);
        vh.article_creator_nick.setText(nick + " - " + time);
        vh.article_content.setText(data.content);
        vh.read_count.setText(String.format(Locale.getDefault(), "阅读(%s)", data.readCount));
        vh.read_good.setText(String.format(Locale.getDefault(), "(%s)", data.likeCount));
        vh.read_bad.setText(String.format(Locale.getDefault(), "(%s)", data.dislikeCount));

        generateComment(data.comments);

    }

    private void generateComment(List<ArticleCommentInfo> comments) {
        if (vh.layout_comment.getChildCount() > 0) vh.layout_comment.removeAllViews();

        for (ArticleCommentInfo comment : comments) {
            CommentWidget commentWidget = new CommentWidget(this);
            commentWidget.setCommentText(comment);
            vh.layout_comment.addView(commentWidget, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_good:
                mPraiseRequest.type = 1;
                mPraiseRequest.execute(true);
                break;
            case R.id.read_bad:
                mPraiseRequest.type = 2;
                mPraiseRequest.execute(true);
                break;
        }
    }

    static class ViewHolder {
        public View rootView;
        public TextView article_creator_nick;
        public TextView article_content;
        public TextView read_count;
        public TextView read_good;
        public TextView read_bad;
        public LinearLayout layout_comment;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.article_creator_nick = (TextView) rootView.findViewById(R.id.article_creator_nick);
            this.article_content = (TextView) rootView.findViewById(R.id.article_content);
            this.read_count = (TextView) rootView.findViewById(R.id.read_count);
            this.read_good = (TextView) rootView.findViewById(R.id.read_good);
            this.read_bad = (TextView) rootView.findViewById(R.id.read_bad);
            this.layout_comment = (LinearLayout) rootView.findViewById(R.id.layout_comment);
        }

    }
}
