package songming.straing.app.https.request;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MissionLoadInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 任务预加载
 */
public class MissionPreLoadRequest extends BaseHttpRequestClient {

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                                            .setPath("/training/pre/create")
                                            .addParam("key", LocalHost.INSTANCE.getKey())
                                            .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            MissionLoadInfo info = JSONUtil.toObject(json.toString(), MissionLoadInfo.class);
            response.setData(info);
        }
    }

    @Override
    public void postValue(Map<String, String> keyValue) {
    }
}

