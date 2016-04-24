package songming.straing.ui.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import songming.straing.R;
import songming.straing.ui.activity.base.BaseActivity;

/**
 * 设置昵称/签名
 */
public class NickAndSignatureSettingActivity extends BaseActivity {

    private String preNick;
    private EditText nick;

    private String preSignature;

    private int mode;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ MODE_NICK, MODE_SIGNATURE })
    public @interface Mode {}

    public static final int MODE_NICK = 0x20;
    public static final int MODE_SIGNATURE = 0x21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_setting);

        mode = getIntent().getIntExtra("mode", 0);
        if (mode == 0) finish();
        if (mode == MODE_SIGNATURE) {
            preSignature = getIntent().getStringExtra("signature");
        }
        else if (mode == MODE_NICK) preNick = getIntent().getStringExtra("nick");
        initView();
    }

    private void initView() {
        nick = (EditText) findViewById(R.id.nick);
        switch (mode) {
            case MODE_NICK:
                if (!TextUtils.isEmpty(preNick)) {
                    nick.setHint(preNick);
                }
                break;
            case MODE_SIGNATURE:
                if (!TextUtils.isEmpty(preSignature)) {
                    nick.setHint(preSignature);
                }
                break;
        }
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        String result = submit();
        Intent intent = new Intent();
        if (mode == MODE_NICK) {
            intent.putExtra("nick", result);
            setResult(101, intent);
        }
        else if (mode == MODE_SIGNATURE) {
            intent.putExtra("signature", result);
            setResult(102, intent);
        }

        finish();
        super.onBackPressed();
    }

    String nickStr;
    String signature;

    private String submit() {
        // validate
        switch (mode) {
            case MODE_NICK:
                nickStr = nick.getText().toString().trim();
                if (TextUtils.isEmpty(nickStr)) {
                    return preNick;
                }
                else {
                    return nickStr;
                }
            case MODE_SIGNATURE:
                signature = nick.getText().toString().trim();
                if (TextUtils.isEmpty(signature)) {
                    return preSignature;
                }
                else {
                    return signature;
                }
            default:
                break;
        }
        return "未设置";
    }
}
