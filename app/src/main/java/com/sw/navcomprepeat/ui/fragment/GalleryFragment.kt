package com.sw.navcomprepeat.ui.fragment

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.sw.navcomprepeat.core.constants.Constants
import com.sw.navcomprepeat.core.utils.isImage
import com.sw.navcomprepeat.databinding.FragmentGalleryBinding
import com.sw.navcomprepeat.ui.adapter.GalleryAdapter
import java.io.File

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter
    val listOfImages: MutableList<File> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scan(File(Environment.getExternalStorageDirectory().path))
        setupAdapter()


    }


    private fun setupAdapter() {
        adapter = GalleryAdapter(listOfImages)
        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = layoutManager
    }

    fun scan(file: File) {
        if(file.isImage()) {
            listOfImages.add(file)
            return
        }
        if(file.isDirectory) {
            file.listFiles()?.toList()?.let {files ->
                files.forEach{scan(it)}

            }
        }
    }

}