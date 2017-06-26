package explorer.ding.com.explorer.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    /**
     * Returns the <b>JSONObject</b> located at a given position in a
     * <b>JSONArray</b>
     *
     * @param position
     * @param jsonArray
     * @return The <b>JSONObject</b> located at the given position. If position
     * is out of the array range or array is null, it will return a
     * <b>null</b> object.
     * @author JoseMiguel
     */
    public static JSONObject getJSONObjectFromArray(int position, JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }

        if (position < 0) {
            return null;
        }

        int lenght = jsonArray.length();
        if (position >= lenght) {
            return null;
        }

        return jsonArray.optJSONObject(position);
    }

    /**
     * @param object
     * @param key
     * @return
     * @throws JSONException
     * @author Ammad Amjad
     */
    public static String getString(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return "";
        }

        if (TextUtils.isEmpty(key)) {
            return "";
        }

        if (!object.has(key)) {
            return "";
        }

        try {
            return object.getString(key).trim();
        } catch (JSONException e) {
            return "";
        }
    }

    /**
     * @param object
     * @param key
     * @return
     * @author Ammad Amjad
     */
    public static int getInt(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return 0;
        }

        if (TextUtils.isEmpty(key)) {
            return 0;
        }

        return object.optInt(key);
    }

    /**
     * @param object
     * @param key
     * @return
     * @author Ammad Amjad
     */
    public static long getLong(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return 0;
        }

        if (TextUtils.isEmpty(key)) {
            return 0;
        }

        return object.optLong(key);

    }

    /**
     * @param object
     * @param key
     * @return
     * @author JoseMiguel
     */
    public static double getDouble(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return 0;
        }

        if (TextUtils.isEmpty(key)) {
            return 0;
        }

        return object.optDouble(key);

    }

    /**
     * @param object
     * @param key
     * @return
     * @author Ammad Amjad
     */
    public static boolean getBoolean(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return false;
        }

        if (TextUtils.isEmpty(key)) {
            return false;
        }

        return object.optBoolean(key);

    }

    /**
     * @param object
     * @param key
     * @return JSONArray containing the array indicated by key, if not
     * available, an empty array
     * @author Ammad Amjad
     */
    public static JSONArray getJsonArray(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return new JSONArray();
        }

        if (TextUtils.isEmpty(key)) {
            return new JSONArray();
        }

        if (!object.has(key)) {
            return new JSONArray();
        }

        try {
            return object.getJSONArray(key);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    /**
     * @param object
     * @param key
     * @return
     * @author Ammad Amjad
     */
    public static JSONObject getJsonObject(
            JSONObject object,
            String key
    ) {
        if (object == null) {
            return new JSONObject();
        }

        if (TextUtils.isEmpty(key)) {
            return new JSONObject();
        }

        if (!object.has(key)) {
            return new JSONObject();
        }

        try {
            return object.getJSONObject(key);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    /**
     * @param array
     * @return true , if array is empty
     * @author Ammad AMjad
     */
    public static boolean isEmpty(JSONArray array) {
        if (array == null) {
            return true;
        }
        return array.length() <= 0;
    }
}
