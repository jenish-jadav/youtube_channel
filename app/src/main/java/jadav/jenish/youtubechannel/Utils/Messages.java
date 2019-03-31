package jadav.jenish.youtubechannel.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import jadav.jenish.youtubechannel.BuildConfig;

/**
 * Created by jenish on 15/5/17.
 */

public class Messages {
    public static void log(String message) {
        if (BuildConfig.DEBUG)
            Log.d("Jenish", message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
