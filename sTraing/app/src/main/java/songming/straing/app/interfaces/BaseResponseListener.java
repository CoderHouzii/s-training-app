package songming.straing.app.interfaces;

import songming.straing.app.https.base.BaseResponse;

/**
 * 网络请求回掉接口封装
 */
public interface BaseResponseListener {
    void onStart(BaseResponse response);
    void onStop(BaseResponse response);
    void onFailure(BaseResponse response);
    void onSuccess(BaseResponse response);

}
