package explorer.ding.com.explorer.net;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * NETWORK RESPONSE INTERFACE , TO HANDLE AND FILTER DIFFERENT TYPES OF RESPONSES
 * <p>
 * Created by Ammad Amjad
 * Copyright (C) 2016 Sandbox3
 * <p/>
 * Copy or sale of this class is forbidden.
 */
public interface NetworkResponseInterface {

    void onSuccess(String tag, Object object, JSONArray response);

    void onSuccess(String tag, Object object, JSONObject response);

    void onSuccess(String tag, Object object, String response);

    void onError(String tag, Object object, int responseCode, VolleyError error);

    void onNetworkUnAvailable(String tag, Object object, boolean isConnectedToInternet);
}
