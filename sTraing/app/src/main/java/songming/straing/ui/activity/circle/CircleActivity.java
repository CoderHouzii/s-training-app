package songming.straing.ui.activity.circle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import razerdp.basepopup.InputMethodUtils;
import songming.straing.R;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.CircleListRequest;
import songming.straing.app.https.request.MomentsCommentAddRequest;
import songming.straing.app.https.request.MomentsCommentDelRequest;
import songming.straing.app.https.request.MomentsPraiseRequest;
import songming.straing.app.https.request.MomentsShareRequest;
import songming.straing.model.MomentsInfo;
import songming.straing.moments.MomentsActionCallBack;
import songming.straing.moments.MomentsManager;
import songming.straing.moments.base.adapter.CircleBaseAdapter;
import songming.straing.moments.base.adapter.CircleAdapter;
import songming.straing.moments.item.ItemOnlyChar;
import songming.straing.ui.activity.base.BaseTableActivity;
import songming.straing.utils.UIHelper;
import songming.straing.widget.SharePopup;
import songming.straing.widget.ptrwidget.FriendCirclePtrListView;

/**
 * 个人圈（包含评论）
 */
public class CircleActivity extends BaseTableActivity<MomentsInfo> implements MomentsActionCallBack, View.OnClickListener {

    public static final int REQ_PRAISE = 0x10;
    public static final int REQ_COMMENT_DEL = 0x11;
    public static final int REQ_COMMENT_ADD = 0x12;
    public static final int REQ_SHARE = 0x13;

    private CircleListRequest circleListRequest;
    private ViewHolder vh;

    private MomentsPraiseRequest praiseRequest;
    private MomentsCommentDelRequest commentDelRequest;
    private MomentsCommentAddRequest commentAddRequest;
    private MomentsShareRequest momentsShareRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        initView();
        initReq();

    }

    private void initView() {
        vh = new ViewHolder(getWindow().getDecorView());
        CircleBaseAdapter.Builder<MomentsInfo> builder = new CircleBaseAdapter.Builder<MomentsInfo>(datas).addType(0, ItemOnlyChar.class).addManager(new MomentsManager(CircleActivity.this)).build();
        bindListView(R.id.list, null, new CircleAdapter(this, builder));
        vh.btn_send.setOnClickListener(this);
    }

    private void initReq() {
        circleListRequest = new CircleListRequest();
        circleListRequest.setOnResponseListener(this);
        List<MomentsInfo> cache = circleListRequest.loadCache();
        if (cache != null && cache.size() > 0) {
            datas.clear();
            datas.addAll(cache);
            mAdapter.notifyDataSetChanged();
        }
        circleListRequest.execute();


        praiseRequest = new MomentsPraiseRequest();
        praiseRequest.setOnResponseListener(this);
        praiseRequest.setRequestType(REQ_PRAISE);

        commentDelRequest = new MomentsCommentDelRequest();
        commentDelRequest.setOnResponseListener(this);
        commentDelRequest.setRequestType(REQ_COMMENT_DEL);

        commentAddRequest = new MomentsCommentAddRequest();
        commentAddRequest.setOnResponseListener(this);
        commentAddRequest.setRequestType(REQ_COMMENT_ADD);

        momentsShareRequest = new MomentsShareRequest();
        momentsShareRequest.setOnResponseListener(this);
        momentsShareRequest.setRequestType(REQ_SHARE);
    }


    @Override
    public void onPullDownRefresh() {
        circleListRequest.start = 0;
        circleListRequest.execute();
    }

    @Override
    public void onLoadMore() {
        circleListRequest.execute();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            switch (response.getRequestType()) {
                case REQ_PRAISE:
                case REQ_COMMENT_DEL:
                    setNeedReset(true);
                    onPullDownRefresh();
                    break;
                case REQ_COMMENT_ADD:
                    setNeedReset(true);
                    onPullDownRefresh();
                    reply_user_id = 0;
                    break;
                case REQ_SHARE:
                    setNeedReset(true);
                    onPullDownRefresh();
                    break;
            }
        }
    }

    @Override
    protected void onTitleRightClick(View v) {
        super.onTitleRightClick(v);
        UIHelper.startToDynamicCreateActivity(this);
    }

    private long momentsid;
    private long reply_user_id;

    //=============================================================action call back
    @Override
    public void onPraiseStateChanged(long moment_id, int type) {
        praiseRequest.moment_id = moment_id;
        praiseRequest.type = type;
        praiseRequest.execute(true);

    }

    @Override
    public void onCommentReplyAdd(long moment_id, long reply_user_id, String reply_user_nick) {
        this.momentsid = moment_id;
        this.reply_user_id = reply_user_id;
        vh.ll_input.setVisibility(View.VISIBLE);
        InputMethodUtils.showInputMethod(vh.ed_input);
        vh.ed_input.setHint("回复" + reply_user_nick + "：");
    }

    @Override
    public void onCommentAdd(long moment_id) {
        this.momentsid = moment_id;
        vh.ll_input.setVisibility(View.VISIBLE);
        InputMethodUtils.showInputMethod(vh.ed_input);
        vh.ed_input.setHint("输入您想说的");

    }

    @Override
    public void onCommentDelete(long reply_id) {
        commentDelRequest.reply_id = reply_id;
        commentDelRequest.execute(true);

    }

    @Override
    public void onShare(final long transfer_id) {
        SharePopup sharePopup = new SharePopup(this);
        sharePopup.setOnOkButtonClickEvent(new SharePopup.OnOkButtonClickEvent() {
            @Override
            public void onTextGet(String content) {
                momentsShareRequest.content = content;
                momentsShareRequest.transfer_id = transfer_id;
                momentsShareRequest.post(true);
            }
        });
        sharePopup.showPopupWindow();


    }

    @Override
    public void onDeleteDynamic() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                commentAddRequest.moment_id = momentsid;
                if (reply_user_id != 0) {
                    commentAddRequest.reply_user_id = reply_user_id;
                }
                commentAddRequest.content = vh.ed_input.getText().toString().trim();
                commentAddRequest.post(true);
                vh.ed_input.setHint("输入您想说的：");
                vh.ed_input.setText("");
                UIHelper.hideInputMethod(vh.ed_input);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setNeedReset(true);
        onPullDownRefresh();
    }

    static class ViewHolder {
        public View rootView;
        public FriendCirclePtrListView list;
        public EditText ed_input;
        public TextView btn_send;
        public LinearLayout ll_input;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.list = (FriendCirclePtrListView) rootView.findViewById(R.id.list);
            this.ed_input = (EditText) rootView.findViewById(R.id.ed_input);
            this.btn_send = (TextView) rootView.findViewById(R.id.btn_send);
            this.ll_input = (LinearLayout) rootView.findViewById(R.id.ll_input);
        }

    }
}
