package songming.straing.utils;

import songming.straing.app.STraingApp;

/**
 * 资源工具类
 */
public class ResourceUtils {

    public static int getResColor(int res) {
        return STraingApp.appContext.getResources().getColor(res);
    }

    public static String getResString(int strRes) {
        return STraingApp.appContext.getResources().getString(strRes);
    }

    public static float getResDimen(int dimenRes) {
        return STraingApp.appContext.getResources().getDimension(dimenRes);
    }

}
