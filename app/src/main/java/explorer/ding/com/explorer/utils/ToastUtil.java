package explorer.ding.com.explorer.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {

	public static void showToastShort(Context context, Toast toast, String text) {
		if (toast != null) {
			toast.setText(text);
			toast.notify();
		} else {
			if (context == null) {
				return;
			}

			if (Looper.myLooper() == null) {
				Looper.prepare();
			}

			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		toast.show();
	}


	public static void showToastShort(Context context, Toast toast, int resId) {
		if (toast != null) {
			toast.setText(resId);
			toast.notify();
		} else {
			if (context == null) {
				return;
			}

			if (Looper.myLooper() == null) {
				Looper.prepare();
			}

			toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static void showToastLong(Context context, Toast toast, String text) {
		if (toast != null) {
			toast.setText(text);
			toast.notify();
		} else {
			if (context == null) {
				return;
			}

			if (Looper.myLooper() == null) {
				Looper.prepare();
			}

			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		}
		toast.show();
	}

	public static void showToastLong(Context context, Toast toast, int resId) {
		if (toast != null) {
			toast.setText(resId);
			toast.notify();
		} else {
			if (context == null) {
				return;
			}

			if (Looper.myLooper() == null) {
				Looper.prepare();
			}

			toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
		}
		toast.show();
	}
}
