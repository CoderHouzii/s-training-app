package songming.straing.moments.item;

import android.support.annotation.NonNull;
import android.view.View;

import songming.straing.R;
import songming.straing.model.MomentsInfo;
import songming.straing.moments.base.adapter.viewholder.BaseItemDelegate;

/**
 * 只有文字的朋友圈item
 *
 */
public class ItemOnlyChar extends BaseItemDelegate {

    public ItemOnlyChar(){}

    @Override
    protected void bindData(int position, @NonNull View v, @NonNull MomentsInfo data, int dynamicType) {
    }

    @Override
    public int getViewRes() {
        return R.layout.dynamic_item_only_char;
    }

    @Override
    public void onFindView(@NonNull View parent) {

    }
}
