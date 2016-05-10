package songming.straing.app.https.request;

import android.support.annotation.IntDef;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import songming.straing.app.config.Config;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.model.ArticleDetailInfo;
import songming.straing.utils.JSONUtil;
import songming.straing.utils.RequestUrlUtils;

/**
 * 圈点赞
 */
public class CirclePraiseRequest extends BaseHttpRequestClient {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACTION_PRAISE, ACTION_CANCEL})
    public @interface PraiseType {
    }

    public static final int ACTION_PRAISE = 1;
    public static final int ACTION_CANCEL = 2;

    public long moment_id;
    public int type;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder().setHost(Config.HOST)
                .setPath("/moment/like/create")
                .addParam("key", LocalHost.INSTANCE.getKey())
                .addParam("moment_id", moment_id)
                .addParam("type", type)
                .build();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {
    }
}

