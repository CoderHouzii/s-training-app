package songming.straing.moments.base.adapter;

import android.app.Activity;

import songming.straing.model.MomentsInfo;


/**
 * 圈适配器
 */
public class CircleAdapter extends CircleBaseAdapter<MomentsInfo> {

    public CircleAdapter(Activity context, Builder<MomentsInfo> mBuilder) {
        super(context, mBuilder);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
