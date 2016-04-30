package songming.straing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import songming.straing.R;
import songming.straing.app.https.request.CreateArticleRequest;
import songming.straing.model.MissionInfo;
import songming.straing.ui.activity.article.ArticleActivity;
import songming.straing.ui.activity.index.MainActivity;
import songming.straing.ui.activity.login.LoginActivity;
import songming.straing.ui.activity.login.RegisterActivity;
import songming.straing.ui.activity.mission.MissionDetailActivity;
import songming.straing.ui.activity.person.AvatarSettingActivity;
import songming.straing.ui.activity.person.NickAndSignatureSettingActivity;
import songming.straing.ui.activity.person.PersonIndexActivity;
import songming.straing.ui.activity.person.PersonSettingActivity;

/**
 * ui工具类
 */
public class UIHelper {
    /**
     * dip转px
     */
    public static int dipToPx(Context context, float dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dip
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕分辨率：宽
     */
    public static int getScreenPixWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕分辨率：高
     */
    public static int getScreenPixHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //=============================================================START TO ACTIVITY METHODS

    /**
     * 跳转到登录页面
     */
    public static void startToLoginActivity(Activity c) {
        Intent intent = new Intent(c, LoginActivity.class);
        c.startActivity(intent);
        c.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

    }

    /**
     * 跳转到注册页面
     */
    public static void startToRegisterActivity(Activity c) {
        Intent intent = new Intent(c, RegisterActivity.class);
        c.startActivity(intent);
    }

    /**
     * 跳转到主页
     */
    public static void startToMainActivity(Activity c) {
        Intent intent = new Intent(c, MainActivity.class);
        c.startActivity(intent);
        c.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        c.finish();
    }

    /**
     * 跳转到个人资料页
     */
    public static void startToPersonSettingActivity(Activity c, int requestCode) {
        Intent intent = new Intent(c, PersonSettingActivity.class);
        c.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到头像设置
     */
    public static void startToAvatarSettingActivity(Activity c, int requestCode) {
        Intent intent = new Intent(c, AvatarSettingActivity.class);
        c.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到昵称设置
     */
    public static void startToNickSettingActivity(Activity c, @NickAndSignatureSettingActivity.Mode
    int mode, String text, int requestCode) {
        Intent intent = new Intent(c, NickAndSignatureSettingActivity.class);
        intent.putExtra("mode", mode);
        switch (mode) {
            case NickAndSignatureSettingActivity.MODE_NICK:
                intent.putExtra("nick", text);
                break;
            case NickAndSignatureSettingActivity.MODE_SIGNATURE:
                intent.putExtra("signature", text);
                break;
            default:
                break;
        }
        c.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到个人主页
     */
    public static void startToPersonIndexActivity(Activity c, long userid) {
        Intent intent = new Intent(c, PersonIndexActivity.class);
        intent.putExtra("userid", userid);
        c.startActivity(intent);
    }

    /**
     * 跳转到任务详情主页
     */
    public static void startToMissionDetailActivity(Activity c, @NonNull MissionInfo info) {
        Intent intent = new Intent(c, MissionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MissionDetailActivity.INFO, info);
        intent.putExtra(MissionDetailActivity.BUNDLE_NAME, bundle);
        c.startActivityForResult(intent, 150);
    }

    /**
     * 跳转到创建文章
     */
    public static void startToCreateArticleActivity(Activity c) {
        Intent intent = new Intent(c, ArticleActivity.class);
        c.startActivityForResult(intent,119);
    }
}
