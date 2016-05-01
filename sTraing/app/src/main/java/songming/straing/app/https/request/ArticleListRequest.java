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
import songming.straing.model.MissionInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 文章列表
 */
public class ArticleListRequest extends BaseHttpRequestClient {

    public int start = 0;
    public int count = 10;
    public long target_id;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/article/list/user")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("start", start)
                .addParam("count", count)
                .addParam("target_id", target_id)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            List<ArticleDetailInfo> infos = JSONUtil.toList(json.optString("articles"), new TypeToken<ArrayList<ArticleDetailInfo>>() {
            }.getType());
            response.setData(infos);

            if (start == 0)
                CacheManager.INSTANCE.save(Config.CacheName.CACHE_ARTICLE_LIST + target_id, json.optString("articles"));

            this.start = start;
        }
    }

    public List<ArticleDetailInfo> loadCache(long userid) {
        return JSONUtil.toList(CacheManager.INSTANCE.loadCacheString(Config.CacheName.CACHE_ARTICLE_LIST + userid), new TypeToken<ArrayList<ArticleDetailInfo>>() {
        }.getType());
    }

    @Override
    public void postValue(Map<String, String> keyValue) {
    }
}

