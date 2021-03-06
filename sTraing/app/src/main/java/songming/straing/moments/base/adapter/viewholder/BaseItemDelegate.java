package songming.straing.moments.base.adapter.viewholder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import songming.straing.R;
import songming.straing.app.config.LocalHost;
import songming.straing.model.CommentInfo;
import songming.straing.model.MomentsInfo;
import songming.straing.model.MomentsShareInfo;
import songming.straing.moments.MomentsManager;
import songming.straing.ui.activity.person.AvatarSettingActivity;
import songming.straing.utils.TimeUtils;
import songming.straing.utils.UIHelper;
import songming.straing.widget.CommentPopup;
import songming.straing.widget.DeleteCommentPopup;
import songming.straing.widget.SuperImageView;
import songming.straing.widget.commentwidget.ArticleCommentWidget;
import songming.straing.widget.commentwidget.CommentWidget;
import songming.straing.widget.praisewidget.PraiseWidget;


/**
 *
 */
public abstract class BaseItemDelegate implements BaseItemView<MomentsInfo>,
        View.OnClickListener,
        View.OnLongClickListener,
        ViewGroup.OnHierarchyChangeListener {
    protected Activity context;
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

    protected MomentsManager momentsManager;

    private MomentsInfo mInfo;
    private int curPos;

    //评论区的view对象池
    private static final CommentPool COMMENT_TEXT_POOL = new CommentPool(35);

    private int commentPaddintRight = 0;

    public BaseItemDelegate() {
    }

    public BaseItemDelegate(Activity context) {
        this.context = context;
    }

    private CommentPopup mCommentPopup;

    @Override
    public void onBindData(int position, @NonNull View v, @NonNull MomentsInfo data, final int dynamicType) {
        if (commentPaddintRight == 0) commentPaddintRight = UIHelper.dipToPx(context, 8f);

        mInfo = data;
        curPos = position;
        //初始化共用部分
        bindView(v);
        bindShareData(data);
        bindData(position, v, data, dynamicType);
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
                UIHelper.startToOtherCircleActivity(getActivityContext(), data.user.userID);
            }
        });
        nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.startToOtherCircleActivity(getActivityContext(), data.user.userID);
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
                UIHelper.startToPersonIndexActivity(getActivityContext(), id);
            }
        }
    };

    /**
     * 绑定共用部分
     */
    private void bindView(View v) {
        if (avatar == null) avatar = (SuperImageView) v.findViewById(R.id.avatar);
        if (nick == null) nick = (TextView) v.findViewById(R.id.nick);
        if (textField == null) textField = (TextView) v.findViewById(R.id.item_text_field);

        if (shareLayout == null) shareLayout = (RelativeLayout) v.findViewById(R.id.share_content);
        if (shareNick == null) shareNick = (TextView) v.findViewById(R.id.share_nick);
        if (shareContent == null) shareContent = (TextView) v.findViewById(R.id.share_text);

        if (createTime == null) createTime = (TextView) v.findViewById(R.id.create_time);
        if (commentImage == null) commentImage = (ImageView) v.findViewById(R.id.comment_press);
        if (commentButton == null)
            commentButton = (FrameLayout) v.findViewById(R.id.comment_button);
        if (commentAndPraiseLayout == null) {
            commentAndPraiseLayout = (LinearLayout) v.findViewById(R.id.comment_praise_layout);
        }
        if (praiseWidget == null) praiseWidget = (PraiseWidget) v.findViewById(R.id.praise);
        if (line == null) line = v.findViewById(R.id.divider);
        if (commentLayout == null)
            commentLayout = (LinearLayout) v.findViewById(R.id.comment_layout);
        if (mCommentPopup == null) mCommentPopup = new CommentPopup(context);

        avatar.setOnClickListener(this);
        nick.setOnClickListener(this);
        textField.setOnLongClickListener(this);

        commentButton.setOnClickListener(this);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInfo!=null){
                    UIHelper.startToDynamicDetailActivity(getActivityContext(),mInfo.momentID);
                }
            }
        });
    }

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
                    commentWidget = new CommentWidget(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 1;
                    params.bottomMargin = 1;
                    commentWidget.setLayoutParams(params);
                    commentWidget.setPadding(0, 0, commentPaddintRight, 0);
                    commentWidget.setLineSpacing(4, 1);
                }
                commentWidget.setBackgroundDrawable(
                        context.getResources().getDrawable(R.drawable.selector_comment_widget));
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
            default:
                break;
        }

        //评论的click
        if (v instanceof CommentWidget) {
            final CommentInfo info = ((CommentWidget) v).getData();
            if (info.canDelete==1) {
                final DeleteCommentPopup deleteCommentPopup = new DeleteCommentPopup(getActivityContext());
                deleteCommentPopup.setOnDeleteCommentClickListener(new DeleteCommentPopup.OnDeleteCommentClickListener() {
                    @Override
                    public void onDelClick(View v) {
                        if (momentsManager != null) {
                            momentsManager.deleteComment(info.replyID);
                            deleteCommentPopup.dismiss();
                        }
                    }
                });
                deleteCommentPopup.showPopupWindow();

            } else
                momentsManager.addComment(info.momentID, info.replyUser.userID, info.replyUser.username);
        }
    }

    private CommentPopup.OnCommentPopupClickListener mPopupClickListener
            = new CommentPopup.OnCommentPopupClickListener() {
        @Override
        public void onLikeClick(View v, MomentsInfo info, boolean hasPraise) {
            if (momentsManager != null && info != null) {
                momentsManager.onPraise(info.momentID, hasPraise ? 2 : 1);
            }
        }

        @Override
        public void onCommentClick(View v, MomentsInfo info) {
            if (momentsManager != null && info != null) {
                momentsManager.addDynamicComment(info.momentID);
            }
        }

        @Override
        public void onShareClick(long transfer_id) {
            if (momentsManager != null) {
                momentsManager.onShare(transfer_id);
            }
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

    @Override
    public void setManager(MomentsManager manager) {
        this.momentsManager = manager;
    }

    //=============================================================
    @Override
    public Activity getActivityContext() {
        return context;
    }

    @Override
    public void setActivityContext(Activity context) {
        this.context = context;
    }


    protected abstract void bindData(int position, @NonNull View v, @NonNull MomentsInfo data, final int dynamicType);

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
