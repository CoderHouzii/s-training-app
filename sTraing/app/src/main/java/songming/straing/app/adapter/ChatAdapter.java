package songming.straing.app.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import songming.straing.R;
import songming.straing.model.ChatInfo;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CircleImageView;

/**
 * 聊天adapter
 */
public class ChatAdapter extends BaseAdapter {
    public static final int LEFT = 0x10;
    public static final int RIGHT = 0x11;

    private List<Pair<Integer, ChatInfo>> datas;
    private Context context;
    private LayoutInflater mInflater;

    public ChatAdapter(Context context, List<Pair<Integer, ChatInfo>> datas) {
        this.context = context;
        this.datas = datas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Pair<Integer, ChatInfo> getItem(int position) {
        return datas.get(position);
    }


    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).first == LEFT) {
            return 2;
        } else if (getItem(position).first == RIGHT) {
            return 1;
        } else
            return 0;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LeftViewHolder leftViewHolder = null;
        RightViewHolder rightViewHolder = null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case 1:
                    convertView = mInflater.inflate(R.layout.item_chat_me, parent, false);
                    rightViewHolder = new RightViewHolder();
                    rightViewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.chat_me_avatar);
                    rightViewHolder.content = (TextView) convertView.findViewById(R.id.content);
                    convertView.setTag(rightViewHolder);
                    break;
                case 2:
                    convertView = mInflater.inflate(R.layout.item_chat_other, parent, false);
                    leftViewHolder = new LeftViewHolder();
                    leftViewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.chat_other_avatar);
                    leftViewHolder.content = (TextView) convertView.findViewById(R.id.content);
                    convertView.setTag(leftViewHolder);
                    break;
                default:
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case 1:
                    rightViewHolder = (RightViewHolder) convertView.getTag();
                    break;
                case 2:
                    leftViewHolder = (LeftViewHolder) convertView.getTag();
                    break;
            }
        }

        switch (getItemViewType(position)) {
            case 1:
                rightViewHolder = (RightViewHolder) convertView.getTag();
                rightViewHolder.avatar.loadImageDefault(getItem(position).second.avatar);
                rightViewHolder.content.setText(getItem(position).second.content);
                rightViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIHelper.startToPersonIndexActivity(context,getItem(position).second.userid);
                    }
                });
                break;
            case 2:
                leftViewHolder = (LeftViewHolder) convertView.getTag();
                leftViewHolder.avatar.loadImageDefault(getItem(position).second.avatar);
                leftViewHolder.content.setText(getItem(position).second.content);
                leftViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIHelper.startToPersonIndexActivity(context,getItem(position).second.userid);
                    }
                });
                break;
        }


        return convertView;
    }


    static class RightViewHolder {
        public CircleImageView avatar;
        public TextView content;
    }

    static class LeftViewHolder {
        public CircleImageView avatar;
        public TextView content;
    }
}
