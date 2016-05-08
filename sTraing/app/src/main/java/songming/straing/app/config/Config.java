package songming.straing.app.config;

/**
 * 配置类
 */
public class Config {
    //启动时间
    public static final long APP_START = 2 * 1000;

    //服务器地址
    public static final String HOST="http://115.28.102.139:80";
    //socket地址
    public static final String SOCKET_HOST="115.28.102.139";

    public static class SocketIntent {
        public static final String TYPE = "type";
        public static final String PACKET = "PACKET";

        public class Types{
            public static final int CONNECT = 0;
            public static final int DISCONNECT = 1;
            public static final int SEND = 2;
        }

    }


    public static class CacheName{
        public static final String CACHE_PERSON_DETAIL="personaldetail";
        public static final String CACHE_ARTICLE_LIST="articlelist";
        public static final String CACHE_CIRCLE_LIST="circleList";

    }


}
