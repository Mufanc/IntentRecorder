package xyz.mufanc.macro.xposed

import android.content.Intent
import android.os.Bundle
import mufanc.easyhook.api.reflect.getFieldAs
import xyz.mufanc.macro.BundleWrapper

class RequestPack(holder: Bundle) : BundleWrapper(holder) {
    val intent: Intent by holder
    val callingPackage: String by holder

    constructor(request: Any) : this(Bundle()) {
//        val intent = request.getFieldAs<Intent>("intent")!!.clone() as Intent
//        holder.putParcelable(::intent.name, intent)
        holder.putParcelable(::intent.name, request.getFieldAs(::intent.name))
        holder.putString(::callingPackage.name, request.getFieldAs(::callingPackage.name))
    }
}
