# Recorda
### A realtime usage recording service for Android application.
----------
###About
**Recorda** allows you to monitor and record the application usage in the background while it is being used.
It runs in the background
and receives callbacks from the system when AccessibilityEvents are fired. Such events
denote some state transition in the GUI, for example, a button has been clicked. This
service can be toggled on or off from the Accessibility menu in the device’s setting After the service is turned on, a new file will be created in sdcard.

The following shows an example of the first
10 lines of a usage log from `AnyMemo` app.

``` json
[ {"eventText":"[AnyMemo Free]","isEnable":"false","scrollY":"null","className":"org.liberty.android.fantastischmemo.MainTabs","eventType":"TYPE_WINDOW_STATE_CHANGED","eventTime":"138884","packageName":"org.liberty.android.fantastischmemo"},
  {"isEnable":"false","resource-id":"org.liberty.android.fantastischmemo:id\/recent_open_list","packageName":"org.liberty.android.fantastischmemo","isPassword":"false","scrollY":"-1","eventType":"TYPE_VIEW_SCROLLED","toIndex":"6","scrollX":"-1","currentItemIndex":"-1","fromIndex":"0","isChecked":"false","eventText":"[]","className":"android.widget.ListView","eventTime":"139066"},
  {"isEnable":"false","fromIndex":"-1","packageName":"org.liberty.android.fantastischmemo","isChecked":"false","eventText":"[Quiz Bowl - Art.db, Total:39 New:33 Rev:6]","isPassword":"false","scrollY":"null","className":"android.widget.LinearLayout","eventType":"TYPE_VIEW_CLICKED","eventTime":"146436","toIndex":"-1","itemCount":"-1","currentItemIndex":"-1"},
  {"isEnable":"false","resource-id":"noneId","packageName":"org.liberty.android.fantastischmemo","eventText":"[Please wait..., Loading database...]","scrollY":"null","className":"android.app.ProgressDialog","eventType":"TYPE_WINDOW_STATE_CHANGED","eventTime":"146823"},
  {"eventText":"[New:33 Rev:6 ID:1  ]","isEnable":"false","scrollY":"null","className":"org.liberty.android.fantastischmemo.cardscreen.MemoScreen","eventType":"TYPE_WINDOW_STATE_CHANGED","eventTime":"146931","packageName":"org.liberty.android.fantastischmemo"},
  {"isEnable":"false","resource-id":"org.liberty.android.fantastischmemo:id\/layout_question","packageName":"org.liberty.android.fantastischmemo","isPassword":"false","scrollY":"null","eventType":"TYPE_VIEW_CLICKED","toIndex":"-1","currentItemIndex":"-1","fromIndex":"-1","isChecked":"false","eventText":"[Painter of \"The Scream\" \n]","className":"android.widget.LinearLayout","eventTime":"149932","itemCount":"-1"},
  {"isEnable":"false","resource-id":"org.liberty.android.fantastischmemo:id\/layout_answer","packageName":"org.liberty.android.fantastischmemo","isPassword":"false","scrollY":"null","eventType":"TYPE_VIEW_CLICKED","toIndex":"-1","currentItemIndex":"-1","fromIndex":"-1","isChecked":"false","eventText":"[Edvard Munch \n]","className":"android.widget.LinearLayout","eventTime":"153675","itemCount":"-1"},
  {"isEnable":"false","resource-id":"org.liberty.android.fantastischmemo:id\/answer","packageName":"org.liberty.android.fantastischmemo","isPassword":"false","scrollY":"null","eventType":"TYPE_VIEW_CLICKED","toIndex":"-1","currentItemIndex":"-1","fromIndex":"-1","isChecked":"false","eventText":"[Edvard Munch \n]","className":"android.widget.TextView","eventTime":"154866","itemCount":"-1"},
  {"isEnable":"false","resource-id":"org.liberty.android.fantastischmemo:id\/answer","packageName":"org.liberty.android.fantastischmemo","isPassword":"false","scrollY":"null","eventType":"TYPE_VIEW_CLICKED","toIndex":"-1","currentItemIndex":"-1","fromIndex":"-1","isChecked":"false","eventText":"[Edvard Munch \n]","className":"android.widget.TextView","eventTime":"155575","itemCount":"-1"},
  {"isEnable":"false","resource-id":"org.liberty.android.fantastischmemo:id\/question","packageName":"org.liberty.android.fantastischmemo","isPassword":"false","scrollY":"null","eventType":"TYPE_VIEW_CLICKED","toIndex":"-1","currentItemIndex":"-1","fromIndex":"-1","isChecked":"false","eventText":"[Painter of \"The Scream\" \n]","className":"android.widget.TextView","eventTime":"156159","itemCount":"-1"} ]
```

----------
### Install
Simply clone this project and run on your phone or simulator.

----------

### Usage

* Make sure a SD card is inserted.
* Turn on recorda service in Accessibility menu in the device’s setting.
* Start excercising a target application.
* Retrieve the `usagelog.txt` by `adb pull /sdcard/recorda_log.txt` or open the file directly.

----------

**Recorda** is released under MIT license.
