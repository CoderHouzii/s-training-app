package songming.straing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import songming.straing.R;
import songming.straing.utils.ResourceUtils;

/**
 * 顶部标题栏
 */
public class TitleBar extends FrameLayout implements View.OnClickListener {
    private Context mContext;

    private ImageView leftButton;
    private TextView title;
    private Button rightButton;

    private OnLeftBtnClickListener mOnLeftBtnClickListener;
    private OnRightBtnClickListener mOnRightBtnClickListener;

    private String titleText;
    private String rightBtnText;
    private int leftSrc;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        titleText = a.getString(R.styleable.TitleBar_titleText);
        rightBtnText = a.getString(R.styleable.TitleBar_right_btn_text);
        leftSrc = a.getResourceId(R.styleable.TitleBar_left_btn_src, R.drawable.ic_arrow_back);
        a.recycle();

        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.widget_title_bar, this);
        leftButton = (ImageView) findViewById(R.id.btn_title_left);
        title = (TextView) findViewById(R.id.tx_title);
        rightButton = (Button) findViewById(R.id.btn_title_right);

        leftButton.setImageResource(leftSrc);
        title.setText(titleText);
        rightButton.setText(rightBtnText);

        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_title_left:
                if (mOnLeftBtnClickListener != null) {
                    mOnLeftBtnClickListener.onClick(v);
                }
                break;
            case R.id.btn_title_right:
                if (mOnRightBtnClickListener != null) {
                    mOnRightBtnClickListener.onClick(v);
                }
                break;
            default:
                break;
        }
    }

    public void setTitle(String titleStr) {
        if (title != null) {
            this.titleText = titleStr;
            title.setText(titleStr);
        }
    }

    public void setTitle(int resId) {
        if (title != null && resId != 0) {
            this.titleText = ResourceUtils.getResString(resId);
            title.setText(resId);
        }
    }

    public void setRightButtonText(String text) {
        if (rightButton != null) {
            rightButton.setText(text);
        }
    }

    public void setRightButtonText(int resId) {
        if (rightButton != null && resId != 0) {
            rightButton.setText(resId);
        }
    }

    public void setLeftButtonSrc(int resId) {
        if (leftButton != null) {
            leftButton.setImageResource(resId);
        }
    }

    public void setLeftButtonSrc(Drawable drawable) {
        if (leftButton != null) {
            leftButton.setImageDrawable(drawable);
        }
    }

    public void setLeftButtonVisible(int visible){
        if (leftButton!=null){
            leftButton.setVisibility(visible);
        }
    }

    public interface OnLeftBtnClickListener {
        void onClick(View v);
    }

    public interface OnRightBtnClickListener {
        void onClick(View v);
    }

    public OnLeftBtnClickListener getOnLeftBtnClickListener() {
        return mOnLeftBtnClickListener;
    }

    public void setOnLeftBtnClickListener(OnLeftBtnClickListener onLeftBtnClickListener) {
        mOnLeftBtnClickListener = onLeftBtnClickListener;
    }

    public OnRightBtnClickListener getOnRightBtnClickListener() {
        return mOnRightBtnClickListener;
    }

    public void setOnRightBtnClickListener(OnRightBtnClickListener onRightBtnClickListener) {
        mOnRightBtnClickListener = onRightBtnClickListener;
    }
}
