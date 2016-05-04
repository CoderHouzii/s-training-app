package songming.straing.widget.sortlist;

import songming.straing.model.UserDetailInfo;
import songming.straing.model.UserInfo;

public class SortModel {

	private UserDetailInfo userInfo;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母

	public UserDetailInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserDetailInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getName() {
		return userInfo.username;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
