package songming.straing.app.https.request;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.utils.PreferenceUtils;
import songming.straing.utils.RequestUrlUtils;

/**
 * 登录
 */
public class LoginRequest extends BaseHttpRequestClient{
    public String phone;
    public String password;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST).setPath("/user/login/phone").build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus()==1){
            LocalHost.INSTANCE.setKey(json.optString("key"));
            LocalHost.INSTANCE.setUserId(json.optLong("userID"));
            LocalHost.INSTANCE.setHasLogin(true);
            response.setData(true);
        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("phone", phone);
        keyValue.put("password", password);
    }
}
