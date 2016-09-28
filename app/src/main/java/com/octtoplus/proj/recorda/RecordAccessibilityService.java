package com.octtoplus.proj.recorda;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by muangsiriworamet on 9/21/16.
 */

public class RecordAccessibilityService extends AccessibilityService {

    private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
    private static final String TAG = "RecService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        final int eventType = accessibilityEvent.getEventType();
        String eventText = null;
        
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Clicked: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                eventText = "Focused: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "Scrolled: ";
                break;
            default:
                eventText = "Other: ";
                break;
        }
        
        eventText = eventText + accessibilityEvent.getContentDescription();
        Log.i(TAG, "Event: " + eventText);
        Log.i(TAG, "a: "+accessibilityEvent.toString());
        toast("Event:"+ eventText);
        logToSdCard(accessibilityEvent, true);

        
    }

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "onServiceConnected: start");
        //logToSdCard("Start recorda session");

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_LONG_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED |
        AccessibilityEvent.TYPE_VIEW_SCROLLED | AccessibilityEvent.TYPE_VIEW_SELECTED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED |
        AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOWS_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_HAPTIC;
        info.packageNames = new String[]{"com.octtoplus.proj.recorda"};
        info.notificationTimeout = 100;

        this.setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {

    }

    public void toast(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void logToSdCard(AccessibilityEvent event, boolean isVerbose) {
        try {
            File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recorda_log.txt");
            toast(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (!myFile.exists()) {
                toast("CREATE NEW FILE");
                myFile.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter outWriter = new OutputStreamWriter(fOut);

            if (isVerbose) {
                outWriter.append("[class]:"+event.getClassName());
                outWriter.append("\n");
                outWriter.append("[source]:"+event.getSource());
                outWriter.append("\n");
                outWriter.append("[type]:"+AccessibilityEvent.eventTypeToString(event.getEventType()));
                outWriter.append("\n");
                outWriter.append("[desc]:"+event.getContentDescription());
                outWriter.append("\n");
                outWriter.append(event.toString());
                outWriter.append("\n");
                outWriter.append("---------------------");
            } else {
                outWriter.append(event.toString());
            }

            outWriter.append("\n");
            outWriter.close();
            fOut.close();
        } catch (Exception e) {
            toast(e.getMessage());
        }
    }
}
