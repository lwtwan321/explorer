package explorer.ding.com.explorer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import explorer.ding.com.explorer.module.BaseActivity;
import explorer.ding.com.explorer.net.NetworkResponseInterface;
import explorer.ding.com.explorer.net.RequestFactory;
import explorer.ding.com.explorer.utils.MLog;

public class MainActivity extends BaseActivity implements NetworkResponseInterface {




    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
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
        MLog.i("ding",response.toString());
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
