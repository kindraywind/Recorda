package com.octtoplus.proj.recorda;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


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
                eventText = getJsonFromClickEvent(accessibilityEvent).toString();
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
        logToSdCard(eventText);

        
    }

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "onServiceConnected: start");
        //logToSdCard("Start recorda session");

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
//							AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED |
                AccessibilityEvent.TYPE_VIEW_LONG_CLICKED |
                AccessibilityEvent.TYPE_VIEW_SELECTED |
//							AccessibilityEvent.TYPE_VIEW_FOCUSED |
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED |
//							AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED |
							AccessibilityEvent.TYPE_VIEW_SCROLLED |
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOWS_CHANGED;

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
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

    public JSONObject getJsonFromClickEvent(AccessibilityEvent event) {
        JSONObject json = new JSONObject();
        try {
            json.put("eventTime", Long.toString(event.getEventTime()));
            json.put("packageName", event.getPackageName());
            json.put("eventType", AccessibilityEvent.eventTypeToString(event.getEventType()));
            json.put("className", event.getClassName());
            json.put("eventText", getEventText(event));
            json.put("isEnable", event.isEnabled());
            json.put("isPassword", event.isPassword());
            json.put("contentDescription", event.getContentDescription());
            json.put("fromIndex", event.getFromIndex());
            json.put("toIndex", event.getToIndex());
            json.put("itemCount", event.getItemCount());
            json.put("isChecked", event.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public JSONObject logSelectOrFocus(AccessibilityEvent event) {
        JSONObject json = new JSONObject();
        try {
            json.put("eventTime", Long.toString(event.getEventTime()));
            json.put("packageName", event.getPackageName());
            json.put("eventType", AccessibilityEvent.eventTypeToString(event.getEventType()));
            json.put("className", event.getClassName());
            json.put("eventText", getEventText(event));
            json.put("isEnable", event.isEnabled());
            json.put("isPassword", event.isPassword());
            json.put("contentDescription", event.getContentDescription());
            json.put("fromIndex", event.getFromIndex());
            json.put("toIndex", event.getToIndex());
            json.put("itemCount", event.getItemCount());
            json.put("currentItemIndex", event.getCurrentItemIndex());
            json.put("isChecked", event.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public JSONObject logTextChange(AccessibilityEvent event) {
        JSONObject json = new JSONObject();
        try {
            json.put("eventTime", Long.toString(event.getEventTime()));
            json.put("packageName", event.getPackageName());
            json.put("eventType", AccessibilityEvent.eventTypeToString(event.getEventType()));
            json.put("className", event.getClassName());
            json.put("eventText", getEventText(event));
            json.put("isEnable", event.isEnabled());
            json.put("isPassword", event.isPassword());
            json.put("contentDescription", event.getContentDescription());
            json.put("fromIndex", event.getFromIndex());
            json.put("addedCount", event.getAddedCount());
            json.put("removedCount", event.getRemovedCount());
            json.put("beforeText", event.getBeforeText());
            json.put("isChecked", event.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }



    public void logToSdCard(String eventStr) {
        try {
            File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recorda_log.txt");
            toast(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (!myFile.exists()) {
                toast("CREATE NEW FILE");
                myFile.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter outWriter = new OutputStreamWriter(fOut);

                outWriter.append(eventStr);

            outWriter.append("\n");
            outWriter.close();
            fOut.close();
        } catch (Exception e) {
            toast(e.getMessage());
        }
    }

    private List<String> getEventText(AccessibilityEvent event) {
        List<String> eventTextList = new ArrayList<String>();
        for(CharSequence charSequence : event.getText()) {
            eventTextList.add(charSequence.toString());
        }
        return eventTextList;
    }
}
