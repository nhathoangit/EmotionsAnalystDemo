package com.nhat910.emotionsanalyst.utils;
/*
 * Created by HoangDong on 30/11/2017.
 */

import android.app.Activity;
import android.content.Intent;

import com.nhat910.emotionsanalyst.AnalystImageActivity;

public class StartActivityUtils {

    public static void toAnalyzePhoto(Activity context) {
        Intent intent = new Intent().setClass(context, AnalystImageActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
