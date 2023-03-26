package com.example.innobuzzassignment

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class AccessibilityService: AccessibilityService() {

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            Toast.makeText(applicationContext, "WhatsApp Launched.", Toast.LENGTH_LONG).show()
            Log.v("MyActivity","app opened")
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.v("MyActivity","service connected")
    }

}