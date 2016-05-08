package songming.straing.ui.activity.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.ArticleAdapter;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.ArticleListRequest;
import songming.straing.app.https.request.ArticleRecommedRequest;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.ui.activity.base.BaseTableActivity;
import songming.straing.ui.activity.person.NickAndSignatureSettingActivity;
import songming.straing.utils.UIHelper;
import songming.straing.widget.ArticleActionPopup;

/**
 * 文章列表
 */
public class ArticleListActivity extends BaseTableActivity<ArticleDetailInfo> implements ArticleActionPopup.OnArticleActionClickListener {

    enum Mode {
        RECOMMED, USER
    }

    private Mode mode;

    public static final String ARTICLE_USER_ID = "article_user_id";
    private long userid = 0;
    private ArticleListRequest mListRequest;
    private ArticleRecommedRequest mRecommedRequest;

    private ArticleActionPopup mArticleActionPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        getData();
        initView();
        initReq();
    }

    private void getData() {
        userid = getIntent().getLongExtra(ARTICLE_USER_ID, 0);
        if (userid == 0) mode = Mode.RECOMMED;
        else mode = Mode.USER;
    }

    @Override
    protected void onTitleRightClick(View v) {
        UIHelper.startToCreateArticleActivity(this);
    }

    private int position;

    private void initView() {

        if (mode == Mode.RECOMMED) mTitleBar.setRightButtonVisibility(View.GONE);
        else {
            if (userid == LocalHost.INSTANCE.getUserId())
                mTitleBar.setRightButtonVisibility(View.VISIBLE);
            else
                mTitleBar.setRightButtonVisibility(View.GONE);
        }

        bindListView(R.id.list, null, new ArticleAdapter(this, datas));

        mArticleActionPopup = new ArticleActionPopup(this);
        mArticleActionPopup.setOnArticleActionClickListener(this);

        mListView.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleListActivity.this.position = position;
                mArticleActionPopup.showPopupWindow();
                return true;
            }
        });

        mListView.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long articleId = datas.get(position).articleID;
                UIHelper.startToArticleDetailActivity(ArticleListActivity.this, articleId);
            }
        });

    }

    private void initReq() {
        if (mode == Mode.USER) {
            mListRequest = new ArticleListRequest();
            mListRequest.setOnResponseListener(this);
            mListRequest.target_id = userid;
            List<ArticleDetailInfo> cache = mListRequest.loadCache(userid);
            if (cache != null && cache.size() > 0) {
                datas.clear();
                datas.addAll(cache);
                mAdapter.notifyDataSetChanged();
            }
            mListRequest.execute();
        } else {
            mRecommedRequest = new ArticleRecommedRequest();
            mRecommedRequest.setOnResponseListener(this);
            mRecommedRequest.execute();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            onPullDownRefresh();
        }
    }

    @Override
    public void onPullDownRefresh() {
        if (mode == Mode.USER) {
            mListRequest.start = 0;
            mListRequest.execute();
        } else {
            mRecommedRequest.start = 0;
            mRecommedRequest.execute();
        }
    }

    @Override
    public void onLoadMore() {
        if (mode == Mode.USER) {
            mListRequest.execute();
        } else {
            mRecommedRequest.execute();
        }
    }

    @Override
    public void onUploadSuccess(BaseResponse response) {
        super.onUploadSuccess(response);
    }

    @Override
    public void onArticleActionClick(View v) {

    }
}
