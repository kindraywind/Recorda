package com.octtoplus.proj.recorda

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.os.Environment
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import org.json.JSONObject
import java.io.File
import java.util.ArrayList

/**
 * Created by muangsiriworamet on 9/21/16.
 */

class RecordAccessibilityService : AccessibilityService() {

    private val info = AccessibilityServiceInfo()

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        val eventType = accessibilityEvent.eventType
        var eventText: String? = null

        when (eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> eventText = getJsonFromClickEvent(accessibilityEvent).toString()
            AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> eventText = getJsonFromSelectOrFocus(accessibilityEvent).toString()
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> eventText = getJsonFromScroll(accessibilityEvent).toString()
            AccessibilityEvent.TYPE_WINDOWS_CHANGED -> eventText = getJsonFromWindowChange(accessibilityEvent).toString()
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> eventText = getJsonFromWindowStateChange(accessibilityEvent).toString()
            else -> eventText = getJsonFromOtherEvent(accessibilityEvent).toString()
        }
        logToSdCard(eventText)
    }

    override fun onGesture(gestureId: Int): Boolean {
        toast("gesture: " + gestureId)
        logToSdCard("gesture: " + gestureId)
        return super.onGesture(gestureId)
    }

    override fun onServiceConnected() {
        toast("START RECORDA")

        val events = listOf(AccessibilityEvent.TYPE_VIEW_CLICKED,
                AccessibilityEvent.TYPE_VIEW_LONG_CLICKED,
                AccessibilityEvent.TYPE_VIEW_SCROLLED,
                AccessibilityEvent.TYPE_VIEW_SELECTED,
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED,
                AccessibilityEvent.TYPE_WINDOWS_CHANGED,
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)

        val flags = listOf(AccessibilityServiceInfo.DEFAULT,
                AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS,
                AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE)

        info.eventTypes = events.reduce { reduced, value -> reduced or value }
        info.flags = flags.reduce { reduced, value -> reduced or value }

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.packageNames = arrayOf("com.octtoplus.proj.recorda", "com.poketutor.pokedex2")
        info.notificationTimeout = 100

        this.serviceInfo = info
    }

    override fun onInterrupt() {

    }

    fun toast(text: CharSequence) {
        val context = applicationContext

        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    fun getJsonFromClickEvent(event: AccessibilityEvent) =
            JSONObject().apply {
                put("className", event.className)
                put("contentDescription", event.contentDescription)
                put("eventText", getEventText(event))
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("fromIndex", event.fromIndex)
                put("isChecked", event.isChecked)
                put("isEnable", event.isEnabled)
                put("isPassword", event.isPassword)
                put("itemCount", event.itemCount)
                put("packageName", event.packageName)
                put("toIndex", event.toIndex)
                put("idName", event.source.viewIdResourceName)
            }

    fun getJsonFromSelectOrFocus(event: AccessibilityEvent) =
            JSONObject().apply {
                put("className", event.className)
                put("contentDescription", event.contentDescription)
                put("currentItemIndex", event.currentItemIndex)
                put("eventText", getEventText(event))
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("fromIndex", event.fromIndex)
                put("isChecked", event.isChecked)
                put("isEnable", event.isEnabled)
                put("isPassword", event.isPassword)
                put("itemCount", event.itemCount)
                put("packageName", event.packageName)
                put("toIndex", event.toIndex)
                put("idName", event.source.viewIdResourceName)
            }

    fun getJsonFromScroll(event: AccessibilityEvent) =
            JSONObject().apply {
                put("className", event.className)
                put("contentDescription", event.contentDescription)
                put("currentItemIndex", event.currentItemIndex)
                put("eventText", getEventText(event))
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("fromIndex", event.fromIndex)
                put("isChecked", event.isChecked)
                put("isEnable", event.isEnabled)
                put("isPassword", event.isPassword)
                put("itemCount", event.itemCount)
                put("packageName", event.packageName)
                put("scrollX", event.scrollX)
                put("scrollY", event.scrollY)
                put("toIndex", event.toIndex)
                put("idName", event.source.viewIdResourceName)
            }

    fun getJsonFromTextChange(event: AccessibilityEvent) =
            JSONObject().apply {
                put("addedCount", event.addedCount)
                put("beforeText", event.beforeText)
                put("className", event.className)
                put("contentDescription", event.contentDescription)
                put("eventText", getEventText(event))
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("fromIndex", event.fromIndex)
                put("isChecked", event.isChecked)
                put("isEnable", event.isEnabled)
                put("isPassword", event.isPassword)
                put("packageName", event.packageName)
                put("removedCount", event.removedCount)
                put("idName", event.source.viewIdResourceName)
            }

    fun getJsonFromWindowChange(event: AccessibilityEvent) =
            JSONObject().apply {
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("idName", event.source.viewIdResourceName)
            }

    fun getJsonFromWindowStateChange(event: AccessibilityEvent) =
            JSONObject().apply {
                put("className", event.className)
                put("eventText", getEventText(event))
                put("eventTime", event.eventTime.toString())
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("isEnabled", event.isEnabled)
                put("packageName", event.packageName)
                put("idName", event.source.viewIdResourceName)
            }

    fun getJsonFromOtherEvent(event: AccessibilityEvent) =
            JSONObject().apply {
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("idName", event.source.viewIdResourceName)
            }

    fun logToSdCard(eventStr: String) {
        val path = Environment.getExternalStorageDirectory().absolutePath + "/recorda_log.txt"
        File(path).printWriter().use { out ->
            out.println(eventStr + ",")
        }
    }

    private fun getEventText(event: AccessibilityEvent): List<String> {
        val eventTextList = ArrayList<String>()
        for (charSequence in event.text) {
            eventTextList.add(charSequence.toString())
        }
        return eventTextList
    }

    companion object {
        private val TAG = "RecService"
    }
}
