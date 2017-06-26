package explorer.ding.com.explorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import explorer.ding.com.explorer.net.NetworkResponseInterface;
import explorer.ding.com.explorer.net.RequestFactory;

public class MainActivity extends AppCompatActivity implements NetworkResponseInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestFactory.makeObjectRequest(
                this,
                Request.Method.GET,
                "http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=关键字&bk_length=600",
                null,
                this,
                "ding",
                null
        );
    }

    @Override
    public void onSuccess(String tag, Object object, JSONArray response) {

    }

    @Override
    public void onSuccess(String tag, Object object, JSONObject response) {

    }

    @Override
    public void onSuccess(String tag, Object object, String response) {

    }

    @Override
    public void onError(String tag, Object object, int responseCode, VolleyError error) {

    }

    @Override
    public void onNetworkUnAvailable(String tag, Object object, boolean isConnectedToInternet) {

    }
}
