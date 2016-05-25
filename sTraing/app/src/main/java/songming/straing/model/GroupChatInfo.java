package songming.straing.model;

import java.io.Serializable;
import java.util.List;

/**
 */
public class GroupChatInfo implements Serializable {


    /**
     * groupID : 9
     * groupName : 陈松铭LA
     * memberCount : 2
     * members : [{"userID":10000000,"username":"陈松铭LA","signNature":"helloworld","phone":"13512372279","avatar":"http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","rank":0,"score":0},{"userID":10000001,"username":"","signNature":"该用户很懒，什么也没留下","phone":"12345672277","avatar":"http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png","rank":0,"score":0}]
     */

    public int groupID;
    public String groupName;
    public int memberCount;
    /**
     * userID : 10000000
     * username : 陈松铭LA
     * signNature : helloworld
     * phone : 13512372279
     * avatar : http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png
     * rank : 0
     * score : 0
     */

    public List<UserDetailInfo> members;

}
