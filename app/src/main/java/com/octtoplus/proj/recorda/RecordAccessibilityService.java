package com.octtoplus.proj.recorda;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

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
        }
        
        eventText = eventText + accessibilityEvent.getContentDescription();
        Log.i(TAG, "Event: " + eventText);
        Log.i(TAG, "a: "+accessibilityEvent.toString());
        
    }

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "onServiceConnected: asdfadfasdfasdfasdfasdfdsafdsafs");
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_SCROLLED |
                AccessibilityEvent.TYPE_VIEW_LONG_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED |
                AccessibilityEvent.TYPE_VIEW_SELECTED;
        //info.feedbackType = AccessibilityServiceInfo.FEEDBACK_HAPTIC;
        info.packageNames = new String[]{"com.octtoplus.proj.recorda"};
        info.notificationTimeout = 100;

        this.setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {

    }
}
