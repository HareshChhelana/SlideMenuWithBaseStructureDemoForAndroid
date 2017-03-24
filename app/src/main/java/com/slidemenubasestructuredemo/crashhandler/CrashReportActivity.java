package com.slidemenubasestructuredemo.crashhandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


import com.slidemenubasestructuredemo.R;
import com.slidemenubasestructuredemo.Utilities.AppUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CrashReportActivity extends Activity {

    public static final String EXTRA_STACKTRACE = "stackTrace";

    private String stackTrace;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stackTrace = getIntent().getStringExtra(EXTRA_STACKTRACE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int imageResource = android.R.drawable.stat_sys_warning;
        Drawable image = getResources().getDrawable(imageResource);

        builder.setTitle(getString(R.string.application_crashed))
                .setMessage(String.format(getString(R.string.crash_log_message),getString(R.string.app_name)))
                .setIcon(image)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appendLog(stackTrace);
                        dialog.dismiss();
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.finishAffinity(CrashReportActivity.this);
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void appendLog(String text) {
        File dir = new File(AppUtility.getAppStoragePath(this, ""));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File logFile = new File(dir, "Log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            Calendar cal = Calendar.getInstance();
            String strstackTrace = "\n\nDevice Info: " + getDeviceSuperInfo();
            strstackTrace += "\n\n Logged on :";
            strstackTrace += cal.get(Calendar.DAY_OF_MONTH);
            strstackTrace += "-" + (cal.get(Calendar.MONTH) + 1);
            strstackTrace += "-" + cal.get(Calendar.YEAR);
            strstackTrace += " " + cal.get(Calendar.HOUR_OF_DAY);
            strstackTrace += ":" + cal.get(Calendar.MINUTE);
            strstackTrace += ":" + cal.get(Calendar.SECOND) + "\n";
            buf.append(strstackTrace);
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openEmailClient(Uri.fromFile(logFile));
    }

    private String getDeviceSuperInfo() {
        String s = "";
        try {
            s += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
            s += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
            s += "\n Device: " + android.os.Build.DEVICE;
            s += "\n Model (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
            s += "\n RELEASE: " + android.os.Build.VERSION.RELEASE;
            s += "\n BRAND: " + android.os.Build.BRAND;
            s += "\n DISPLAY: " + android.os.Build.DISPLAY;
            s += "\n CPU_ABI: " + android.os.Build.CPU_ABI;
            s += "\n CPU_ABI2: " + android.os.Build.CPU_ABI2;
            s += "\n UNKNOWN: " + android.os.Build.UNKNOWN;
            s += "\n HARDWARE: " + android.os.Build.HARDWARE;
            s += "\n Build ID: " + android.os.Build.ID;
            s += "\n MANUFACTURER: " + android.os.Build.MANUFACTURER;
            s += "\n SERIAL: " + android.os.Build.SERIAL;
            s += "\n USER: " + android.os.Build.USER;
            s += "\n HOST: " + android.os.Build.HOST;
        } catch (Exception e) {
            e.printStackTrace();
            s = "unable to found device info";
        }
        return s;

    }

    private void openEmailClient(Uri uri) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.error_log_receiver)});
            String dateTime = "";
            try {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH);
                Date currenTimeZone = calendar.getTime();
                dateTime = sdf.format(currenTimeZone);
            } catch (Exception e) {
                e.printStackTrace();
            }
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Error log on : " + dateTime);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Please find attached crash log file.");
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(emailIntent, "Choose an email provider :"));
            ActivityCompat.finishAffinity(CrashReportActivity.this);
            finish();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
