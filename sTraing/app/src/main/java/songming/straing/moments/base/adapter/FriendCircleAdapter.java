package songming.straing.moments.base.adapter;

import android.app.Activity;

import songming.straing.model.MomentsInfo;


/**
 * 圈适配器
 */
public class FriendCircleAdapter extends CircleBaseAdapter<MomentsInfo> {

    public FriendCircleAdapter(Activity context, Builder<MomentsInfo> mBuilder) {
        super(context, mBuilder);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
