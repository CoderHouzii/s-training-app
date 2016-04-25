package songming.straing.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import com.daimajia.numberprogressbar.NumberProgressBar;
import razerdp.basepopup.BasePopupWindow;
import songming.straing.R;

/**
 * 上传文件的popup
 */
public class ProgressPopup extends BasePopupWindow {
    private NumberProgressBar mNumberProgressBar;

    public ProgressPopup(Activity context) {
        super(context);
        mNumberProgressBar = (NumberProgressBar) mPopupView.findViewById(R.id.number_progress);
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return null;
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_progress);
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.popup_parent);
    }

    public void setProgress(int curProgress) {
        mNumberProgressBar.setProgress(curProgress);
    }

    static class SpringInterPolator extends LinearInterpolator {
        private float factor = 0.6f;

        public SpringInterPolator() {
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }
}
