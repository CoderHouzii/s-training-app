package songming.straing.model;

import java.util.List;

/**
 * 文章详情
 */
public class ArticleDetailInfo {


    /**
     * articleID : 35
     * user : {"userID":10000000,"username":"陈松铭LA","signNature":"helloworld","phone":"12345672278","avatar":"http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","status":0,"rank":0,"score":0}
     * title : 陈松铭TIT
     * content : 陈松铭CON
     * createAt : 0
     * readCount : 0
     * likeCount : 0
     * dislikeCount : 0
     * commentCount : 0
     * comments : []
     * canDelete : 1
     */

    public long articleID;
    public String title;
    public String content;
    public long createAt;
    public int readCount;
    public int likeCount;
    public int dislikeCount;
    public int commentCount;
    public int canDelete;

    public UserDetailInfo user;
    public List<ArticleCommentInfo> comments;
}
