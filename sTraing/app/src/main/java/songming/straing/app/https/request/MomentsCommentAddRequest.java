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
 * 添加评论
 */
public class MomentsCommentAddRequest extends BaseHttpRequestClient {
    public long moment_id;
    public long reply_user_id;
    public String content;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/reply/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("moment_id",""+moment_id);
        keyValue.put("reply_user_id",""+reply_user_id);
        keyValue.put("content",content);

    }
}
