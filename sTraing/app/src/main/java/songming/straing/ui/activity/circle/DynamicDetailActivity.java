package songming.straing.ui.activity.circle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import razerdp.basepopup.InputMethodUtils;
import songming.straing.R;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.CircleDetailRequest;
import songming.straing.app.https.request.CircleListRequest;
import songming.straing.app.https.request.MomentsCommentAddRequest;
import songming.straing.app.https.request.MomentsCommentDelRequest;
import songming.straing.app.https.request.MomentsPraiseRequest;
import songming.straing.app.https.request.MomentsShareRequest;
import songming.straing.model.CommentInfo;
import songming.straing.model.MomentsInfo;
import songming.straing.model.MomentsShareInfo;
import songming.straing.moments.MomentsManager;
import songming.straing.ui.activity.base.BaseActivity;
import songming.straing.utils.TimeUtils;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CommentPopup;
import songming.straing.widget.DeleteCommentPopup;
import songming.straing.widget.SharePopup;
import songming.straing.widget.SuperImageView;
import songming.straing.widget.commentwidget.ArticleCommentWidget;
import songming.straing.widget.commentwidget.CommentWidget;
import songming.straing.widget.praisewidget.PraiseWidget;

/**
 * 动态详情
 */
public class DynamicDetailActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener, ViewGroup.OnHierarchyChangeListener {
    //顶部
    protected SuperImageView avatar;
    protected TextView nick;
    protected TextView textField;
    //底部
    protected TextView createTime;
    protected ImageView commentImage;
    protected FrameLayout commentButton;
    protected LinearLayout commentAndPraiseLayout;
    protected PraiseWidget praiseWidget;
    protected View line;
    protected LinearLayout commentLayout;

    //中间内容层
    protected RelativeLayout shareLayout;
    protected TextView shareNick;
    protected TextView shareContent;
    private MomentsInfo mInfo;
    private CommentPopup mCommentPopup;
    //评论区的view对象池
    private static final CommentPool COMMENT_TEXT_POOL = new CommentPool(35);

    private int commentPaddintRight = 0;
    private long momentid;
    private CircleDetailRequest circleDetailRequest;

    private MomentsPraiseRequest praiseRequest;
    private MomentsCommentDelRequest commentDelRequest;
    private MomentsCommentAddRequest commentAddRequest;
    private MomentsShareRequest momentsShareRequest;

    public static final int REQ_PRAISE = 0x15;
    public static final int REQ_COMMENT_DEL = 0x16;
    public static final int REQ_COMMENT_ADD = 0x17;
    public static final int REQ_SHARE = 0x18;


