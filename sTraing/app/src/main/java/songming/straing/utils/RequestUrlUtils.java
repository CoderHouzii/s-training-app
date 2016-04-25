package songming.straing.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求url构造工具类
 */
public class RequestUrlUtils {

    private static final int api_version = 1;
    private static final double app_version = 1.0;
    private static final int platform = 1;

    private RequestUrlUtils() {}

    public static class Builder {
        private boolean isHttp = true;

        private String hosturl;
        private String path;

        private HashMap<String, Object> params;

        public Builder(boolean isHttp) {
            this.isHttp = isHttp;
            params = new LinkedHashMap<>();
        }

        public Builder() {
            this(true);
        }

        public Builder setHost(String hosturl) {
            this.hosturl = hosturl;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path + "?api_version=" + api_version + "&app_version=" + app_version + "&platform=" + platform;
            return this;
        }

        public Builder addParam(String key, Object value) {
            params.put(key, value);
            return this;
        }

        public String build() {
            String url = hosturl + path;
            int i = 0;
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entity = (Map.Entry<String, Object>) iterator.next();
                if (entity != null) {
                    url += "&" + entity.getKey() + "=" + entity.getValue();
                }
                i++;
            }
            return url;
        }
    }
}
