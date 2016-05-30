package songming.straing.moments.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import songming.straing.R;
import songming.straing.model.MomentsInfo;
import songming.straing.moments.base.adapter.viewholder.BaseItemDelegate;

/**
 */
public class ItemOther extends BaseItemDelegate {
    private TextView text;

    public ItemOther() {
    }

    @Override
    protected void bindData(int position, @NonNull View v, @NonNull MomentsInfo data, int dynamicType) {
        text.setText(data.text);
    }

    @Override
    public int getViewRes() {
        return R.layout.dynamic_other_item;
    }

    @Override
    public void onFindView(@NonNull View parent) {
        if (text==null)text= (TextView) parent.findViewById(R.id.text);

    }
}
