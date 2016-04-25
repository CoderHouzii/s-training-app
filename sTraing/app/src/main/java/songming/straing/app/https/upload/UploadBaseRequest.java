package songming.straing.app.https.upload;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;
import songming.straing.app.config.LocalHost;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.base.VolleyManager;
import songming.straing.app.interfaces.OnUploadProgressListener;

/**
 * 上传文件表单请求
 */
public class UploadBaseRequest extends BaseHttpRequestClient {
    private String fileName;
    private OnUploadProgressListener mOnUploadProgressListener;
    private long size = 0;
    private long userid;
    public String url;

    @Override
    public void post() {
        try {
            File file = new File(fileName);
            FileBody fileBody = new FileBody(file);

            String requestUrl = setUrl();

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileBody);
            builder.addTextBody("key", LocalHost.INSTANCE.getKey());
            builder.addTextBody("suffix", "jpg");

            HttpEntity httpEntity = builder.build();
            size = httpEntity.getContentLength();
            HttpEntityWithProgress httpEntityWithProgress = null;
            if (mOnUploadProgressListener != null) {
                httpEntityWithProgress = new HttpEntityWithProgress(httpEntity,
                        new HttpEntityWithProgress.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                try {
                                    //防止刷得过快
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mOnUploadProgressListener.onProgressChange((int) ((num / (float) size) * 100));
                            }
                        });
            }
            if (httpEntityWithProgress!=null){
                size=httpEntityWithProgress.getContentLength();
            }
            KLog.d("upload_image_size", "上传的图片大小：>>>>>  " + size);
            postStart();
            UploadStringRequest postRequest;
            if (mOnUploadProgressListener != null) {
                postRequest = new UploadStringRequest(Request.Method.POST, requestUrl, this, this,
                        httpEntityWithProgress);
            }
            else {
                postRequest = new UploadStringRequest(Request.Method.POST, requestUrl, this, this, httpEntity);
            }
            postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, 1.0f));
            postRequest.setShouldCache(false);
            VolleyManager.INSTANCE.addQueue(postRequest);
            if (httpEntity != null) httpEntity.consumeContent();
            if (httpEntityWithProgress != null) httpEntityWithProgress.consumeContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String setUrl() {
        return url;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json, int start, boolean hasMore) throws JSONException {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public OnUploadProgressListener getOnUploadProgressListener() {
        return mOnUploadProgressListener;
    }

    public void setOnUploadProgressListener(OnUploadProgressListener onUploadProgressListener) {
        mOnUploadProgressListener = onUploadProgressListener;
    }
}
