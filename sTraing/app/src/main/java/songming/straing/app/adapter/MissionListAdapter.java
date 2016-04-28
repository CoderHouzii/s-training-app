package songming.straing.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Objects;
import songming.straing.R;
import songming.straing.model.MissionInfo;
import songming.straing.utils.TimeUtils;

/**
 * Created by 大灯泡 on 2016/4/28.
 */
public class MissionListAdapter extends BaseAdapter {

    private List<Object> datas;
    private Context mContext;
    private LayoutInflater mInflater;

    public MissionListAdapter(Context context, List<Object> datas) {
        mContext = context;
        this.datas = datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return 0;
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
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return datas.get(position)==null?0:1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder missionHolder=null;
        TitleViewHolder titleHolder=null;
        if (convertView==null) {
            switch (getItemViewType(position)) {
                case 0:
                    titleHolder=new TitleViewHolder();
                    convertView=mInflater.inflate( R.layout.item_mission_title,parent,false);
                    titleHolder.title= (TextView) convertView.findViewById(R.id.time);
                    convertView.setTag(titleHolder);
                    break;
                case 1:
                    missionHolder=new ViewHolder();
                    convertView=mInflater.inflate( R.layout.item_mission,parent,false);
                    missionHolder.missionTtile= (TextView) convertView.findViewById(R.id.mission_title);
                    missionHolder.statusIcon= (ImageView) convertView.findViewById(R.id.mission_status_icon);
                    convertView.setTag(missionHolder);
                    break;
            }
        }else {
            switch (getItemViewType(position)) {
                case 0:
                    titleHolder= (TitleViewHolder) convertView.getTag();
                    break;
                case 1:
                    missionHolder= (ViewHolder) convertView.getTag();
                    break;
            }
        }
        switch (getItemViewType(position)) {
            case 0:
                titleHolder.title.setText(position==0?"今天":"昨天");
                break;
            case 1:
                break;
        }
        return null;
    }

    static class ViewHolder {
        public TextView missionTtile;
        public ImageView statusIcon;

    }

    static class TitleViewHolder{
        public TextView title;

    }
}
