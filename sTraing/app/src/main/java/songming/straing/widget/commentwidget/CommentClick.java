package songming.straing.widget.commentwidget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.View;
import android.widget.Toast;

import songming.straing.model.UserDetailInfo;
import songming.straing.model.UserInfo;
import songming.straing.utils.UIHelper;


/**
 * 评论点击事件
 */
public class CommentClick extends ClickableSpanEx {
    private Context mContext;
    private int textSize;
    private UserDetailInfo mUserInfo;

    private CommentClick() {}

    private CommentClick(Builder builder) {
        super(builder.color,builder.clickEventColor);
        mContext = builder.mContext;
        mUserInfo = builder.mUserInfo;
        this.textSize = builder.textSize;
    }

    @Override
    public void onClick(View widget) {
        if (mUserInfo!=null){
            UIHelper.startToPersonIndexActivity(mContext,mUserInfo.userID);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setTextSize(textSize);
    }

    public static class Builder {
        private int color;
        private Context mContext;
        private int textSize=16;
        private UserDetailInfo mUserInfo;
        private int clickEventColor;

        public Builder(Context context, @NonNull UserDetailInfo info) {
            mContext = context;
            mUserInfo=info;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = UIHelper.sp2px(mContext,textSize);
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setClickEventColor(int color){
            this.clickEventColor=color;
            return this;
        }

        public CommentClick build() {
            return new CommentClick(this);
        }
    }
}
