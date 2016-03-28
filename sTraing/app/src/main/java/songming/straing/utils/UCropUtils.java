package songming.straing.utils;

import android.graphics.Bitmap;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

/**
 * ucrop工具类，用于初始化设定ucrop的一些UI信息
 */
public class UCropUtils {

    public UCropUtils() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    private static UCrop.Options mCircleOptions;

    public static UCrop.Options getCircleUcropOption() {
        if (mCircleOptions == null) {
            mCircleOptions = new UCrop.Options();
            //设置裁剪图片的保存格式
            mCircleOptions.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            //设置裁剪图片的图片质量
            mCircleOptions.setCompressionQuality(80);
            //设置你想要指定的可操作的手势
            mCircleOptions.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
            //设置uCropActivity里的UI样式
            mCircleOptions.setShowCropFrame(false);
            mCircleOptions.setOvalDimmedLayer(true);
        }
        return mCircleOptions;
    }

    private static UCrop.Options mReactOptions;
    public static UCrop.Options getRectUcropOption() {
        if (mReactOptions == null) {
            mReactOptions = new UCrop.Options();
            //设置裁剪图片的保存格式
            mReactOptions.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            //设置裁剪图片的图片质量
            mReactOptions.setCompressionQuality(80);
            //设置你想要指定的可操作的手势
            mReactOptions.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
            //设置uCropActivity里的UI样式
            mReactOptions.setShowCropFrame(false);
        }
        return mReactOptions;
    }

}
