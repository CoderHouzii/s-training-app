package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MissionInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 任务详情
 */
public class MissionDetailRequest extends BaseHttpRequestClient {

    public long training_id;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/training/detail")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("training_id", training_id)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            MissionInfo info = JSONUtil.toObject(json.optString("training"), MissionInfo.class);
            response.setData(info);
        }
    }

    @Override
    public void postValue(Map<String, String> keyValue) {
    }
}

