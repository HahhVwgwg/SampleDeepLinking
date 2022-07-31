package project.sample.deeplinking.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import project.sample.deeplinking.MainActivity

/*
You can test broadcast with adb command
*** WARNING starting from ANDROID 8 there's restriction to
explicit broadcast intent (try to test it with device below Android 8)
adb shell am broadcast -a project.sample.deeplinking.push.receiver --es DATA_BODY "https://www.google.com/"

You can test simple deep link using this adb command
adb shell am start -W -a android.intent.action.VIEW -d  "sample://project" project.sample.deeplinking
*/
class PushSimulationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action
        if (action.equals(INTENT_NAME, ignoreCase = true))
            startMainActivityWithIntent(context, intent?.extras?.getString(DATA_BODY))
    }

    private fun startMainActivityWithIntent(context: Context, url: String?) {
        val intent = Intent(context.applicationContext, MainActivity::class.java)
        intent.putExtra(DATA_BODY, url)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    companion object {
        const val DATA_BODY = "DATA_BODY"
        const val INTENT_NAME = "project.sample.deeplinking.push.receiver"
    }
}