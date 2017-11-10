package com.enterprise.lu.uni.notebook.app.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * Created by Plarent on 11/1/2017.
 */

public class UIHelper {

    public static void showDialog(Activity activity, String title, String message, String buttonText,
                                  DialogInterface.OnClickListener buttonListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, buttonListener);
        builder.setCancelable(false);
        builder.show();
    }

    public static void slideBackButton(Activity activity){
        SlidrConfig config = new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .sensitivity(1f)
                .scrimColor(Color.TRANSPARENT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(2400)
                .distanceThreshold(0.25f)
                .edge(false)
                .edgeSize(0.18f)
                .build();
        Slidr.attach(activity, config);
    }
}
