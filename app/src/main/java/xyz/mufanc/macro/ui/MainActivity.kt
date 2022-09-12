package xyz.mufanc.macro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import mufanc.easyhook.api.Logger
import xyz.mufanc.macro.IHandlerService
import xyz.mufanc.macro.R
import xyz.mufanc.macro.databinding.ActivityMainBinding
import xyz.mufanc.macro.xposed.RequestPack
import xyz.mufanc.macro.xposed.ServiceChannel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter by lazy { IntentListAdapter() }

    private val service = object : IHandlerService.Stub() {
        override fun onNewIntent(bundle: Bundle) {
            val request = RequestPack(bundle)
            Logger.i(request.callingPackage)
            Logger.i(request.intent.toUri(0))
            runOnUiThread {
                adapter.insert(request.intent.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with (binding) {
            setContentView(root)

            intentList.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.VERTICAL, false
            )
            intentList.adapter = adapter
        }

        ServiceChannel.init(applicationContext, service)
    }
}
