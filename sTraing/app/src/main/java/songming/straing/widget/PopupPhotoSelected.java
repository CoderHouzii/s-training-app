package songming.straing.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import razerdp.basepopup.BasePopupWindow;
import songming.straing.R;
import songming.straing.utils.CameraUtils;

/**
 * 选择相机popup
 */
public class PopupPhotoSelected extends BasePopupWindow implements View.OnClickListener {
    public PopupPhotoSelected(Activity context) {
        super(context);
        mPopupView.findViewById(R.id.from_camera).setOnClickListener(this);
        mPopupView.findViewById(R.id.from_album).setOnClickListener(this);

    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    public Animator getShowAnimator() {
       return getDefaultSlideFromBottomAnimationSet();
    }

    @Override
    protected View getClickToDismissView() {
        return mPopupView;
    }

    @Override
    public Animator getExitAnimator() {
        AnimatorSet set = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            set = new AnimatorSet();

            if (mAnimaView != null) {
                set.playTogether(
                        ObjectAnimator.ofFloat(mAnimaView, "translationY", 0, 250).setDuration(400),
                        ObjectAnimator.ofFloat(mAnimaView, "alpha", 1.0f, 0).setDuration(250 * 3 / 2));
            }
        }
        return set;
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_camera);
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.popup_parent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.from_camera:
                //从相机读取
                CameraUtils.getPhotoFromCamera(mContext);
                dismiss();
                break;
            case R.id.from_album:
                //从相册读取
                CameraUtils.getPhotoFromAlbum(mContext);
                dismiss();
                break;
        }

    }
}
