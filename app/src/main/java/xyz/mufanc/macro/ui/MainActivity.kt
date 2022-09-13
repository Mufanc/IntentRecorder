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

    private val adapter by lazy { IntentListAdapter(packageManager) }

    private val service = object : IHandlerService.Stub() {
        override fun onNewIntent(bundle: Bundle) {
            val request = RequestPack(bundle)
            runOnUiThread {
                adapter.insert(request.intent)
                binding.intentList.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with (binding) {
            setContentView(root)

            intentList.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.VERTICAL, true
            ).apply { stackFromEnd = true }
            intentList.adapter = adapter
        }

        adapter.insert(intent)
        ServiceChannel.init(applicationContext, service)
    }
}
