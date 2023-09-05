package com.sw.navcomprepeat.ui.adapter

import android.content.ClipData.Item
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sw.navcomprepeat.R
import com.sw.navcomprepeat.core.constants.Constants
import com.sw.navcomprepeat.core.utils.update
import com.sw.navcomprepeat.data.ItemDat
import com.sw.navcomprepeat.databinding.LayoutItemBinding
import java.io.File

class ProductAdapter(
    private var items:List<File>,
    private val onClick: (File) -> Unit
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: LayoutItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHold(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItem(files: List<File>) {
        val oldList = this.items

        this.items = files
        update(oldList, this.items) { item1, item2 ->
            item1.absolutePath == item2.absolutePath
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(holder is ItemViewHold) {
            holder.binding.run {
                if(item.isDirectory) {
                    imageView.setImageResource(R.drawable.ic_folder)
                } else if(Regex(Constants.imageReg).containsMatchIn(item.path)) {
                    Glide
                        .with(binding.root)
                        .load(item)
                        .placeholder(R.drawable.ic_image)
                        .into(imageView)
                } else {
                    imageView.setImageResource(R.drawable.ic_file)
                }

                tvtitle.text = item.name
                llFile.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    class ItemViewHold(val binding: LayoutItemBinding):RecyclerView.ViewHolder(binding.root)
}