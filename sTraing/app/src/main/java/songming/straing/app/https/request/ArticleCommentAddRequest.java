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
 * 文章评论
 */
public class ArticleCommentAddRequest extends BaseHttpRequestClient {
    public long article_id;
    public long reply_id;
    public String content;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/comment/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("article_id",""+article_id);
        keyValue.put("reply_id",""+reply_id);
        keyValue.put("content",content);

    }
}
