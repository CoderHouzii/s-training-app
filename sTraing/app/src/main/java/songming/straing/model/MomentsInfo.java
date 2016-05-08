package songming.straing.model;

import java.util.List;

/**
 * 动态
 */
public class MomentsInfo {

    /**
     * momentID : 4
     * user : {"userID":10000000,"username":"陈松铭LA","signNature":"helloworld","phone":"12345672278","tags":[],"location":"","occupation":"","avatar":"http://www.baidu.com/load?fileName=http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","status":0,"rank":0,"score":0}
     * text : 陈松铭LALALA
     * tranferMoment : null
     * createAt : 1461393484000
     * likeCount : 1
     * replyCount : 0
     * tranferCount : 0
     * likeUsers : []
     * replys : []
     * canDelete : 1
     */

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
    public Object tranferMoment;
    public long createAt;
    public int likeCount;
    public int replyCount;
    public int tranferCount;
    public int canDelete;
    public List<UserDetailInfo> likeUsers;
    public List<CommentInfo> replys;

}
