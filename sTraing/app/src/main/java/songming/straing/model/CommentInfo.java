package songming.straing.model;

import java.util.List;

/**
 * 评论
 */
public class CommentInfo {

    /**
     * replyID : 1
     * momentID : 3
     * user : {"userID":10000000,"username":"陈松铭LA","signNature":"helloworld","phone":"12345672278","tags":[],"location":"","occupation":"","avatar":"http://www.baidu.com/load?fileName=http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","status":0,"rank":0,"score":0}
     * text : 陈松铭HAHAH
     * replyUser : {"userID":10000001,"username":"","signNature":"该用户很懒，什么也没留下","phone":"12345672277","tags":[],"location":"","occupation":"","avatar":"http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","status":0,"rank":0,"score":0}
     * createAt : 1461393406000
     * canDelete : 1
     */

    public int replyID;
    public int momentID;
    /**
     * userID : 10000000
     * username : 陈松铭LA
     * signNature : helloworld
     * phone : 12345672278
     * tags : []
     * location :
     * occupation :
     * avatar : http://www.baidu.com/load?fileName=http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png
     * status : 0
     * rank : 0
     * score : 0
     */

    public UserDetailInfo user;
    public String text;
    /**
     * userID : 10000001
     * username :
     * signNature : 该用户很懒，什么也没留下
     * phone : 12345672277
     * tags : []
     * location :
     * occupation :
     * avatar : http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png
     * status : 0
     * rank : 0
     * score : 0
     */

    public UserDetailInfo replyUser;
    public long createAt;
    public int canDelete;

}
