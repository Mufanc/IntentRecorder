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

    override fun onCreate() {
        super.onCreate()

        bindService(
            Intent(this, HandlerService::class.java),
            object : ServiceConnection {
                override fun onServiceDisconnected(ignored: ComponentName) = Unit
                override fun onServiceConnected(ignored: ComponentName, service: IBinder) {
                    Logger.i("@Module: service connected")
                    ServiceChannel.init(this@App, service)
                }
            },
            Context.BIND_AUTO_CREATE
        )
    }
}
