package songming.straing.app.https.request;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import songming.straing.app.cache.CacheManager;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MomentsInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 动态创建
 */
public class CircleCreateRequest extends BaseHttpRequestClient {
    public String content;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("content",content);
    }
}
