package songming.straing.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import razerdp.basepopup.BasePopupWindow;
import songming.straing.R;
import songming.straing.utils.UIHelper;

/**
 * 转发输入
 */
public class SharePopup extends BasePopupWindow implements View.OnClickListener {


    private EditText input;
    private Button cancel;
    private Button ok;

    public SharePopup(Activity context) {
        super(context);

        input = (EditText) findViewById(R.id.input);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);

        setViewClickListener(this, cancel, ok);

        setAdjustInputMethod(true);
        setAutoShowInputMethod(true);
    }

    @Override
    protected Animation getShowAnimation() {
        return getDefaultScaleAnimation();
    }


    @Override
    protected View getClickToDismissView() {
        return null;
    }

    @Override
    public View getInputView() {
        return input;
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_share_input);
    }

    @Override
    public View getAnimaView() {
        return findViewById(R.id.popup_parent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                input.setText("");
                dismiss();
                break;
            case R.id.ok:
                if (onOkButtonClickEvent != null) {
                    String text = input.getText().toString().trim();
                    onOkButtonClickEvent.onTextGet(text);
                    UIHelper.hideInputMethod(input);
                }
                input.setText("");
                dismiss();
                break;
        }
    }

    private OnOkButtonClickEvent onOkButtonClickEvent;

    public OnOkButtonClickEvent getOnOkButtonClickEvent() {
        return onOkButtonClickEvent;
    }

    public void setOnOkButtonClickEvent(OnOkButtonClickEvent onOkButtonClickEvent) {
        this.onOkButtonClickEvent = onOkButtonClickEvent;
    }

    public interface OnOkButtonClickEvent {
        void onTextGet(String content);
    }
}
