package songming.straing.widget.commentwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import songming.straing.model.ArticleCommentInfo;
import songming.straing.model.CommentInfo;

/**
 * 评论控件
 */
public class CommentWidget extends TextView {
    private static final String TAG = "CommentWidget";
    //用户名颜色
    private int textColor = 0xff517fae;
    private static final int textSize = 14;
    SpannableStringBuilderAllVer mSpannableStringBuilderAllVer;

    public CommentWidget(Context context) {
        this(context, null);
    }

    public CommentWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(LinkMovementMethod.getInstance());
        setOnTouchListener(new ClickableSpanEx.ClickableSpanSelector());
        this.setHighlightColor(0x00000000);
        setTextSize(textSize);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setMovementMethod(LinkMovementMethod.getInstance());
        this.setHighlightColor(0x00000000);
        setTextSize(textSize);
    }

    public void setCommentText(CommentInfo info) {
        if (info == null) return;
        try {
            setTag(info);
            createCommentStringBuilder(info);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "虽然在下觉得不可能会有这个情况，但还是捕捉下吧，万一被打脸呢。。。");
        }
    }

    private void createCommentStringBuilder(@NonNull CommentInfo info) {
        if (mSpannableStringBuilderAllVer == null) {
            mSpannableStringBuilderAllVer = new SpannableStringBuilderAllVer();
        } else {
            mSpannableStringBuilderAllVer.clear();
            mSpannableStringBuilderAllVer.clearSpans();
        }
        String content = "： " + info.text + "\0";
        boolean isApply = (info.replyUser == null);
        // 用户B为空，证明是一条原创评论
        if (info.user != null && isApply) {
            CommentClick userA = new CommentClick.Builder(getContext(), info.user).setColor(0xff517fae)
                    .setClickEventColor(0xffc6c6c6)
                    .setTextSize(textSize)
                    .build();
            mSpannableStringBuilderAllVer.append(info.user.username, userA, 0);
            mSpannableStringBuilderAllVer.append(content);
        } else if (info.replyUser != null && !isApply) {
            //用户A，B不空，证明是回复评论
            CommentClick userA = new CommentClick.Builder(getContext(), info.user).setColor(0xff517fae)
                    .setClickEventColor(0xffc6c6c6)
                    .setTextSize(textSize)
                    .build();
            mSpannableStringBuilderAllVer.append(info.user.username, userA, 0);
            mSpannableStringBuilderAllVer.append("回复");
            CommentClick userB = new CommentClick.Builder(getContext(), info.replyUser).setColor(0xff517fae)
                    .setClickEventColor(0xffc6c6c6)
                    .setTextSize(textSize)
                    .build();
            mSpannableStringBuilderAllVer.append(info.replyUser.username, userB, 0);
            mSpannableStringBuilderAllVer.append(content);
        }
        setText(mSpannableStringBuilderAllVer);
    }

    public CommentInfo getData() throws ClassCastException {
        return (CommentInfo) getTag();
    }
}
