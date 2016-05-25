package songming.straing.widget.praisewidget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.View;
import android.widget.Toast;

import songming.straing.model.UserDetailInfo;
import songming.straing.model.UserInfo;
import songming.straing.utils.UIHelper;
import songming.straing.widget.span.ClickableSpanEx;


/**
 * 点击事件
 */
public class PraiseClick extends ClickableSpanEx {
    private static final int DEFAULT_COLOR = 0xff517fae;
    private int color;
    private Context mContext;
    private int textSize;
    private UserDetailInfo mPraiseInfo;

    private PraiseClick() {
    }

    private PraiseClick(Builder builder) {
        super(builder.color, builder.clickBgColor);
        mContext = builder.mContext;
        mPraiseInfo = builder.mPraiseInfo;
        this.textSize = builder.textSize;
    }

    @Override
    public void onClick(View widget) {
        if (mPraiseInfo != null)
            UIHelper.startToPersonIndexActivity(mContext, mPraiseInfo.userID);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setTextSize(textSize);
    }


    public static class Builder {
        private int color;
        private Context mContext;
        private int textSize = 16;
        private UserDetailInfo mPraiseInfo;
        private int clickBgColor;

        public Builder(Context context, @NonNull UserDetailInfo info) {
            mContext = context;
            mPraiseInfo = info;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = UIHelper.sp2px(mContext, textSize);
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setClickEventColor(int color) {
            this.clickBgColor = color;
            return this;
        }

        public PraiseClick build() {
            return new PraiseClick(this);
        }
    }
}
