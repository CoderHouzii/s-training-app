package songming.straing.ui.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import songming.straing.R;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.LoginRequest;
import songming.straing.app.socket.SocketService;
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

    private LoginRequest mLoginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initReq();
    }


    private void initView() {
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    private void initReq() {
        mLoginRequest=new LoginRequest();
        mLoginRequest.setOnResponseListener(this);
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


        mLoginRequest.phone=userName;
        mLoginRequest.password=passWord;
        mLoginRequest.post(true);



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

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus()==1) {
            boolean prepare= (boolean) response.getData();
            if (prepare) {
                SocketService.CallService(this, Config.SocketIntent.Types.CONNECT);
                UIHelper.startToMainActivity(this);
            }
        }
    }
}
