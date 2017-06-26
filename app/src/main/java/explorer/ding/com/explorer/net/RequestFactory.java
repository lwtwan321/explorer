package explorer.ding.com.explorer.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import explorer.ding.com.explorer.BuildConfig;
import explorer.ding.com.explorer.utils.MLog;
import explorer.ding.com.explorer.utils.RequestHeaderUtils;
import explorer.ding.com.explorer.utils.VolleyUtils;

public class RequestFactory {

    private static final String TAG = RequestFactory.class.getSimpleName();
    private static final int TIMEOUT = 30000;

    private static RetryPolicy makeRetryPolicy() {
        return new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
    }

    private static void printRequestHeaders(Map<String, String> map) {
        if (!BuildConfig.DEBUG) {
            return;
        }

        if (map == null) {
            return;
        }

        for (String key : map.keySet()) {
            MLog.w(TAG, "Header Key: " + key + ", " + "Value: " + map.get(key));
        }
    }

    private static void printRequestURLParameters(Map<String, String> map) {
        if (!BuildConfig.DEBUG) {
            return;
        }

        if (map == null) {
            return;
        }

        for (String key : map.keySet()) {
            MLog.w(TAG, "Parameter Key: " + key + ", " + "Value: " + map.get(key));
        }
    }


    /**
     * @param method HTTP Request Method, i.e, GET
     */
    private static String methodOverride(int method) {
        if (Request.Method.PATCH == method) {
            return "PATCH";
        } else if (Request.Method.DELETE == method) {
            return "DELETE";
        }
        return null;
    }

    private static void logRequest(final @NonNull String url, @Nullable Object requestObject, @Nullable String requestTag) {
        if (!BuildConfig.DEBUG) {
            return;
        }

        MLog.d(TAG, "HTTP Request, URL: " + url);
        if (requestObject != null) {
            MLog.d(TAG, "HTTP Request, Object: " + requestObject.toString());
        }
        MLog.d(TAG, "HTTP Request, TAG: " + requestTag);
    }

    /**
     * MAKE OBJECT REQUEST
     *
     * @param context                  The Application Context
     * @param method                   HTTP Request Method, i.e, GET
     * @param url                      Target request URL
     * @param jsonObject               JSONObject data
     * @param networkResponseInterface to be used to report result
     * @param que_tag                  Request TAG
     * @param headers                  Http Headers to be Added during Request
     */
    public static void makeObjectRequest(
            final @NonNull Context context,
            final int method,
            final @NonNull String url,
            final @Nullable JSONObject jsonObject,
            final @Nullable NetworkResponseInterface networkResponseInterface,
            final @Nullable String que_tag,
            final @Nullable Map<String, String> headers) {
        makeObjectRequest(
                context,
                method,
                url,
                jsonObject,
                networkResponseInterface,
                que_tag,
                null,
                headers
        );
    }

    private static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * MAKE OBJECT REQUEST
     *
     * @param context                  The Application Context
     * @param method                   HTTP Request Method, i.e, GET
     * @param url                      Target request URL
     * @param jsonObject               JSONObject data
     * @param networkResponseInterface to be used to report result
     * @param que_tag                  Request TAG
     * @param objectTag                Object binded to the request
     * @param headers                  Http Headers to be Added during Request
     */
    public static void makeObjectRequest(
            final @NonNull Context context,
            int method,
            final @NonNull String url,
            final @Nullable JSONObject jsonObject,
            final @Nullable NetworkResponseInterface networkResponseInterface,
            final @Nullable String que_tag,
            final @Nullable Object objectTag,
            final @Nullable Map<String, String> headers) {
        logRequest(url, jsonObject, que_tag);

        if (!isNetworkAvailable(context)) {
            if (networkResponseInterface != null) {
                networkResponseInterface.onNetworkUnAvailable(que_tag, objectTag, false);
            }
            return;
        }

        final String methodOverride = methodOverride(method);
        if (!TextUtils.isEmpty(methodOverride)) {
            method = Request.Method.POST;
        }

        Listener<JSONObject> successListener = new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {// PASS INFO TO INTERFACE
                if (networkResponseInterface != null) {
                    networkResponseInterface.onSuccess(que_tag, objectTag, response);
                }
                // LOG
                VolleyUtils.logVolleySuccess(que_tag, response);
            }
        };

