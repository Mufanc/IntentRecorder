package xyz.mufanc.macro.xposed

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import mufanc.easyhook.api.Logger
import mufanc.easyhook.api.catch
import xyz.mufanc.macro.BuildConfig
import xyz.mufanc.macro.BundleWrapper

object ServiceChannel {
    private const val ACTION_PUSH_SERVICE = "xyz.mufanc.macro.action.PUSH_SERVICE"
    private const val INTENT_EXTRA_KEY = "TOKEN"

    private class Receiver(private val callback: (IBinder) -> Unit) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            catch {
                val extra = intent.getBundleExtra(INTENT_EXTRA_KEY) ?: return
                val service = Token(extra).getService() ?: return
                callback(service)
            }
        }
    }

    private class Token(holder: Bundle) : BundleWrapper(holder) {
        private val identifier: PendingIntent by holder
        private val service: IBinder by holder

        constructor(identifier: PendingIntent, service: IBinder) : this(Bundle()) {
            holder.putParcelable(::identifier.name, identifier)
            holder.putBinder(::service.name, service)
        }

        @JvmName("getService$")
        fun getService(): IBinder? {
            return if (identifier.creatorPackage == BuildConfig.APPLICATION_ID) {
                service
            } else {
                Logger.e("@Hooker: verification failed!")
                null
            }
        }
    }

    fun init(context: Context, service: IBinder) {
        context.sendBroadcast(
            Intent(ACTION_PUSH_SERVICE).setPackage("android").putExtra(
                INTENT_EXTRA_KEY,
                Token(
                    PendingIntent.getBroadcast(
                        context, 0, Intent(),
                        PendingIntent.FLAG_IMMUTABLE
                    ),
                    service
                ).asBundle()
            )
        )
    }

    fun init(context: Context, callback: (IBinder) -> Unit) {
        context.registerReceiver(Receiver(callback), IntentFilter(ACTION_PUSH_SERVICE))
    }
}
