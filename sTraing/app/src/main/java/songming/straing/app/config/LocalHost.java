package songming.straing.app.config;

import songming.straing.utils.PreferenceUtils;

/**
 * 本地账户
 */
public enum LocalHost {
    INSTANCE;

    private static HostInfo sHostInfo = new HostInfo();

    public String getKey() {
        return sHostInfo.getKey();
    }

    public void setKey(String key) {
        PreferenceUtils.INSTANCE.setSharedPreferenceData("key",key);
        sHostInfo.setKey(key);
    }

    public long getUserId() {
        return sHostInfo.getUserID();
    }

    public void setUserId(long userid) {
        PreferenceUtils.INSTANCE.setSharedPreferenceData("userid",userid);
        sHostInfo.setUserID(userid);
    }

    public String getUserName() {
        return sHostInfo.getUserName();
    }

    public void setUserName(String userName) {
        sHostInfo.setUserName(userName);
    }

    public boolean getIsLogin(){
        return sHostInfo.isLogin();
    }

    public void setHasLogin(boolean hasLogin){
        PreferenceUtils.INSTANCE.setSharedPreferenceData("haslogin",hasLogin?1:0);
        sHostInfo.setHasLogin(hasLogin);
    }

    public String getUserAvatar(){
        return sHostInfo.getAvatar();
    }

    public void setUserAvatar(String avatar){
        PreferenceUtils.INSTANCE.setSharedPreferenceData("avatar",avatar);
        sHostInfo.setAvatar(avatar);
    }

    static class HostInfo {
        private String key;
        private long userID;
        private String userName;
        private boolean hasLogin;
        private String avatar;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getUserID() {
            return userID;
        }

        public void setUserID(long userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isLogin() {
            return hasLogin;
        }

        public void setHasLogin(boolean hasLogin) {
            this.hasLogin = hasLogin;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

}
