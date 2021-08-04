package ru.oliverhd.wallpapersearcher.ui.categorylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ru.oliverhd.wallpapersearcher.R
import ru.oliverhd.wallpapersearcher.model.ResponseImage

class CategoryRecyclerAdapter(
    private var onItemViewClickListener: OnItemViewClickListener?
) : RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {

    private var categoryData: List<ResponseImage> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val imageUrl: String = categoryData[position].previewURL
        holder.imageView.load(imageUrl) {
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
        holder.imageView.setOnClickListener {
            onItemViewClickListener?.onItemViewClick(categoryData[position].largeImageURL)
        }
    }

    override fun getItemCount(): Int {
        return categoryData.size
    }

    fun setData(data: List<ResponseImage>) {
        categoryData = data
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_preview)
    }
}