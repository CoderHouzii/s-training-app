package songming.straing.app.https.request;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.cache.CacheManager;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MissionInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 任务列表
 */
public class MissionListRequest extends BaseHttpRequestClient {
    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                                            .setPath("/training/list")
                                            .addParam("key", LocalHost.INSTANCE.getKey())
                                            .addParam("target_user_id", LocalHost.INSTANCE.getUserId())
                                            .addParam("start", 0)
                                            .addParam("count", 100)
                                            .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            List<MissionInfo> list = JSONUtil.toList(json.optString("trainings"),
                    new TypeToken<ArrayList<MissionInfo>>() {}.getType());
            response.setData(list);

            CacheManager.INSTANCE.save("mission_list" + LocalHost.INSTANCE.getUserId(), json.optString("trainings"));
        }
    }

    public List<MissionInfo> loadCache() {
        return JSONUtil.toList(CacheManager.INSTANCE.loadCacheString("mission_list" + LocalHost.INSTANCE.getUserId()),
                new TypeToken<ArrayList<MissionInfo>>() {}.getType());
    }
}
