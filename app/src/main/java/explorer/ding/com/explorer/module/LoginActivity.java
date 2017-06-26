package explorer.ding.com.explorer.module;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import explorer.ding.com.explorer.R;
import explorer.ding.com.explorer.utils.MLog;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @BindView(R.id.et_user_pwd)
    EditText et_user_pwd;

    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;

    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    @OnClick({R.id.tv_forget_pwd,R.id.btn_login})
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.tv_forget_pwd:
                MLog.i("djh","tv_forget_pwd");
                break;
            case R.id.btn_login:
                MLog.i("djh","btn_login");
                break;
        }
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
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
    }
}
