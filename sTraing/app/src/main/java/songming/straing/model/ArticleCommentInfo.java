package songming.straing.model;

/**
 * 文章详情(评论)
 */
public class ArticleCommentInfo {

    /**
     * articleCommentID : 3
     * user : {"userID":10000000,"username":"陈松铭LA","signNature":"helloworld","phone":"12345672278","avatar":"http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","status":0,"rank":0,"score":0}
     * replyUser : null
     * content : aaaaa
     * canDelete : 1
     */

    public int articleCommentID;
    /**
     * userID : 10000000
     * username : 陈松铭LA
     * signNature : helloworld
     * phone : 12345672278
     * avatar : http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png
     * status : 0
     * rank : 0
     * score : 0
     */

    public UserDetailInfo user;
    public UserDetailInfo replyUser;
    public String content;
    public int canDelete;
}
