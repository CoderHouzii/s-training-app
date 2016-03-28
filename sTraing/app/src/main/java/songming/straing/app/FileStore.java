package songming.straing.app;

import android.net.Uri;
import java.io.File;
import java.io.FileFilter;
import songming.straing.utils.FileUtils;
import songming.straing.utils.PreferenceUtils;

/**
 * 本地文件存储类
 */
public enum FileStore {
    INSTANCE;

    //=============================================================创建文件夹
    private final String rootFolder = "sTraingApp";
    private String rootPath = FileUtils.getSDCardPath() + rootFolder;

    public void createFileFolder() {
        FileUtils.createFolder(rootPath, "Photos");
        FileUtils.createFolder(rootPath, "Cache");
        FileUtils.createFolder(rootPath, "Star");
        FileUtils.createFolder(rootPath, "localCache");
        FileUtils.createFolder(rootPath, "host");

        //=============================================================子文件夹
        FileUtils.createFolder(getPhotoImgPath(), "temp");
    }

    public void clearTempFolder() {
        File file = new File(getPhotoTempImgPath());
        if (file != null && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File tempFile : files) {
                    tempFile.delete();
                }
            }
        }
        File hostFiles = new File(getHostInfoPath());
        if (hostFiles != null && hostFiles.isDirectory()) {
            File[] files = hostFiles.listFiles(new HostInfoFileFilter());
            if (files != null && files.length > 0) {
                for (File tempFile : files) {
                    tempFile.delete();
                }
            }
        }
    }

    public String getHostInfoPath() {return rootPath + "/" + "host";}

    public String getPhotoImgPath() {
        return rootPath + "/" + "Photos";
    }

    public String getCachePath() {
        return rootPath + "/" + "Cache";
    }

    public String getLocalCachePath() {return rootPath + "/" + "localCache";}

    public String getStarPath() {
        return rootPath + "/" + "Star";
    }

    public String getPhotoTempImgPath() {
        return getPhotoImgPath() + "/" + "temp";
    }

    static class HostInfoFileFilter implements FileFilter {
        String avatar = (String) PreferenceUtils.INSTANCE.getSharedPreferenceData("avatar", "");
        String wallpic = (String) PreferenceUtils.INSTANCE.getSharedPreferenceData("wallpic", "");

        @Override
        public boolean accept(File f) {
            if (f != null) {
                String path = String.valueOf(Uri.fromFile(f));
                if (path.equals(avatar) || path.equals(wallpic)) {
                    return false;
                }
                else {
                    return true;
                }
            }
            return false;
        }
    }
}
