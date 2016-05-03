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
 * 文章详情
 */
public class ArticleDetailRequest extends BaseHttpRequestClient {

    public long article_id;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/detail")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("article_id", article_id)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            ArticleDetailInfo info = JSONUtil.toObject(json.optString("article"), ArticleDetailInfo.class);
            response.setData(info);
        }
    }
}

