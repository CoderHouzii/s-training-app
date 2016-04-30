package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.utils.RequestUrlUtils;

/**
 * 创建文章的请求
 */
public class CreateArticleRequest extends BaseHttpRequestClient{

    public String title;
    public String content;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("title",title);
        keyValue.put("content",content);
    }
}
