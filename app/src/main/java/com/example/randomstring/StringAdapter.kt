package com.example.randomstring

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StringAdapter(
    private val strings: List<RandomString>
) : RecyclerView.Adapter<StringAdapter.StringViewHolder>() {

    class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringValue: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return StringViewHolder(view)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val item = strings[position]
        holder.stringValue.text = "${item.value} (Length: ${item.length})"
    }

    override fun getItemCount() = strings.size
}
