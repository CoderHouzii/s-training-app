package songming.straing.ui.activity.person;

import android.os.Bundle;
import songming.straing.R;
import songming.straing.ui.activity.base.BaseActivity;

/**
 * 个人资料页
 */
public class PersonSettingActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
    }

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        finish();
    }
}
