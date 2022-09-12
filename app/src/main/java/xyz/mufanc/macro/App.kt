package xyz.mufanc.macro

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import mufanc.easyhook.api.Logger
import xyz.mufanc.macro.xposed.ServiceChannel

class App : Application() {

    companion object {
        @JvmStatic
        val isModuleActivated = false

        const val TAG = "Intent++"
        init {
            Logger.configure(TAG = TAG)
        }
    }
}
