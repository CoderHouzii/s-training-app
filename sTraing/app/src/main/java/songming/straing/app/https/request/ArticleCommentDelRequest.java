package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 文章评论删除
 */
public class ArticleCommentDelRequest extends BaseHttpRequestClient {

    public long comment_id;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/comment/delete")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("comment_id", comment_id)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
    }
}