    private EditText ed_input;
    private TextView btn_send;
    private LinearLayout ll_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_detail);
        getdata();
        initView();
        initReq();
    }

    private void getdata() {
        momentid = getIntent().getLongExtra("momentid", 0);
        if (momentid == 0) finish();
    }

    private void initView() {
        if (avatar == null) avatar = (SuperImageView) findViewById(R.id.avatar);
        if (nick == null) nick = (TextView) findViewById(R.id.nick);
        if (textField == null) textField = (TextView) findViewById(R.id.item_text_field);

        if (shareLayout == null) shareLayout = (RelativeLayout) findViewById(R.id.share_content);
        if (shareNick == null) shareNick = (TextView) findViewById(R.id.share_nick);
        if (shareContent == null) shareContent = (TextView) findViewById(R.id.share_text);

        if (createTime == null) createTime = (TextView) findViewById(R.id.create_time);
        if (commentImage == null) commentImage = (ImageView) findViewById(R.id.comment_press);
        if (commentButton == null)
            commentButton = (FrameLayout) findViewById(R.id.comment_button);
        if (commentAndPraiseLayout == null) {
            commentAndPraiseLayout = (LinearLayout) findViewById(R.id.comment_praise_layout);
        }
        if (praiseWidget == null) praiseWidget = (PraiseWidget) findViewById(R.id.praise);
        if (line == null) line = findViewById(R.id.divider);
        if (commentLayout == null)
            commentLayout = (LinearLayout) findViewById(R.id.comment_layout);
        if (mCommentPopup == null) mCommentPopup = new CommentPopup(this);

        ed_input = (EditText) findViewById(R.id.ed_input);
        btn_send = (TextView) findViewById(R.id.btn_send);
        ll_input = (LinearLayout) findViewById(R.id.ll_input);

        btn_send.setOnClickListener(this);

        avatar.setOnClickListener(this);
        nick.setOnClickListener(this);
        textField.setOnLongClickListener(this);

        commentButton.setOnClickListener(this);
    }

    private void initReq() {
        circleDetailRequest = new CircleDetailRequest();
        circleDetailRequest.moment_id = momentid;
        circleDetailRequest.setRequestType(0x11);
        circleDetailRequest.setOnResponseListener(this);
        circleDetailRequest.execute(true);

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
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus() == 1) {
            switch (response.getRequestType()) {
                case 0x11:
                    bindData2View((MomentsInfo) response.getData());
                    break;
                case REQ_PRAISE:
                case REQ_COMMENT_DEL:
                    circleDetailRequest.execute(true);
                    break;
                case REQ_COMMENT_ADD:
                    circleDetailRequest.execute(true);
                    reply_user_id = 0;
                    break;
                case REQ_SHARE:
                    circleDetailRequest.execute(true);
                    break;
            }
        }
    }

    private void bindData2View(MomentsInfo info) {
        if (info == null) return;
        mInfo = info;
        bindShareData(info);

    }


    /**
     * 共有数据绑定
     */
    private void bindShareData(final MomentsInfo data) {
        avatar.loadImageDefault(data.user.avatar);
        nick.setText(data.user.username);
        textField.setText(data.text);


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.startToOtherCircleActivity(DynamicDetailActivity.this, data.user.userID);
            }
        });
        nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.startToOtherCircleActivity(DynamicDetailActivity.this, data.user.userID);
            }
        });

        setShareContent(data.tranferMoment);

        createTime.setText(TimeUtils.longToString(TimeUtils.yyyy_MM_dd, data.createAt));
        setCommentPraiseLayoutVisibility(data);
        //点赞
        praiseWidget.setDatas(data.likeUsers);
        //评论
        addCommentWidget(data.replys);
    }

    private void setShareContent(MomentsShareInfo tranferMoment) {
        if (tranferMoment == null) {
            shareLayout.setVisibility(View.GONE);
            return;
        }
        shareLayout.setVisibility(View.VISIBLE);
        if (tranferMoment.user != null) {
            shareNick.setText(tranferMoment.user.username);
            shareNick.setTag(tranferMoment.user.userID);
            shareNick.setOnClickListener(onShareNickClickListener);
        }
        shareContent.setText(tranferMoment.text);

    }

    private View.OnClickListener onShareNickClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long id = (long) v.getTag();
            if (id != 0) {
                UIHelper.startToPersonIndexActivity(DynamicDetailActivity.this, id);
            }
        }
    };

    /**
     * 是否有点赞或者评论
     */
    private void setCommentPraiseLayoutVisibility(MomentsInfo data) {
        if ((data.replys == null || data.replys.size() == 0) &&
                (data.likeUsers == null || data.likeUsers.size() == 0)) {
            //全空，取消显示
            commentAndPraiseLayout.setVisibility(View.GONE);
        } else {
            //某项不空，则展示layout
            commentAndPraiseLayout.setVisibility(View.VISIBLE);
            //点赞或者评论某个为空，分割线不展示
            if (data.replys == null || data.replys.size() == 0 ||
                    data.likeUsers == null || data.likeUsers.size() == 0) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }
            //点赞为空，取消点赞控件的可见性
            if (data.likeUsers == null || data.likeUsers.size() == 0) {
                praiseWidget.setVisibility(View.GONE);
            } else {
                praiseWidget.setVisibility(View.VISIBLE);
            }
            //评论
            if (data.replys == null || data.replys.size() == 0) {
                commentLayout.setVisibility(View.GONE);
            } else {
                commentLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addCommentWidget(List<CommentInfo> commentList) {
        if (commentList == null || commentList.size() == 0) return;
        /**
         * 优化方案：
         * 因为是在listview里面，那么复用肯定有，意味着滑动的时候必须要removeView或者addView
         * 但为了性能提高，不可以直接removeAllViews
         * 于是采取以下方案：
         *    根据现有的view进行remove/add差额
         *    然后统一设置
         *
         * 2016-02-26:复用池进一步优化
         * */
        final int childCount = commentLayout.getChildCount();
        commentLayout.setOnHierarchyChangeListener(this);
        if (childCount < commentList.size()) {
            //当前的view少于list的长度，则补充相差的view
            int subCount = commentList.size() - childCount;
            for (int i = 0; i < subCount; i++) {
                CommentWidget commentWidget = COMMENT_TEXT_POOL.get();
                if (commentWidget == null) {
                    commentWidget = new CommentWidget(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 1;
                    params.bottomMargin = 1;
                    commentWidget.setLayoutParams(params);
                    commentWidget.setPadding(0, 0, commentPaddintRight, 0);
                    commentWidget.setLineSpacing(4, 1);
                }
                commentWidget.setBackgroundDrawable(
                        this.getResources().getDrawable(R.drawable.selector_comment_widget));
                commentWidget.setOnClickListener(this);
                commentWidget.setOnLongClickListener(this);
                commentLayout.addView(commentWidget);
            }
        } else if (childCount > commentList.size()) {
            //当前的view的数目比list的长度大，则减去对应的view
            commentLayout.removeViews(commentList.size(), childCount - commentList.size());
        }
        //绑定数据
        for (int n = 0; n < commentList.size(); n++) {
            CommentWidget commentWidget = (CommentWidget) commentLayout.getChildAt(n);
            if (commentWidget != null) commentWidget.setCommentText(commentList.get(n));
        }
    }

    private long reply_user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 评论按钮
            case R.id.comment_button:
                if (mInfo == null) return;
                mCommentPopup.setMomentsInfo(mInfo);
                mCommentPopup.setOnCommentPopupClickListener(mPopupClickListener);
                mCommentPopup.showPopupWindow(commentImage);
                break;
            case R.id.btn_send:
                commentAddRequest.moment_id = momentid;
                if (reply_user_id != 0) {
                    commentAddRequest.reply_user_id = reply_user_id;
                }
                commentAddRequest.content = ed_input.getText().toString().trim();
                commentAddRequest.post(true);
                ed_input.setHint("输入您想说的：");
                ed_input.setText("");
                UIHelper.hideInputMethod(ed_input);
                break;
            default:
                break;
        }

        //评论的click
        if (v instanceof CommentWidget) {
            final CommentInfo info = ((CommentWidget) v).getData();
            if (info.canDelete == 1) {
                final DeleteCommentPopup deleteCommentPopup = new DeleteCommentPopup(this);
                deleteCommentPopup.setOnDeleteCommentClickListener(new DeleteCommentPopup.OnDeleteCommentClickListener() {
                    @Override
                    public void onDelClick(View v) {
                        commentDelRequest.reply_id = info.replyID;
                        commentDelRequest.execute(true);
                        deleteCommentPopup.dismiss();
                    }
                });
                deleteCommentPopup.showPopupWindow();

            } else {
                reply_user_id = info.replyUser.userID;
                ll_input.setVisibility(View.VISIBLE);
                InputMethodUtils.showInputMethod(ed_input);
                ed_input.setHint("回复" + info.replyUser.username + "：");
            }
        }
    }

    private CommentPopup.OnCommentPopupClickListener mPopupClickListener
            = new CommentPopup.OnCommentPopupClickListener() {
        @Override
        public void onLikeClick(View v, MomentsInfo info, boolean hasPraise) {
            praiseRequest.moment_id = momentid;
            praiseRequest.type = hasPraise ? 2 : 1;
            praiseRequest.execute(true);
        }

        @Override
        public void onCommentClick(View v, MomentsInfo info) {
            ll_input.setVisibility(View.VISIBLE);
            InputMethodUtils.showInputMethod(ed_input);
            ed_input.setHint("输入您想说的");
        }

        @Override
        public void onShareClick(final long transfer_id) {
            SharePopup sharePopup = new SharePopup(DynamicDetailActivity.this);
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
    };

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        if (child instanceof ArticleCommentWidget) COMMENT_TEXT_POOL.put((CommentWidget) child);
    }

    public void clearCommentPool() {
        COMMENT_TEXT_POOL.clearPool();
    }


    //=============================================================pool class
    static class CommentPool {
        private CommentWidget[] CommentPool;
        private int size;
        private int curPointer = -1;

        public CommentPool(int size) {
            this.size = size;
            CommentPool = new CommentWidget[size];
        }

        public synchronized CommentWidget get() {
            if (curPointer == -1 || curPointer > CommentPool.length) return null;
            CommentWidget commentTextView = CommentPool[curPointer];
            CommentPool[curPointer] = null;
            //Log.d("itemDelegate","复用成功---- 当前的游标为： "+curPointer);
            curPointer--;
            return commentTextView;
        }

        public synchronized boolean put(CommentWidget commentTextView) {
            if (curPointer == -1 || curPointer < CommentPool.length - 1) {
                curPointer++;
                CommentPool[curPointer] = commentTextView;
                //Log.d("itemDelegate","入池成功---- 当前的游标为： "+curPointer);
                return true;
            }
            return false;
        }

        public void clearPool() {
            for (int i = 0; i < CommentPool.length; i++) {
                CommentPool[i] = null;
            }
            curPointer = -1;
        }
    }
}
