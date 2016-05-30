package songming.straing.app.https.upload;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpEntity;
import songming.straing.app.https.base.BaseHttpRequestClient;
import songming.straing.app.https.base.BaseRequest;

/**
 * 文件上传请求
 */
public class UploadStringRequest extends BaseRequest {
    private HttpEntity mEntity;

    public UploadStringRequest(int method, String url, BaseHttpRequestClient client, Response.ErrorListener listener,
                               HttpEntity entity) {
        super(method, url, client, listener);
        this.mEntity = entity;
    }

    @Override
    public String getBodyContentType() {
        if (mEntity != null) {
            return mEntity.getContentType().getValue();
        }
        else {
            return super.getBodyContentType();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        if (mEntity == null) return super.getBody();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }
}
