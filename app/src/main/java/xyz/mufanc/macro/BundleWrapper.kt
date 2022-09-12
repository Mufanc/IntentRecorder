package xyz.mufanc.macro

import android.os.Bundle
import kotlin.reflect.KProperty

abstract class BundleWrapper(protected val holder: Bundle) {

    fun asBundle() = holder

    protected operator fun <T, R> Bundle.getValue(ignored: T, property: KProperty<*>): R {
        @Suppress("Unchecked_Cast")
        return get(property.name) as R
    }
}
