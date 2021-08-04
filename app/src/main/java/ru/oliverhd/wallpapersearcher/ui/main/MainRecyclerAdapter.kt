package ru.oliverhd.wallpapersearcher.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.oliverhd.wallpapersearcher.R
import ru.oliverhd.wallpapersearcher.ui.categorylist.OnItemViewClickListener

class MainRecyclerAdapter(
    private var onItemViewClickListener: OnItemViewClickListener?
) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    private var categoryData: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.textView.text = categoryData[position]
        holder.textView.setOnClickListener {
            onItemViewClickListener?.onItemViewClick(categoryData[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryData.size
    }

    fun setData(data: List<String>) {
        categoryData = data
        notifyDataSetChanged()
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.category_text_view)
    }
}