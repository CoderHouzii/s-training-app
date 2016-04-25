package songming.straing.ui.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import songming.straing.R;
import songming.straing.app.config.Config;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.RegisterRequest;
import songming.straing.app.socket.SocketService;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.ToastUtils;
import songming.straing.utils.UIHelper;
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

    private RegisterRequest mRegisterRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initReq();
    }

    private void initReq() {
        mRegisterRequest=new RegisterRequest();
        mRegisterRequest.setOnResponseListener(this);
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

        mRegisterRequest.phone=userName;
        mRegisterRequest.password=passWord;

        mRegisterRequest.post(true);


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
        switch (v.getId()){
            case R.id.register:
                submit();
                break;
            default:
                break;
        }

    }

    @Override
    public void onFailure(BaseResponse response) {
        super.onFailure(response);
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
