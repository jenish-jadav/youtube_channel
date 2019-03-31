package jadav.jenish.youtubechannel.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jenish on 24/5/17.
 */

public class MyUtils {
    
    public static Spanned textToHtml(String text)
    {
        return  Html.fromHtml(text);
    }

}
