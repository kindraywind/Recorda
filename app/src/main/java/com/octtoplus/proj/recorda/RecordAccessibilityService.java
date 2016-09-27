package com.octtoplus.proj.recorda;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

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
        }
        
        eventText = eventText + accessibilityEvent.getContentDescription();
        Log.i(TAG, "Event: " + eventText);
        Log.i(TAG, "a: "+accessibilityEvent.toString());
        toast("Event:"+ eventText);

        
    }

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "onServiceConnected: asdfadfasdfasdfasdfasdfdsafdsafs");
        toast("Start recorda");

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_SCROLLED;
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
}
