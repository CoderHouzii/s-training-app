package songming.straing.app.https.request;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.MissionInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 创建任务
 */
public class MissionCreateRequest extends BaseHttpRequestClient {
    //运动类型
    public int type;
    //运动地点类型
    public int location;
    //运动饮料类型
    public int drinking;
    //运动护具类型
    public int gear;
    //开始时间
    public long begin_at;
    //预设个数
    public int preset_count;
    //预设组数
    public int preset_group;
    //预设平均休息时间
    public int per_breaktime;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                                            .setPath("/training/create")
                                            .addParam("key", LocalHost.INSTANCE.getKey())
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
        keyValue.put("type", "" + type);
        keyValue.put("location", "" + location);
        keyValue.put("drinking", "" + drinking);
        keyValue.put("gear", "" + gear);
        keyValue.put("begin_at", "" + begin_at);
        keyValue.put("preset_count", "" + preset_count);
        keyValue.put("preset_group", "" + preset_group);
        keyValue.put("per_breaktime", "" + per_breaktime);
    }
}

