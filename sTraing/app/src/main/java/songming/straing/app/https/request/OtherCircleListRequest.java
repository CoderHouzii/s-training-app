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
 * 他人圈动态
 */
public class OtherCircleListRequest extends BaseHttpRequestClient {
    public int start = 0;
    public int count = 10;
    public long target_user_id;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/list/user")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("start", start)
                .addParam("count", count)
                .addParam("target_user_id",target_user_id)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            List<MomentsInfo> infos = JSONUtil.toList(json.optString("moments"), new TypeToken<ArrayList<MomentsInfo>>() {
            }.getType());
            response.setData(infos);

            if (start == 0) {
                CacheManager.INSTANCE.save(Config.CacheName.CACHE_CIRCLE_LIST +target_user_id, json.optString("moments"));
            }
            this.start = start;
        }

    }


    public List<MomentsInfo> loadCache(long userid) {
        return JSONUtil.toList(CacheManager.INSTANCE.loadCacheString(Config.CacheName.CACHE_CIRCLE_LIST + userid), new TypeToken<ArrayList<MomentsInfo>>() {
        }.getType());
    }
}
