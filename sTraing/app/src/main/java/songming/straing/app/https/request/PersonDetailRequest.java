package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.cache.CacheManager;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.UserDetailInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 用户详情
 */
public class PersonDetailRequest extends BaseHttpRequestClient {
    public long userid;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                                            .setPath("/user/detail")
                                            .addParam("key", LocalHost.INSTANCE.getKey())
                                            .addParam("target_user_id",userid)
                                            .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus()==1){
            UserDetailInfo info= JSONUtil.toObject(json.optString("user"),UserDetailInfo.class);
            response.setData(info);

            CacheManager.INSTANCE.save(Config.CacheName.CACHE_PERSON_DETAIL+userid,json.optString("user"));
        }
    }


    public UserDetailInfo loadCache(long userid){
        UserDetailInfo info=JSONUtil.toObject(CacheManager.INSTANCE.loadCacheString(Config.CacheName
                .CACHE_PERSON_DETAIL+userid),UserDetailInfo.class);
        return info;
    }
}
