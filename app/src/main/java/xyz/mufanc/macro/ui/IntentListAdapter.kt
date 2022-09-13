package xyz.mufanc.macro.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.mufanc.macro.databinding.ItemIntentListBinding

class IntentListAdapter(
    private val packageManager: PackageManager
) : RecyclerView.Adapter<IntentListAdapter.ViewHolder>() {

    private val intentList = mutableListOf<Intent>()

    class ViewHolder(binding: ItemIntentListBinding) : RecyclerView.ViewHolder(binding.root) {
        val icon: ImageView = binding.icon
        val simpleName: TextView = binding.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemIntentListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val intent = intentList[position]
        holder.icon.setImageDrawable(packageManager.getActivityIcon(intent))
        holder.simpleName.text = intent.component!!.className.substringAfterLast('.')
    }

    override fun getItemCount(): Int = intentList.size

    fun insert(intent: Intent) {
        intentList.add(intent)
        notifyItemInserted(intentList.lastIndex)
    }
}
