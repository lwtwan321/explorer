package explorer.ding.com.explorer.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import explorer.ding.com.explorer.utils.StringUtil;


/**
 * Created by Ammad on 6/23/15.
 * Copyright (C) 2016 Sandbox3
 * <p/>
 * Copy or sale of this class is forbidden.
 */
public class SnackUtils {

    /**
     * @param context
     * @param object
     * @param defaultError
     * @author Ammad Amjad
     */
    public static void showErrorSnackBar(Context context, View layoutView, Object object, int defaultError) {
        String errorMessage = null;
        if (object instanceof String) {
            errorMessage = (String) object;
        }
        if (errorMessage == null) {
            if (context != null) {
                errorMessage = context.getString(defaultError);
            }
        }
        Snackbar.make(layoutView, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    /**
     * SHOW SNACK BAR WITH MESSAGE
     *
     * @param layoutView
     * @param message
     * @param isLong
     * @author Ammad Amjad
     */
    public static void showSnackToast(View layoutView, String message, boolean isLong) {
        if (layoutView == null) {
            return;
        }
        if (StringUtil.isNull(message)) {
            return;
        }
        int lenght = Snackbar.LENGTH_SHORT;
        if (isLong) {
            lenght = Snackbar.LENGTH_LONG;
        }
        Snackbar.make(layoutView, message, lenght).show();
    }

    /**
     * SHOW SNACK BAR WITH MESSAGE
     *
     * @param layoutView
     * @param message
     * @param isLong
     * @author Ammad Amjad
     */
    public static void showSnackToast(View layoutView, int message, boolean isLong) {
        if (layoutView == null) {
            return;
        }
        if (StringUtil.isNull(message)) {
            return;
        }
        int lenght = Snackbar.LENGTH_SHORT;
        if (isLong) {
            lenght = Snackbar.LENGTH_LONG;
        }
        Snackbar.make(layoutView, message, lenght).show();
    }

}
