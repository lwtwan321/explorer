package explorer.ding.com.explorer.net;



import org.json.JSONObject;

import explorer.ding.com.explorer.utils.JsonUtils;

/**
 * Created by Ammad on 6/20/15.
 */
public class ErrorParser {

    private final static String CODE = "code";
    private final static String MESSAGE = "message";

    public final static ErrorModel getError(JSONObject object){
        ErrorModel model = new ErrorModel();
        if(object == null){
            return model;
        }

        int code = JsonUtils.getInt(object , CODE);
        String message = JsonUtils.getString(object , MESSAGE);

        model.setErrorCode(code);
        model.setErrorMessage(message);

        return model;
    }
}
