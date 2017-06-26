package explorer.ding.com.explorer.utils;


import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author Joyband
 */
public class StringUtil {

    public static boolean isNull(Integer integerValue) {
        if (integerValue == null) {
            return true;
        }
        return integerValue <= 0;

    }

    public static boolean isNull(String str) {
        if (str == null || str.trim().isEmpty()) {
            return true;
        }
        return str.equalsIgnoreCase("null");
    }

    /**
     * @param string
     * @return UTF-8 string
     * @author Ammad Amjad
     */
    public static String getUTF8String(String string) {

        if (TextUtils.isEmpty(string)) {
            return "";
        }

        try {
            byte[] utf8 = string.getBytes("iso-8859-1");

            // Convert from UTF-8 to Unicode
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
//            MLog.e("UTF decoding error", e);
        }
        return string;
    }
}
