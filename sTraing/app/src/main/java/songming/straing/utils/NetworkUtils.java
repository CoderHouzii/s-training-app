package songming.straing.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.TextView;

public class NetworkUtils {

	public static boolean isWifi(Context mContext) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) mContext
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
	    if (activeNetInfo != null  
	            && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
	        return true;  
	    }  
	    return false;  
	}  
	/**
	 * 检测网络连通性
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
