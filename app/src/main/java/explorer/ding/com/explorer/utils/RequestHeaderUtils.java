package explorer.ding.com.explorer.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;



import java.util.HashMap;
import java.util.Map;

import explorer.ding.com.explorer.BuildConfig;

/**
 * Created by will on 10/19/2015.
 * <p>
 * Creates default headers for all server-requests.
 */
public class RequestHeaderUtils {

    private static final String ACCEPT = "Accept";
    private static final String TYPE = "application/json";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String AUTHORIZATION = "Authorization";
    private static final String USER_AGENT = "User-Agent";

    public static Map<String, String> createDefaultHeaders(Context context) {
        return createDefaultHeaders(context, null);
    }

    public static Map<String, String> createDefaultHeaders(Context context, Map<String, String> additionalHeaders) {
        Map<String, String> map = new HashMap<>();
        map.put(ACCEPT, TYPE);
//        String lng = LanguageController.getLanguageCode(context);
//        map.put(ACCEPT_LANGUAGE, lng);
        map.put(
                USER_AGENT,
                "ding/"
                        + BuildConfig.VERSION_NAME
                        + " ("
                        + "Android; "
                        + Build.PRODUCT + "; "
                        + Build.VERSION.RELEASE
                        + ")"
        );
        //TODO DJH
//        String auth = AuthController.getBasicAuth(context);
//        XMPPServiceController.showLog("auth=" + auth);
//        if (!TextUtils.isEmpty(auth)) {
//            map.put(AUTHORIZATION, auth);
//        }
        if (additionalHeaders != null) {
            map.putAll(additionalHeaders);
        }
        return map;
    }
}
