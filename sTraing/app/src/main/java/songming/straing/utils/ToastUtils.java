package songming.straing.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {

    public static void ToastMessage(Context context, String msg) {
       Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
