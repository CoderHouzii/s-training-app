package songming.straing.ui.activity.article;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import songming.straing.R;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.CreateArticleRequest;
import songming.straing.ui.activity.base.BaseActivity;

/**
 * 文章
 */
public class ArticleActivity extends BaseActivity {

    private EditText title;
    private EditText content;

    private CreateArticleRequest articleRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();

        articleRequest = new CreateArticleRequest();
        articleRequest.setOnResponseListener(this);
    }

    private void initView() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
    }

    @Override
    protected void onTitleRightClick(View v) {
        submit();
    }

    private void submit() {
        // validate
        String titleStr = title.getText().toString().trim();
        if (TextUtils.isEmpty(titleStr)) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }

        String contentStr = content.getText().toString().trim();
        if (TextUtils.isEmpty(contentStr)) {
            Toast.makeText(this, "请输入正文内容", Toast.LENGTH_SHORT).show();
            return;
        }

        articleRequest.title = titleStr;
        articleRequest.content = contentStr;
        articleRequest.post(true);

    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            finish();
        }
    }
}