        // ERROR LISTENER
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int networkResponseCode;
                if (error.networkResponse == null) {
                    networkResponseCode = getResponseCode();
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        Toast.makeText(context, "Timeout!", Toast.LENGTH_LONG).show();
                        if (networkResponseInterface != null) {
                            networkResponseInterface.onError(que_tag, objectTag, -1, error);
                        }
                    } else if (networkResponseCode >= 200 && networkResponseCode <= 299) {
                        if (networkResponseInterface != null) {
                            networkResponseInterface.onSuccess(que_tag, objectTag, new JSONObject());
                        }
                        VolleyUtils.logVolleySuccess(que_tag, new JSONObject());
                    }
                } else {
                    networkResponseCode = error.networkResponse.statusCode;
                    if (networkResponseInterface != null) {
                        networkResponseInterface.onError(que_tag, objectTag, networkResponseCode, error);
                    }
                    VolleyUtils.logVolleyRequestError(que_tag, networkResponseCode, error);
                }
            }
        };

        // PREPARE REQUEST
        JsonObjectRequest req = new JsonObjectRequest(method, url, jsonObject, successListener, errorListener) {

            // ADDING HEADERS
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = RequestHeaderUtils.createDefaultHeaders(context, headers);
                if (!TextUtils.isEmpty(methodOverride)) {
                    map.put("X-Http-Method-Override", methodOverride);
                }
                printRequestHeaders(map);
                return map;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                setResponseCode(response.statusCode);
                return super.parseNetworkResponse(response);
            }

        };


        req.setRetryPolicy(makeRetryPolicy());

        // ADD REQUEST TO QUE
        RequestServer.addToRequestQueue(req, que_tag);

    }

    private static int responseCode = -1;

    private static void setResponseCode(int code) {
        responseCode = code;
    }

    private static int getResponseCode() {
        return responseCode;
    }

    /**
     * MAKE ARRAY REQUEST
     *
     * @param context                  The Application Context
     * @param method                   HTTP Request Method, i.e, GET
     * @param url                      Target request URL
     * @param networkResponseInterface to be used to report result
     * @param que_tag                  Request TAG
     * @param headers                  Http Headers to be Added during Request
     */
    public static JsonArrayRequest makeArrayRequest(
            final @NonNull Context context,
            final int method,
            final @NonNull String url,
            final @Nullable JSONArray jsonArray,
            final @Nullable NetworkResponseInterface networkResponseInterface,
            final @Nullable String que_tag,
            final @Nullable Map<String, String> headers) {
        return makeArrayRequest(
                context,
                method,
                url,
                jsonArray,
                networkResponseInterface,
                que_tag,
                null,
                headers
        );
    }

    /**
     * MAKE ARRAY REQUEST
     *
     * @param context                  The Application Context
     * @param method                   HTTP Request Method, i.e, GET
     * @param url                      Target request URL
     * @param networkResponseInterface to be used to report result
     * @param que_tag                  Request TAG
     * @param objectTag                Object binded to the request
     * @param headers                  Http Headers to be Added during Request
     */
    public static JsonArrayRequest makeArrayRequest(
            final @NonNull Context context,
            int method,
            final @NonNull String url,
            final @Nullable JSONArray jsonArray,
            final @Nullable NetworkResponseInterface networkResponseInterface,
            final @Nullable String que_tag,
            final @Nullable Object objectTag,
            final @Nullable Map<String, String> headers) {
        logRequest(url, jsonArray, que_tag);

        if (!isNetworkAvailable(context)) {
            if (networkResponseInterface != null) {
                networkResponseInterface.onNetworkUnAvailable(que_tag, objectTag, false);
            }
            return null;
        }

        final String methodOverride = methodOverride(method);
        if (!TextUtils.isEmpty(methodOverride)) {
            method = Request.Method.POST;
        }

        // SUCCESS LISTENER
        Listener<JSONArray> successListener = new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (networkResponseInterface != null) {
                    networkResponseInterface.onSuccess(que_tag, objectTag, response);
                }
                // LOG
                VolleyUtils.logVolleySuccess(que_tag, response);
            }
        };

        // ERROR LISTENER
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int NETWORK_RESPONSE_CODE;
                if (error.networkResponse == null) {
                    NETWORK_RESPONSE_CODE = getResponseCode();
                    if (NETWORK_RESPONSE_CODE >= 200 && NETWORK_RESPONSE_CODE <= 299) {
                        if (networkResponseInterface != null) {
                            networkResponseInterface.onSuccess(que_tag, objectTag, new JSONArray());
                        }
                        VolleyUtils.logVolleySuccess(que_tag, new JSONArray());
                    }
                } else {
                    NETWORK_RESPONSE_CODE = error.networkResponse.statusCode;
                    if (networkResponseInterface != null) {
                        networkResponseInterface.onError(que_tag, objectTag, NETWORK_RESPONSE_CODE, error);
                    }
                    VolleyUtils.logVolleyRequestError(que_tag, NETWORK_RESPONSE_CODE, error);
                }
            }
        };

        // PREPARE REQUEST
        JsonArrayRequest req = new JsonArrayRequest(method, url, jsonArray, successListener, errorListener) {

            // ADDING HEADERS
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = RequestHeaderUtils.createDefaultHeaders(context, headers);
                if (!TextUtils.isEmpty(methodOverride)) {
                    map.put("X-Http-Method-Override", methodOverride);
                }
                printRequestHeaders(map);
                return map;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                setResponseCode(response.statusCode);
                return super.parseNetworkResponse(response);
            }

        };

        req.setRetryPolicy(makeRetryPolicy());

        // ADD REQUEST TO QUE
        RequestServer.addToRequestQueue(req, que_tag);

        return req;
    }

    /**
     * MAKE STRING REQUEST
     *
     * @param context                  The Application Context
     * @param method                   HTTP Request Method, i.e, GET
     * @param url                      Target request URL
     * @param networkResponseInterface to be used to report result
     * @param que_tag                  Request TAG
     * @param params                   HTTP Request URL Encoded Parameters
     * @param headers                  Http Headers to be Added during Request
     */
    public static void makeStringRequest(
            final @NonNull Context context,
            final int method,
            final @NonNull String url,
            final @Nullable NetworkResponseInterface networkResponseInterface,
            final @Nullable String que_tag,
            final @Nullable Map<String, String> params,
            final @Nullable Map<String, String> headers
    ) {
        makeStringRequest(
                context,
                method,
                url,
                networkResponseInterface,
                que_tag,
                null,
                params,
                headers
        );
    }


    /**
     * MAKE STRING REQUEST
     *
     * @param context                  The Application Context
     * @param method                   HTTP Request Method, i.e, GET
     * @param url                      Target request URL
     * @param networkResponseInterface to be used to report result
     * @param que_tag                  Request TAG
     * @param objectTag                Object binded to the request
     * @param params                   HTTP Request URL Encoded Parameters
     * @param headers                  Http Headers to be Added during Request
     */
    public static void makeStringRequest(
            final @NonNull Context context,
            int method,
            final @NonNull String url,
            final @Nullable NetworkResponseInterface networkResponseInterface,
            final @Nullable String que_tag,
            final @Nullable Object objectTag,
            final @Nullable Map<String, String> params,
            final @Nullable Map<String, String> headers) {
        logRequest(url, null, que_tag);

        if (!isNetworkAvailable(context)) {
            if (networkResponseInterface != null) {
                networkResponseInterface.onNetworkUnAvailable(que_tag, objectTag, false);
            }
            return;
        }

        final String methodOverride = methodOverride(method);
        if (!TextUtils.isEmpty(methodOverride)) {
            method = Request.Method.POST;
        }

        Listener<String> successListener = new Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PASS INFO TO INTERFACE
                if (networkResponseInterface != null) {
                    networkResponseInterface.onSuccess(que_tag, objectTag, response);
                }
                // LOG
                VolleyUtils.logVolleySuccess(que_tag, response);
            }
        };

        // ERROR LISTENER
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int NETWORK_RESPONSE_CODE;
                if (error.networkResponse == null) {
                    NETWORK_RESPONSE_CODE = getResponseCode();
                    if (NETWORK_RESPONSE_CODE >= 200 && NETWORK_RESPONSE_CODE <= 299) {
                        if (networkResponseInterface != null) {
                            networkResponseInterface.onSuccess(que_tag, objectTag, "");
                        }
                        VolleyUtils.logVolleySuccess(que_tag, new JSONObject());
                    }
                } else {
                    NETWORK_RESPONSE_CODE = error.networkResponse.statusCode;
                    if (networkResponseInterface != null) {
                        networkResponseInterface.onError(que_tag, objectTag, NETWORK_RESPONSE_CODE, error);
                    }
                    VolleyUtils.logVolleyRequestError(que_tag, NETWORK_RESPONSE_CODE, error);
                }
            }
        };


        // PREPARE REQUEST
        StringRequest req = new StringRequest(method, url, successListener, errorListener) {
            // ADDING HEADERS
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = RequestHeaderUtils.createDefaultHeaders(context, headers);
                if (!TextUtils.isEmpty(methodOverride)) {
                    map.put("X-Http-Method-Override", methodOverride);
                }
                printRequestHeaders(map);
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                setResponseCode(response.statusCode);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                if (params != null) {
                    map.putAll(params);
                }
                printRequestURLParameters(map);
                return map;
            }
        };

        req.setRetryPolicy(makeRetryPolicy());

        // ADD REQUEST TO QUE
        RequestServer.addToRequestQueue(req, que_tag);
    }

}
