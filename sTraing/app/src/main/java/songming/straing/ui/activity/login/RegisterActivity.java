package songming.straing.ui.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import songming.straing.R;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.ToastUtils;
import songming.straing.widget.TitleBar;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar titlebar;
    private EditText user;
    private EditText pass;
    private Button register;
    private CheckBox agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(this);
        agree = (CheckBox) findViewById(R.id.agree);
        agree.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String userName = user.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            postError("用户名不能为空哦");
            return;
        }

        String passWord = pass.getText().toString().trim();
        if (TextUtils.isEmpty(passWord)) {
            postError("密码不能为空哦");
            return;
        }

        if (!agree.isChecked()){
            postError("您必须同意协议哦");
            return;
        }

        // TODO: 2016/4/15 注册请求

    }

    void postError(String text) {
        ToastUtils.ToastMessage(this, text);
    }

    @Override
    protected void onTitleLeftClick() {
        finish();
    }

    @Override
    public void onClick(View v) {
        submit();
    }
}
