package songming.straing.ui.activity.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import songming.straing.R;
import songming.straing.app.adapter.ArticleAdapter;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.ArticleListRequest;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.ui.activity.base.BaseTableActivity;
import songming.straing.utils.UIHelper;

/**
 * 文章列表
 */
public class ArticleListActivity extends BaseTableActivity<ArticleDetailInfo> {

    public static final String ARTICLE_USER_ID = "article_user_id";
    private long userid = 0;
    private ArticleListRequest mListRequest;

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
        if (userid == 0) finish();
    }

    @Override
    protected void onTitleRightClick(View v) {
        UIHelper.startToCreateArticleActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==300){
            onPullDownRefresh();
        }
    }

    private void initReq() {
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
    }


    private void initView() {
        bindListView(R.id.list,null,new ArticleAdapter(this,datas));
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
    }

    @Override
    public void onPullDownRefresh() {
        mListRequest.start=0;
        mListRequest.execute();

    }

    @Override
    public void onLoadMore() {
        mListRequest.execute();
    }


}
