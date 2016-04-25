package songming.straing.app.config;

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
        sHostInfo.setKey(key);
    }

    public long getUserId() {
        return sHostInfo.getUserID();
    }

    public void setUserId(long userid) {
        sHostInfo.setUserID(userid);
    }

    public String getUserName() {
        return sHostInfo.getUserName();
    }

    public void setUserName(String userName) {
        sHostInfo.setUserName(userName);
    }

    static class HostInfo {
        private String key;
        private long userID;
        private String userName;

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
    }

}
