package songming.straing.app.https.upload;

import android.support.annotation.NonNull;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * 上传文件的实体类IO监听
 */
public class HttpEntityWithProgress implements HttpEntity {
    private final ProgressListener listener;
    private final HttpEntity mEntity;

    public HttpEntityWithProgress(@NonNull HttpEntity entity, ProgressListener listener) {
        super();
        this.listener = listener;
        mEntity = entity;
    }

    @Override
    public boolean isRepeatable() {
        return mEntity.isRepeatable();
    }

    @Override
    public boolean isChunked() {
        return mEntity.isChunked();
    }

    @Override
    public long getContentLength() {
        return mEntity.getContentLength();
    }

    @Override
    public Header getContentType() {
        return mEntity.getContentType();
    }

    @Override
    public Header getContentEncoding() {
        return mEntity.getContentEncoding();
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return mEntity.getContent();
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        mEntity.writeTo(new ProgressOutPutStream(outstream,listener));
    }

    @Override
    public boolean isStreaming() {
        return mEntity.isStreaming();
    }

    @Override
    public void consumeContent() throws IOException {
        mEntity.consumeContent();
    }

    public interface ProgressListener {
        void transferred(long num);
    }

    static class ProgressOutPutStream extends FilterOutputStream {

        private final ProgressListener listener;
        private long transferred;

        public ProgressOutPutStream(final OutputStream out,
                                    final ProgressListener listener) {
            super(out);
            this.listener = listener;
            this.transferred = 0;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            this.transferred += len;
            this.listener.transferred(this.transferred);
        }

        public void write(int b) throws IOException {
            out.write(b);
            this.transferred++;
            this.listener.transferred(this.transferred);
        }
    }
}
