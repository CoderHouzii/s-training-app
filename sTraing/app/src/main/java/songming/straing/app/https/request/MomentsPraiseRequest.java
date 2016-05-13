package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.utils.RequestUrlUtils;

/**
 * 动态点赞/取消
 */
public class MomentsPraiseRequest extends BaseHttpRequestClient{
    public int type;
    public long moment_id;
    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/like/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("moment_id",moment_id)
                .addParam("type",type)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {

    }
}
