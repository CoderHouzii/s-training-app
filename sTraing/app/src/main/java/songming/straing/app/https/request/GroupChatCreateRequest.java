package songming.straing.app.https.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.GroupChatInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 创建群聊
 */
public class GroupChatCreateRequest extends BaseHttpRequestClient{

    public String member_ids;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/contact/group/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus()==1){
            GroupChatInfo info= JSONUtil.toObject(json.optString("group"),GroupChatInfo.class);
            response.setData(info);
        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("member_ids",member_ids);
    }
}
