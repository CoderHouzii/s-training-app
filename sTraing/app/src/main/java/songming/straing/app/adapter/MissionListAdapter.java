package songming.straing.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import songming.straing.R;
import songming.straing.model.MissionInfo;
import songming.straing.utils.UIHelper;

/**
 * 任务列表的adapter
 */
public class MissionListAdapter extends BaseAdapter {

    private List<Object> datas;
    private Context mContext;
    private LayoutInflater mInflater;

    public MissionListAdapter(Context context, List<Object> datas) {
        mContext = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 0:title
     * 1:content
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return datas.get(position) instanceof MissionInfo ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder missionHolder = null;
        TitleViewHolder titleHolder = null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case 0:
                    titleHolder = new TitleViewHolder();
                    convertView = mInflater.inflate(R.layout.item_mission_title, parent, false);
                    titleHolder.title = (TextView) convertView.findViewById(R.id.time);
                    convertView.setTag(titleHolder);
                    break;
                case 1:
                    missionHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_mission, parent, false);
                    missionHolder.missionTtile = (TextView) convertView.findViewById(R.id.mission_title);
                    missionHolder.statusIcon = (ImageView) convertView.findViewById(R.id.mission_status_icon);
                    convertView.setTag(missionHolder);
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case 0:
                    titleHolder = (TitleViewHolder) convertView.getTag();
                    break;
                case 1:
                    missionHolder = (ViewHolder) convertView.getTag();
                    break;
            }
        }
        convertView.setOnClickListener(null);
        switch (getItemViewType(position)) {
            case 0:
                titleHolder.title.setText(position == 0 ? "今天" : "过去");
                break;
            case 1:
                if (getItem(position) != null && getItem((position)) instanceof MissionInfo) {
                    MissionInfo info = ((MissionInfo) getItem((position)));
                    convertView.setTag(R.id.mission_info,info);
                    convertView.setOnClickListener(mOnClickListener);
                    missionHolder.missionTtile.setText(info.title);
                    switch (info.status) {
                        case 1:
                            missionHolder.statusIcon.setImageResource(R.drawable.ic_mission_create);
                            break;
                        case 2:
                            missionHolder.statusIcon.setImageResource(R.drawable.ic_mission_wait);
                            break;
                        case 3:
                            if (info.isSuccess == 0) {
                                missionHolder.statusIcon.setImageResource(R.drawable.ic_mission_failed);
                            } else if (info.isSuccess == 1) {
                                missionHolder.statusIcon.setImageResource(R.drawable.ic_mission_finish);
                            }
                            break;
                        default:
                            break;
                    }

                }
                break;
        }
        return convertView;
    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag(R.id.mission_info)!=null&&v.getTag(R.id.mission_info) instanceof MissionInfo){
                MissionInfo info=((MissionInfo) v.getTag(R.id.mission_info));
                UIHelper.startToMissionDetailActivity((Activity) mContext,info.trainingID);
            }
        }
    };

    static class ViewHolder {
        public TextView missionTtile;
        public ImageView statusIcon;

    }

    static class TitleViewHolder {
        public TextView title;

    }


}
