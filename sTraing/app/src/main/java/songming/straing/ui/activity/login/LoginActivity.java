package songming.straing.ui.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import songming.straing.R;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.ToastUtils;
import songming.straing.utils.UIHelper;

/**
 * 注册页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText user;
    private EditText pass;
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
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

        // TODO: 2016/4/15 登录请求
        UIHelper.startToMainActivity(this);

    }

    void postError(String text) {
        ToastUtils.ToastMessage(this, text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                submit();
                break;
            case R.id.register:
                UIHelper.startToRegisterActivity(this);
                break;
        }

    }
}
