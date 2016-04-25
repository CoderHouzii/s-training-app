package songming.straing.app.https.request;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.STraingApp;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.utils.PreferenceUtils;
import songming.straing.utils.RequestUrlUtils;

/**
 * 注册请求
 */
public class RegisterRequest extends BaseHttpRequestClient {
    public String phone;
    public String password;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST).setPath("/user/account/create").build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus()==1){
            final String key=json.optString("key");
            final long id=json.optLong("userID");
            LocalHost.INSTANCE.setKey(key);
            LocalHost.INSTANCE.setUserId(id);

            PreferenceUtils.INSTANCE.setSharedPreferenceData("key",key);
            PreferenceUtils.INSTANCE.setSharedPreferenceData("userid",id);
        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("phone", phone);
        keyValue.put("password", password);
    }
}
