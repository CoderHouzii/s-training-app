package songming.straing.moments.base.adapter.viewholder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import songming.straing.moments.MomentsManager;


/**
 * 圈item接口化
 */
public interface BaseItemView<T> {
    int getViewRes();
    void onFindView(@NonNull View parent);
    void onBindData(final int position, @NonNull View v, @NonNull T data, final int dynamicType);
    Activity getActivityContext();
    void setActivityContext(Activity context);

    void setManager(MomentsManager manager);

}
