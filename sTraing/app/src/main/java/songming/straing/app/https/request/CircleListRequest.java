package songming.straing.app.https.request;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import songming.straing.app.cache.CacheManager;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MomentsInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 圈动态
 */
public class CircleListRequest extends BaseHttpRequestClient {
    public int start = 0;
    public int count = 10;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/list")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("start", start)
                .addParam("count", count)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            List<MomentsInfo> infos = JSONUtil.toList(json.optString("moments"), new TypeToken<ArrayList<MomentsInfo>>() {
            }.getType());
            response.setData(infos);

            if (start == 0) {
                CacheManager.INSTANCE.save(Config.CacheName.CACHE_CIRCLE_LIST + LocalHost.INSTANCE.getUserId(), json.optString("moments"));
            }
            this.start = start;
        }

    }


    public List<MomentsInfo> loadCache() {
        return JSONUtil.toList(CacheManager.INSTANCE.loadCacheString(Config.CacheName.CACHE_CIRCLE_LIST + LocalHost.INSTANCE.getUserId()), new TypeToken<ArrayList<MomentsInfo>>() {
        }.getType());
    }
}
