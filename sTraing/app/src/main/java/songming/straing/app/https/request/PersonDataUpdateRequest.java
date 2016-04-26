package songming.straing.app.https.request;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.utils.RequestUrlUtils;

/**
 * 用户资料更新
 */
public class PersonDataUpdateRequest extends BaseHttpRequestClient {

    public String username;
    public String password;
    public String sign_nature;
    public String avatar;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST).setPath("/user/detail/update").build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("key", LocalHost.INSTANCE.getKey());
        keyValue.put("username",username);
        keyValue.put("password",password);
        keyValue.put("sign_nature",sign_nature);
        keyValue.put("avatar",avatar);
    }
}
