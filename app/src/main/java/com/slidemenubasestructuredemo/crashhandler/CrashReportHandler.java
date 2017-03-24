package com.slidemenubasestructuredemo.crashhandler;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashReportHandler implements UncaughtExceptionHandler {


    private Context m_context;

    ///////////////////////////////////////////// implementation

    private CrashReportHandler(Context context) {
        m_context = context;
    }

    public static void attach(Context context) {

        Thread.setDefaultUncaughtExceptionHandler(
                new CrashReportHandler(context)
        );
    }

    public void uncaughtException(Thread thread, Throwable exception) {

        exception.printStackTrace();
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        try {
            Intent intent = new Intent(m_context, CrashReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CrashReportActivity.EXTRA_STACKTRACE, stackTrace.toString());
            m_context.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // from RuntimeInit.crash()
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

}
