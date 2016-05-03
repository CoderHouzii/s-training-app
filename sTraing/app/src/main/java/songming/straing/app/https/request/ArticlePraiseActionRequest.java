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
 * 文章点赞
 */
public class ArticlePraiseActionRequest extends BaseHttpRequestClient {

    public long article_id;
    public int type;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/like/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("article_id", article_id)
                .addParam("type", type)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
    }
}

