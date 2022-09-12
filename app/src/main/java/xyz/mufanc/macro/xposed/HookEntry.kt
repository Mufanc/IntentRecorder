package xyz.mufanc.macro.xposed

import android.content.Context
import android.os.IBinder
import mufanc.easyhook.api.Logger
import mufanc.easyhook.api.annotation.XposedEntry
import mufanc.easyhook.api.hook.HookHelper
import mufanc.easyhook.api.hook.hook
import mufanc.easyhook.api.reflect.callMethodAs
import mufanc.easyhook.api.reflect.callStaticMethod
import mufanc.easyhook.api.reflect.findField
import mufanc.easyhook.api.reflect.getField
import xyz.mufanc.macro.App
import xyz.mufanc.macro.BuildConfig
import xyz.mufanc.macro.IHandlerService

@XposedEntry
class HookEntry : HookHelper(App.TAG) {
    override fun onHook() {
        handle {
            onLoadPackage(BuildConfig.APPLICATION_ID) {
                findClass(App::class.java.name).findField {
                    name == App::isModuleActivated.name
                }!!.set(null, true)
                Logger.i("@Module: update module activation status")
            }
            onLoadPackage("android") {
                var remote: IHandlerService? = null
                val lock = Any()

                val callback = fun (service: IBinder) {
                    synchronized(lock) {
                        remote = IHandlerService.Stub.asInterface(service)
                    }
                    service.linkToDeath({
                        synchronized(lock) {
                            remote = null
                        }
                        Logger.i("@Hooker: service disconnected!")
                    }, 0)
                    Logger.i("@Hooker: received service!")
                }

                findClass("com.android.server.wm.ActivityStarter").hook {
                    method({ name == "execute" }) {
                        before { param ->
                            val request = param.thisObject.getField("mRequest")!!
                            synchronized(lock) {
                                remote?.onNewIntent(RequestPack(request).asBundle())
                            }
                        }
                    }
                }

                findClass("com.android.server.am.ActivityManagerService").hook {
                    method({ name == "systemReady"}) {
                        after {
                            val context = findClass("android.app.ActivityThread")
                                .callStaticMethod("currentActivityThread")!!
                                .callMethodAs<Context>("getSystemContext")!!
                            ServiceChannel.init(context, callback)
                        }
                    }
                }
            }
        }
    }
}

