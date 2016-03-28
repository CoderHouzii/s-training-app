package songming.straing.app.cache;

import android.text.TextUtils;
import com.jakewharton.disklrucache.DiskLruCache;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import songming.straing.app.FileStore;
import songming.straing.app.thread.ThreadPoolManager;
import songming.straing.utils.MD5Tools;

/**
 * 缓存工具类
 */
public enum CacheManager {
    INSTANCE;

    private DiskLruCache mDiskLruCache;
    private File mCacheFile;

    public void initHttpCache() {
        mCacheFile = new File(FileStore.INSTANCE.getLocalCachePath() + File.separator + "http");
        try {
            //100m缓存
            mDiskLruCache = DiskLruCache.open(mCacheFile, 1, 1, 100 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //保存
    public void save(String name, String jsonStr) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(jsonStr)) return;
        save(name, jsonStr.getBytes());
    }

    public void save(String name, final byte[] data) {
        if (TextUtils.isEmpty(name) || data == null) return;

        if (mDiskLruCache != null) {
            try {
                final DiskLruCache.Editor editor = mDiskLruCache.edit(MD5Tools.hashKey(name));
                if (editor == null) return;
                final OutputStream os = editor.newOutputStream(0);
                ThreadPoolManager.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            os.write(data);
                            os.flush();
                            editor.commit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //取出
    public String loadCacheString(String name) {
        if (TextUtils.isEmpty(name)) return null;
        byte[] data = loadCacheBytes(name);
        return data == null ? null : new String(data);
    }

    public byte[] loadCacheBytes(String name) {
        if (TextUtils.isEmpty(name)) return null;
        byte[] data = null;
        if (mDiskLruCache != null) {
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(MD5Tools.hashKey(name));
                String cacheValue = snapshot != null ? snapshot.getString(0) : null;
                data = TextUtils.isEmpty(cacheValue) ? null : cacheValue.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                data = null;
            }
        }
        return data;
    }

    //删除
    public void removeCache(String fileName) {
        if (fileName == null) return;

        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.remove(MD5Tools.hashKey(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
