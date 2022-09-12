package xyz.mufanc.macro

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import mufanc.easyhook.api.Logger
import xyz.mufanc.macro.xposed.RequestPack

class HandlerService : Service() {

    override fun onCreate() {
        Logger.i("onCreate")
    }

    override fun onBind(intent: Intent): IBinder {
        return object : IHandlerService.Stub() {
            override fun onNewIntent(bundle: Bundle) {
                val request = RequestPack(bundle)
                Logger.i(request.callingPackage)
                Logger.i(request.intent.toUri(0))
            }
        }
    }
}
