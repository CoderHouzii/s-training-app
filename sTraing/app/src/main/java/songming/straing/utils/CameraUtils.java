package songming.straing.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import songming.straing.app.FileStore;

/**
 * 这是一个用于图片拍摄或者选择图片的相机操作工具类
 */
public class CameraUtils {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ FROM_CAMERA, FROM_ALBUM })
    public @interface Type {}

    // 来源
    public static final int FROM_CAMERA = 0x10;
    public static final int FROM_ALBUM = 0x11;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ AVATAR, WALLPIC })
    public @interface PhotoType {}

    // 类型
    public static final int AVATAR = 0x12;
    public static final int WALLPIC = 0x13;

    public static Uri tempPhoto;

    /**
     * 从相机获取
     */
    public static void getPhotoFromCamera(Activity c) {
        if (FileUtils.isSDexist()) {
            String tempFile = FileStore.INSTANCE.getPhotoTempImgPath() + "/" + "temp_" + System.currentTimeMillis();
            File file = new File(tempFile);

            tempPhoto = Uri.fromFile(file);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            c.startActivityForResult(intent, FROM_CAMERA);
        }
        else {
            ToastUtils.ToastMessage(c, "木有SD卡哦");
        }
    }

    public static void getPhotoFromAlbum(Activity c) {
        String tempFile = FileStore.INSTANCE.getPhotoTempImgPath() + "/" + "temp_" + System.currentTimeMillis();
        File file = new File(tempFile);
        tempPhoto = Uri.fromFile(file);
        Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        albumIntent.setType("image/*");
        //将拍到的图片放到指定文件夹
        albumIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        c.startActivityForResult(albumIntent, FROM_ALBUM);
    }

    private static Uri avatarSaveUri;
    private static Uri wallpicSaveUri;

    public static void crop(@NonNull Activity activity, Uri sourcePicUri, @PhotoType int type) {
        setSaveUri(type);
        switch (type) {
            case AVATAR:
                UCrop.of(sourcePicUri, avatarSaveUri).withOptions(UCropUtils.getCircleUcropOption()).start(activity);
                break;
            case WALLPIC:
                UCrop.of(sourcePicUri, wallpicSaveUri).withOptions(UCropUtils.getRectUcropOption()).start(activity);
                break;
            default:
                break;
        }
    }

    private static void setSaveUri(@PhotoType int type) {
        File file;
        String photoName;
        switch (type) {
            case AVATAR:
                photoName = MD5Tools.hashKey("avatar" + System.currentTimeMillis()) + ".jpg";
                file = new File(FileStore.INSTANCE.getHostInfoPath() + "/" + photoName);
                avatarSaveUri = Uri.fromFile(file);
                PreferenceUtils.INSTANCE.setSharedPreferenceData("avatar", String.valueOf(avatarSaveUri));
                if (file.exists()) {
                    file.delete();
                }
                break;
            case WALLPIC:
                photoName = MD5Tools.hashKey("wallpic" + System.currentTimeMillis()) + ".jpg";
                file = new File(FileStore.INSTANCE.getHostInfoPath() + "/" + photoName);
                wallpicSaveUri = Uri.fromFile(file);
                PreferenceUtils.INSTANCE.setSharedPreferenceData("wallpic", String.valueOf(wallpicSaveUri));
                if (file.exists()) {
                    file.delete();
                }
                break;
            default:
                break;
        }
    }
}
