package songming.straing.widget.sortlist;

import songming.straing.model.GroupInfo;
import songming.straing.model.UserDetailInfo;
import songming.straing.model.UserInfo;

public class SortModel {

    private UserDetailInfo userInfo;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    private GroupInfo groupInfo;

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public UserDetailInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDetailInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getName() {
        if (userInfo != null)
            return userInfo.username;
        else if (groupInfo != null)
            return groupInfo.groupName;
        else
            return "";
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
