package songming.straing.model;

import android.os.Parcelable;
import java.io.Serializable;

/**
 * 用户资料bean
 */
public class UserInfo implements Serializable {

    /**
     * userID : 1123131
     * username : chensongming
     * signNature : helloworld
     * phone : 13581372272
     * avatar : http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png
     * status : 1
     * rank : 100
     * score : 3000
     * canEdit : 1
     *
     * user.status： 状态 1.正常 2.删除
     */

    public int userID;
    public String username;
    public String signNature;
    public String phone;
    public String avatar;
    public int status;
    public int rank;
    public int score;
    public int canEdit;
}
