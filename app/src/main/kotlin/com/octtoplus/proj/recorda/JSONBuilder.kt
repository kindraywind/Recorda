package com.octtoplus.proj.recorda

import org.json.JSONObject

class JSON private constructor() {

    companion object {
        fun create(block: JSONBuilder.() -> Unit) = JSONBuilder(block).build()
    }

    class JSONBuilder private constructor() {

        constructor(init: JSONBuilder.() -> Unit) : this() {
            init()
        }

        var className: CharSequence? = null
        var contentDescription: CharSequence? = null
        var eventTime: Long? = null
        var eventType: CharSequence? = null
        var resourceIdName: CharSequence? = null
        var fromIndex: Int? = null
        var isChecked: Boolean? = null
        var isEnable: Boolean? = null
        var isPassword: Boolean? = null
        var itemCount: Int? = null
        var packageName: CharSequence? = null
        var toIndex: Int? = null
        var resource_id: CharSequence? = null

        fun className(block: JSONBuilder.() -> CharSequence) = apply { className = block() }

        fun contentDescription(block: JSONBuilder.() -> CharSequence) = apply { contentDescription = block() }

        fun eventTime(block: JSONBuilder.() -> Long) = apply { eventTime = block() }

        fun eventType(block: JSONBuilder.() -> CharSequence) = apply { eventType = block() }

        fun resourceIdName(block: JSONBuilder.() -> CharSequence) = apply { resourceIdName = block() }

        fun build() = JSONObject().build(this)

        /*
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
        put("resource-id", event.source.viewIdResourceName)
         */
    }

}

fun JSONObject.build(builder: JSON.JSONBuilder): JSONObject {
    apply {
        builder.className?.let {
            put("className", it.toString())
        }
        builder.contentDescription?.let {
            put("contentDescription", it.toString())
        }
        builder.eventTime?.let {
            put("eventTime", it.toString())
        }
        builder.eventType?.let {
            put("eventType", it.toString())
        }
        builder.resourceIdName?.let {
            put("resource-id", it.toString())
        }
        builder.fromIndex?.let {
            put("fromIndex", it.toString())
        }
        builder.isChecked?.let {
            put("isChecked", it.toString())
        }
        builder.isEnable?.let {
            put("isEnable", it.toString())
        }
        builder.isPassword?.let {
            put("isPassword", it.toString())
        }
        builder.itemCount?.let {
            put("itemCount", it.toString())
        }
        builder.packageName?.let {
            put("packageName", it.toString())
        }
        builder.toIndex?.let{
            put("toIndex", it.toString())
        }
        builder.resource_id?.let {
            put("resource-id", it.toString())
        }
    }
    return this
}