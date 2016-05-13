package songming.straing.moments;

/**
 * 动态动作回调
 */
public interface MomentsActionCallBack {

    void onPraiseStateChanged(long moment_id,int type);
    void onCommentReplyAdd(long moment_id,long reply_user_id, String reply_user_nick);
    void onCommentAdd(long moment_id);
    void onCommentDelete(long reply_id);
    void onShare(long transfer_id);
    void onDeleteDynamic();
}
