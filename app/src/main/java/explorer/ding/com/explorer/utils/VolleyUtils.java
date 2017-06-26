package explorer.ding.com.explorer.utils;

import android.text.TextUtils;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import explorer.ding.com.explorer.net.ErrorModel;
import explorer.ding.com.explorer.net.ErrorParser;

public class VolleyUtils {
    private static final String VOLLEY_LOG_TAG = "volley_log_tag";
    private static final String VOLLEY_LOG_TAG_STRING = "Volley error . Request Tag = %s , Response code = %s , Error =%s";
    private static final String VOLLEY_SUCCESS_STRING = "Volley Success . Request Tag = %s , Response = %s";
    private static final String SUCCESS = "Success with an empty response ";
    private static final String MESSAGE = "%s (%s)";
    private static final String VOLLEY_ERROR_SNACK_MESSAGE = "Something went wrong , Error : %s";

    /**
     * @param tag
     * @param string
     */
    public static void logVolleySuccess(String tag, String string) {

        if (TextUtils.isEmpty(tag)) {
            return;
        }

        if (TextUtils.isEmpty(string)) {
            return;
        }

        // FORMAT STRING
        String errorText = String.format(VolleyUtils.VOLLEY_SUCCESS_STRING, tag, string);

        // LOG
        MLog.d(VolleyUtils.VOLLEY_LOG_TAG, errorText);
    }

    /**
     * CALL THIS METHOD TO TOAST AND LOG VOLLEY ERROR
     *
     * @param tag
     * @param responseCode
     * @param error
     */
    public static void logVolleyRequestError(String tag, int responseCode, VolleyError error) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }

        if (responseCode >= 200 && responseCode <= 299) {
            MLog.d(VOLLEY_LOG_TAG, SUCCESS);
            return;
        }

        if (error == null) {
            return;
        }

        // FORMAT STRING
        String errorText = String.format(
                VolleyUtils.VOLLEY_LOG_TAG_STRING,
                tag,
                String.valueOf(responseCode),
                error.toString()
        );

        // LOG
        MLog.e(VolleyUtils.VOLLEY_LOG_TAG, errorText);
    }

    public static void showErrorSnack(JSONObject errorResponse, View parentView) {
        if (errorResponse == null) {
            return;
        }
        ErrorModel model = ErrorParser.getError(errorResponse);
        String finalMessage = String.format(
                MESSAGE,
                model.getErrorMessage(),
                String.valueOf(model.getErrorCode())
        );
        SnackUtils.showSnackToast(parentView, finalMessage, true);

    }

    /**
     * @param tag
     * @param array
     */
    public static void logVolleySuccess(String tag, JSONArray array) {

        if (TextUtils.isEmpty(tag)) {
            return;
        }

        if (array == null) {
            return;
        }

        // FORMAT STRING
        String errorText = String.format(
                VolleyUtils.VOLLEY_SUCCESS_STRING,
                tag,
                array.toString()
        );

        // LOG
        MLog.d(VolleyUtils.VOLLEY_LOG_TAG, errorText);
    }

    /**
     * @param tag
     * @param object
     */
    public static void logVolleySuccess(String tag, JSONObject object) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }

        if (object == null) {
            return;
        }

        // FORMAT STRING
        String logText = String.format(
                VolleyUtils.VOLLEY_SUCCESS_STRING,
                tag,
                object.toString()
        );

        // LOG
        MLog.d(VolleyUtils.VOLLEY_LOG_TAG, logText);
    }

    private static String getErrorMessage(String body, int errorCode) {
        String errorText = String.format(
                VolleyUtils.VOLLEY_ERROR_SNACK_MESSAGE,
                errorCode
        );
        if (TextUtils.isEmpty(body)) {
            return errorText;
        }

        try {
            return new JSONObject(body).getString("message");
        } catch (JSONException e) {
            MLog.e(VOLLEY_LOG_TAG, e);
        }

        return errorText;
    }

    public static JSONObject getErrorObject(VolleyError error) {
        JSONObject responseObject = new JSONObject();
        NetworkResponse networkResponse = error.networkResponse;
        try {
            String responseBody = new String(networkResponse.data, "UTF-8");
            if (!TextUtils.isEmpty(responseBody)) {
                responseBody = StringUtil.getUTF8String(responseBody);
                responseObject = new JSONObject(responseBody);
            }
        } catch (Exception e) {
            MLog.e(VOLLEY_LOG_TAG, e);
        }

        return responseObject;
    }
}
