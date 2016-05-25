package songming.straing.ui.activity.circle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import songming.straing.R;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.CircleCreateRequest;
import songming.straing.ui.activity.base.BaseActivity;

/**
 * 创建动态
 */
public class CircleCreateActivity extends BaseActivity {
    private EditText content;
    private CircleCreateRequest circleCreateRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_create);
        initView();
        circleCreateRequest = new CircleCreateRequest();
        circleCreateRequest.setOnResponseListener(this);
    }

    private void initView() {
        content = (EditText) findViewById(R.id.content);
    }

    @Override
    protected void onTitleRightClick(View v) {
        super.onTitleRightClick(v);
        submit();
    }

    private void submit() {
        // validate
        String contentStr = content.getText().toString().trim();
        if (TextUtils.isEmpty(contentStr)) {
            Toast.makeText(this, "content不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        circleCreateRequest.content = contentStr;
        circleCreateRequest.post(true);

    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus()==1){
            setResult(100);
            finish();
        }
    }
}
