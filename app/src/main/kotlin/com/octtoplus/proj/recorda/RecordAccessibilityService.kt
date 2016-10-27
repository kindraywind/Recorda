package com.octtoplus.proj.recorda

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.os.Environment
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

/**
 * Created by muangsiriworamet on 9/21/16.
 */

class RecordAccessibilityService : AccessibilityService() {

    private val info = AccessibilityServiceInfo()

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        val eventType = accessibilityEvent.eventType

        val eventText: String?
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

        Log.e(TAG, info.eventTypes.toString())
        Log.e(TAG, flags.toString())

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
            JSON.create {
                className = event.className
                contentDescription = event.contentDescription
                currentItemIndex = event.currentItemIndex
                eventText = getEventText(event).toString()
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                fromIndex = event.fromIndex
                isChecked = event.isChecked
                isEnable = event.isScrollable
                isPassword = event.isPassword
                itemCount = event.itemCount
                packageName = event.packageName
                toIndex = event.toIndex
                resource_id = event.source.viewIdResourceName
            }

    fun getJsonFromSelectOrFocus(event: AccessibilityEvent) =
            JSON.create {
                className = event.className
                contentDescription = event.contentDescription
                currentItemIndex = event.currentItemIndex
                eventText = getEventText(event).toString()
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                fromIndex = event.fromIndex
                isChecked = event.isChecked
                isEnable = event.isScrollable
                isPassword = event.isPassword
                itemCount = event.itemCount
                packageName = event.packageName
                toIndex = event.toIndex
                resource_id = event.source.viewIdResourceName
            }

    fun getJsonFromScroll(event: AccessibilityEvent) =
            JSON.create {
                className = event.className
                contentDescription = event.contentDescription
                currentItemIndex = event.currentItemIndex
                eventText = getEventText(event).toString()
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                fromIndex = event.fromIndex
                isChecked = event.isChecked
                isEnable = event.isScrollable
                isPassword = event.isPassword
                packageName = event.packageName
                scrollX = event.scrollX
                scrollY = event.scrollY
                toIndex = event.toIndex
                resource_id = event.source.viewIdResourceName
            }

    fun getJsonFromTextChange(event: AccessibilityEvent) =
            JSON.create {
                addedCount = event.addedCount
                beforeText = event.beforeText
                className = event.className
                contentDescription = event.contentDescription
                eventText = getEventText(event).toString()
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                fromIndex = event.fromIndex
                isChecked = event.isChecked
                isEnable = event.isScrollable
                isPassword = event.isPassword
                packageName = event.packageName
                removedCount = event.removedCount
                resource_id = event.source.viewIdResourceName
            }

    fun getJsonFromWindowChange(event: AccessibilityEvent) =
            JSON.create {
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                resourceIdName = event.source.viewIdResourceName
            }

    fun getJsonFromWindowStateChange(event: AccessibilityEvent) =
            JSON.create {
                className = event.className
                eventText = getEventText(event).toString()
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                isEnable = event.isScrollable
                packageName = event.packageName
                resource_id = event.source.viewIdResourceName
            }

    fun getJsonFromOtherEvent(event: AccessibilityEvent) =
            JSON.create {
                eventTime = event.eventTime
                eventType = AccessibilityEvent.eventTypeToString(event.eventType)
                resourceIdName = event.source.viewIdResourceName
            }

    fun logToSdCard(eventStr: String) {
        val file = Environment.getExternalStorageDirectory().resolve("recorda_log.txt")
        file.appendText(eventStr + ",\n")
    }

    private fun getEventText(event: AccessibilityEvent) = event.text.map { it.toString() }

    companion object {
        private val TAG = RecordAccessibilityService::class.java.simpleName
    }

}
