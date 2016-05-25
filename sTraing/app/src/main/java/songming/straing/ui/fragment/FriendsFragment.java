package songming.straing.ui.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import songming.straing.R;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.FriendListRequest;
import songming.straing.model.UserDetailInfo;
import songming.straing.ui.fragment.base.BaseFragment;
import songming.straing.utils.UIHelper;
import songming.straing.widget.sortlist.CharacterParser;
import songming.straing.widget.sortlist.ClearEditText;
import songming.straing.widget.sortlist.PinyinComparator;
import songming.straing.widget.sortlist.SideBar;
import songming.straing.widget.sortlist.SortAdapter;
import songming.straing.widget.sortlist.SortModel;

/**
 * 朋友
 */
public class FriendsFragment extends BaseFragment {

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private ClearEditText mClearEditText;
    private ListView sortListView;
    private TextView dialog;
    private SideBar sidrbar;

    private List<SortModel> datas;

    private SortAdapter adapter;

    private FriendListRequest friendListRequest;

    private LinearLayout headerView;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void bindData() {
        datas = new ArrayList<>();
        initViews();
        friendListRequest = new FriendListRequest();
        friendListRequest.setOnResponseListener(this);
        friendListRequest.execute();
    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sidrbar = (SideBar) getViewById(R.id.sidrbar);
        dialog = (TextView) getViewById(R.id.dialog);
        sidrbar.setTextView(dialog);

        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) getViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                UserDetailInfo info=adapter.getItem(position-sortListView.getHeaderViewsCount()).getUserInfo();
                if (info!=null){
                    UIHelper.startToPersonChatActivity(mContext,info.userID,info.username);
                }
            }
        });

        // 根据a-z进行排序源数据
        Collections.sort(datas, pinyinComparator);
        headerView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
        headerView.setOnClickListener(onHeaderClickListener);
        sortListView.addHeaderView(headerView);
        adapter = new SortAdapter(mContext, datas);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) getViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            datas.clear();
            datas.addAll(filledData((List<UserDetailInfo>) response.getData()));
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param userDetailInfos
     * @return
     */
    private List<SortModel> filledData(List<UserDetailInfo> userDetailInfos) {
        if (userDetailInfos == null || userDetailInfos.size() <= 0) return datas;

        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < userDetailInfos.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setUserInfo(userDetailInfos.get(i));
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(userDetailInfos.get(i).username);
            String sortString = "";
            if (!TextUtils.isEmpty(pinyin))
                sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = datas;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : datas) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    private View.OnClickListener onHeaderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UIHelper.startToGroupListActivity(mContext);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==233){
            friendListRequest.execute(true);
        }
    }

    @Override
    public String getTitle() {
        return "好友列表";
    }
}
