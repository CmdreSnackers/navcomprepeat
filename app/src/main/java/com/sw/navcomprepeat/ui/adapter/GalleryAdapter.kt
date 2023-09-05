package com.sw.navcomprepeat.ui.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sw.navcomprepeat.R
import com.sw.navcomprepeat.core.constants.Constants
import com.sw.navcomprepeat.databinding.LayoutItem2Binding
import java.io.File

class GalleryAdapter(
    private var files: List<File>
):RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding: LayoutItem2Binding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutItem2Binding.inflate(layoutInflater,parent,false)

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return files.size
    }




    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file =files[position]
        Glide.with(holder.itemView)
            .load(file)
            .placeholder(R.drawable.ic_image)
            .into(holder.binding.ivImage)
    }



}