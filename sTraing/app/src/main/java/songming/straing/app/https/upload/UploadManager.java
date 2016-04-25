package songming.straing.app.https.upload;

import songming.straing.app.interfaces.BaseResponseListener;
import songming.straing.app.interfaces.OnUploadProgressListener;

/**
 * 上传文件帮助类
 */
public class UploadManager {
    private String fileName;
    private OnUploadProgressListener mOnUploadProgressListener;
    private BaseResponseListener mResponseListener;
    private String url;
    private int requestType;

    private UploadManager(BaseResponseListener responseListener){
        mResponseListener=responseListener;
    }

    public static UploadManager create(BaseResponseListener responseListener){
        return new UploadManager(responseListener);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public OnUploadProgressListener getOnUploadProgressListener() {
        return mOnUploadProgressListener;
    }

    public void setOnUploadProgressListener(OnUploadProgressListener onUploadProgressListener) {
        mOnUploadProgressListener = onUploadProgressListener;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public UploadBaseRequest build(){
        return buildRequest();
    }


    UploadBaseRequest buildRequest(){
        UploadBaseRequest request=new UploadBaseRequest();
        request.setFileName(fileName);
        request.setOnUploadProgressListener(mOnUploadProgressListener);
        request.url=url;
        request.setRequestType(requestType);
        request.setOnResponseListener(mResponseListener);
        return request;

    }
}
