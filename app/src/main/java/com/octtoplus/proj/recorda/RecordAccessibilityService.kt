package com.octtoplus.proj.recorda

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

import org.json.JSONException
import org.json.JSONObject

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
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
        Log.i(TAG, "onServiceConnected: start")
        toast("START RECORDA")
        //logToSdCard("Start recorda session");

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or
                //							AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED |
                AccessibilityEvent.TYPE_VIEW_LONG_CLICKED or
                AccessibilityEvent.TYPE_VIEW_SELECTED or
                //							AccessibilityEvent.TYPE_VIEW_FOCUSED |
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED or
                //							AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED |
                AccessibilityEvent.TYPE_VIEW_SCROLLED or
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                AccessibilityEvent.TYPE_WINDOWS_CHANGED

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
            }

    fun getJsonFromWindowChange(event: AccessibilityEvent) =
            JSONObject().apply {
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
            }

    fun getJsonFromWindowStateChange(event: AccessibilityEvent) =
            JSONObject().apply {
                put("className", event.className)
                put("eventText", getEventText(event))
                put("eventTime", event.eventTime.toString())
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
                put("isEnabled", event.isEnabled)
                put("packageName", event.packageName)
                put("source", event.source)
            }

    fun getJsonFromOtherEvent(event: AccessibilityEvent) =
            JSONObject().apply {
                put("eventTime", java.lang.Long.toString(event.eventTime))
                put("eventType", AccessibilityEvent.eventTypeToString(event.eventType))
            }

    fun logToSdCard(eventStr: String) {
        try {
            val myFile = File(Environment.getExternalStorageDirectory().absolutePath + "/recorda_log.txt")
            if (!myFile.exists()) {
                toast("CREATE NEW FILE")
                myFile.createNewFile()
            }
            val fOut = FileOutputStream(myFile, true)
            val outWriter = OutputStreamWriter(fOut)

            outWriter.append(eventStr)

            outWriter.append(",\n")
            outWriter.close()
            fOut.close()
        } catch (e: Exception) {
            toast(e.message.toString())
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
