package songming.straing.app.https.request;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.model.UserDetailInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 好友列表
 */
public class FriendListRequest extends BaseHttpRequestClient {


    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/user/list/follow")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
        if (response.getStatus() == 1) {
            List<UserDetailInfo> infos = JSONUtil.toList(json.optString("users"), new TypeToken<ArrayList<UserDetailInfo>>(){}.getType());
            response.setData(infos);
        }
    }
}

