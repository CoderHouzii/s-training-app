package songming.straing.moments;

/**
 * 不采用MVP，因此通过中间类来沟通adapter和activity
 * 包括请求等
 */
public class MomentsManager {

    private MomentsActionCallBack callBack;

    public MomentsManager(MomentsActionCallBack callBack) {
        this.callBack = callBack;
    }

    public void addComment(long momentsid, long id, String nick) {
        callBack.onCommentReplyAdd(momentsid, id, nick);
    }

    public void addDynamicComment(long momentsid) {
        callBack.onCommentAdd(momentsid);
    }

    public void deleteComment(long id) {
        callBack.onCommentDelete(id);
    }

    public void onPraise(long momentsid, int type) {
        callBack.onPraiseStateChanged(momentsid, type);
    }

    public void onShare(long momentsid){
        callBack.onShare(momentsid);
    }
}
