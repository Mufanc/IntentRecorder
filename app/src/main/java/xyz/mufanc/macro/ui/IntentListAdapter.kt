package xyz.mufanc.macro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.mufanc.macro.databinding.ItemIntentListBinding

class IntentListAdapter : RecyclerView.Adapter<IntentListAdapter.ViewHolder>() {

    private val intentList = mutableListOf<String>()

    class ViewHolder(binding: ItemIntentListBinding) : RecyclerView.ViewHolder(binding.root) {
        val uri: TextView = binding.uri
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemIntentListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.uri.text = intentList[position]
    }

    override fun getItemCount(): Int = intentList.size

    fun insert(intent: String) {
        intentList.add(intent)
        notifyDataSetChanged()
    }
}
