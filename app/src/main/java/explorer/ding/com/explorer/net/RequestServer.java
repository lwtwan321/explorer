package explorer.ding.com.explorer.net;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import explorer.ding.com.explorer.application.ExplorerApplication;

/**
 * MAKE CALLS AND REQUESTS TO SERVER
 *
 * @author Ammad Amjad
 */

public class RequestServer {


    /**
     * @param req
     * @param tag
     * @author JoseMiguel
     */
    public static void addToRequestQueue(Request<JSONObject> req, String tag) {
        ExplorerApplication.getInstance().addToRequestQueue(req, tag);
    }

    /**
     * @param req
     * @param tag
     * @author JoseMiguel
     */
    public static void addToRequestQueue(JsonArrayRequest req, String tag) {
        ExplorerApplication.getInstance().addToRequestQueue(req, tag);
    }

    /**
     * @param req
     * @param tag
     * @author JoseMiguel
     */
    public static void addToRequestQueue(StringRequest req, String tag) {
        ExplorerApplication.getInstance().addToRequestQueue(req, tag);
    }
}
