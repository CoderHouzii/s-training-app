package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MomentsInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 圈动态详情
 */
public class CircleDetailRequest extends BaseHttpRequestClient {
    public long moment_id;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/detail")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("moment_id", moment_id)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            MomentsInfo info = JSONUtil.toObject(json.optString("moment"),MomentsInfo.class);
            response.setData(info);
        }

    }


}
