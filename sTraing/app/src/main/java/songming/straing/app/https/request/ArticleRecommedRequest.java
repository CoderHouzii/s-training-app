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
import songming.straing.model.ArticleDetailInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 推荐文章
 */
public class ArticleRecommedRequest extends BaseHttpRequestClient {

    public int start = 0;
    public int count = 10;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/list/recommed")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("start", start)
                .addParam("count", count)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            List<ArticleDetailInfo> infos = JSONUtil.toList(json.optString("articles"), new TypeToken<ArrayList<ArticleDetailInfo>>() {
            }.getType());
            response.setData(infos);
            this.start = start;
        }
    }
}

