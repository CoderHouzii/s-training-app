package songming.straing.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;

import razerdp.basepopup.BasePopupWindow;
import songming.straing.R;

/**
 * 文章操作
 */
public class ArticleActionPopup extends BasePopupWindow implements View.OnClickListener {

    public ArticleActionPopup(Activity context) {
        super(context);
        findViewById(R.id.article_action).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
    }

    @Override
    protected Animation getShowAnimation() {
        return getTranslateAnimation(300, 0, 400);
    }

    @Override
    protected View getClickToDismissView() {
        return mPopupView;
    }

    @Override
    public Animation getExitAnimation() {
        return getTranslateAnimation(0, 300, 400);
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_article);
    }

    @Override
    public View getAnimaView() {
        return findViewById(R.id.popup_parent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_action:
                if (mListener != null)
                    mListener.onArticleActionClick(v);

                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }


    private OnArticleActionClickListener mListener;

    public OnArticleActionClickListener getOnArticleActionClickListener() {
        return mListener;
    }

    public void setOnArticleActionClickListener(OnArticleActionClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnArticleActionClickListener {
        void onArticleActionClick(View v);
    }
}
